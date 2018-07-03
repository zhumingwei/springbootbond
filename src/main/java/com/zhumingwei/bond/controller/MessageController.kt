package com.zhumingwei.bond.controller

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse
import com.zhumingwei.bond.TOKEN_NAME
import com.zhumingwei.bond.tool.ResponseCode
import com.zhumingwei.bond.tool.StringUtil.checkPhoneNum
import com.zhumingwei.bond.tool.TokenManager
import com.zhumingwei.bond.tool.msm.MessageManager
import com.zhumingwei.bond.tool.response.BaseResponse
import org.apache.ibatis.annotations.Param
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *
 *   @author zhumingwei
 *   @date 2018/6/21 下午8:48
 */
@RestController
@RequestMapping("/msg")
class MessageController : BaseController() {
    @RequestMapping("/sendmsg/{type}", method = [RequestMethod.POST])
    fun sendMsg(@PathVariable type: String, request: HttpServletRequest, response: HttpServletResponse, @Param("phonenum") phonenum: String) {
        if (!checkPhoneNum(phonenum)) {
            responseError(response, ResponseCode.PHONE_ERROR)
        }
        var resp: SendSmsResponse? = when (type) {
            "register" -> {
                MessageManager.sendRegisterCode(phonenum)
            }
            "login" -> {
                MessageManager.sendLoginCode(phonenum)
            }
            "changePwd" -> {
                MessageManager.sendChangePwdCode(phonenum)
            }
            else -> {
                null
            }
        }

        resp?.let {
            responseMessage(response, BaseResponse<String>().apply {
                if (it.code.toLowerCase() == "ok") {
                    code = ResponseCode.SUCCESS.code
                    message = ResponseCode.SUCCESS.message + " " + resp.message
                    data = "发送验证码成功"
                } else {
                    code = ResponseCode.SEND_MESSAGE_ERROR.code
                    message = ResponseCode.SEND_MESSAGE_ERROR.message + " " + resp.message
                    data = resp.message
                }
            })
        } ?: run {
            responseMessage(response, BaseResponse<String>().apply {
                code = ResponseCode.SEND_MESSAGE_ERROR.code
                message = ResponseCode.SEND_MESSAGE_ERROR.message
                data = "发送验证码失败"
            })
        }
    }

}