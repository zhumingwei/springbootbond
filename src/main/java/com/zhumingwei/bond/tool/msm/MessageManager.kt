package com.zhumingwei.bond.tool.msm

import com.alibaba.fastjson.JSON
import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse
import com.aliyuncs.exceptions.ClientException
import com.aliyuncs.profile.DefaultProfile
import com.zhumingwei.bond.BondConstant
import com.zhumingwei.bond.tool.msm.save.IMsgCodeStore
import com.zhumingwei.bond.tool.msm.save.MemoryCodeStore
import java.util.*

/**
 * @author zhumingwei
 * @date 2018/6/21 下午1:38
 */
//TODO 目前只是存储了，并没有定期清理数据。后期加上使用rds
object MessageManager {
    //过期时间
    val EX_TIME = (1000 * 60 * 10).toLong()
    //产品名称:云通信短信API产品,开发者无需替换
    val product = "Dysmsapi"
    //产品域名,开发者无需替换
    val domain = "dysmsapi.aliyuncs.com"


    internal val signName = "朱明伟"

    @Throws(ClientException::class)
    fun sendSmsResponse(templateCode: String, param: Map<String, String>, phone: String): SendSmsResponse {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000")
        System.setProperty("sun.net.client.defaultReadTimeout", "10000")

        //初始化acsClient,暂不支持region化
        val profile = DefaultProfile.getProfile("cn-shanghai", BondConstant.MEG_ACCESSKEYID, BondConstant.MEG_ACCESSKEYSECRET)
        DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", product, domain)
        val acsClient = DefaultAcsClient(profile)

        //组装请求对象-具体描述见控制台-文档部分内容
        val request = SendSmsRequest()
        //必填:待发送手机号
        request.phoneNumbers = phone
        //必填:短信签名-可在短信控制台中找到
        request.signName = "朱明伟"
        //必填:短信模板-可在短信控制台中找到
        request.templateCode = "SMS_137412068"
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.templateParam = JSON.toJSONString(param)

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        val sendSmsResponse = acsClient.getAcsResponse(request)
        return sendSmsResponse
    }


    var registerCodeStore: IMsgCodeStore = MemoryCodeStore()//存储器
    //注册发送验证码
    @Throws(ClientException::class)
    fun sendRegisterCode(phone: String, code: String = generateCode()): SendSmsResponse {
        val response = sendSmsResponse("SMS_137412068", mapOf("code" to code), phone)
        if ("ok" == response.code.toLowerCase()) {
            registerCodeStore.put(phone, code to Date(System.currentTimeMillis() + EX_TIME))
        }
        return response
    }

    fun getRegisterCode(phone: String): String {
        return getCodeByUidFromStore(phone, registerCodeStore)
    }

    fun deleteRegisterCode(phone: String) {
        deleteCodeFromStore(phone, registerCodeStore)
    }


    var loginCodeStore: IMsgCodeStore = MemoryCodeStore()//存储器
    @Throws(ClientException::class)
    fun sendLoginCode(phone: String, code: String = generateCode()): SendSmsResponse {
        val response = sendSmsResponse("SMS_137505069", mapOf("code" to code), phone)
        if ("ok" == response.code.toLowerCase()) {
            loginCodeStore.put(phone, code to Date(System.currentTimeMillis() + EX_TIME))
        }
        return response
    }

    fun getLoginCode(phone: String): String {
        return getCodeByUidFromStore(phone, loginCodeStore)
    }

    fun deleteLoginCode(phone: String) {
        deleteCodeFromStore(phone, loginCodeStore)
    }


    var changePwdStore: IMsgCodeStore = MemoryCodeStore()//存储器
    @Throws(ClientException::class)
    fun sendChangePwdCode(phone: String, code: String = generateCode()): SendSmsResponse {
        val response = sendSmsResponse("SMS_137657862", mapOf("code" to code), phone)
        if ("ok" == response.code.toLowerCase()) {
            changePwdStore.put(phone, code to Date(System.currentTimeMillis() + EX_TIME))
        }
        return response
    }

    fun getChangePwdCode(phone: String): String {
        return getCodeByUidFromStore(phone, changePwdStore)
    }

    fun deleteChangePwdCode(phone: String) {
        deleteCodeFromStore(phone, changePwdStore)
    }


    private fun getCodeByUidFromStore(phone: String, store: IMsgCodeStore): String {
        val p: Pair<String, Date>? = store.get(phone)
        return p?.let {
            if (it.second.after(Date())) {
                it.first
            } else {
                store.delete(phone)
                ""
            }
        } ?: ""
    }

    fun deleteCodeFromStore(phone: String, store: IMsgCodeStore) {
        store.delete(phone)
    }

    fun generateCode(): String {
        return ((Math.random() * 9 + 1) * 100000).toInt().toString()
    }

}
