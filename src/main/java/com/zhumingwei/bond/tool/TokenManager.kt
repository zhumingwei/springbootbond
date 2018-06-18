package com.zhumingwei.bond.tool

import com.zhumingwei.bond.entity.Account
import com.zhumingwei.bond.tool.TokenManager.cacheIDtoExTime
import com.zhumingwei.bond.tool.TokenManager.getEncryptStr
import java.nio.charset.StandardCharsets
import java.util.Base64
import java.util.Date
import java.util.HashMap

/**
 * @author zhumingwei
 * @date 2018/6/17 下午9:15
 */
object TokenManager {

    //过期时间
    val EX_TIME = (1000 * 60 * 10).toLong()
    //key 为用户id value Pair<token,Date>为过期时间。
    val cacheIDtoExTime = HashMap<Int, Pair<String, Date>>()
    private val SPLIT_STR = "~~~";
    //获取token
    fun generateToken(account: Account): String {
        //根据时间，用户id。生成token
        val origins = account.uid.toString() + SPLIT_STR + System.currentTimeMillis()
        return getEncryptStr(origins)
    }


    fun getEncryptStr(str: String): String {
        //TODO 对称加密的方法做
        val bytes = Base64.getEncoder().encode(str.toByteArray(StandardCharsets.UTF_8))
        return String(bytes, StandardCharsets.UTF_8)
    }

    fun getIDFromToken(encryptStr: String) = getDecodeStr(encryptStr).split(SPLIT_STR)[0].toIntOrNull()?.let { it } ?: 0

    fun getDecodeStrArray(encryptStr: String): Array<String> {
        return getDecodeStr(encryptStr).split(SPLIT_STR).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    fun getDecodeStr(encryptStr: String): String {
        //TODO 对称加密的方法做
        val bytes = encryptStr.toByteArray(StandardCharsets.UTF_8)
        return String(Base64.getDecoder().decode(bytes), StandardCharsets.UTF_8)
    }

    fun put(uid: Int, tokendate: Pair<String, Date>) {
        cacheIDtoExTime[uid] = tokendate
    }

    operator fun get(uid: Int): Pair<String, Date>? {
        return cacheIDtoExTime.get(uid)
    }

    fun checkUserExTimeAndTokenRight(uid: Int, userToken: String?): Boolean {
        return TokenManager.get(uid)?.let {
            //        var date = it.value
            //        var token = it.key
            it.first == userToken && it.second.after(Date())
        } ?: false
    }

    //刷新添加新的Token和时间
    fun refreshExTime(uid: Int, token: String) {
        var p: Pair<String, Date>? = TokenManager.get(uid)
        TokenManager.put(uid, token to Date(System.currentTimeMillis() + TokenManager.EX_TIME))
    }

    //自己验证Token
    fun refreshExTime(token: String?) {
        token?.let {
            refreshExTime(getIDFromToken(token),token)
        }

    }

}




