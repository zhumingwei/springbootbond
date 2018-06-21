package com.zhumingwei.bond.tool

import com.zhumingwei.bond.entity.Account
import com.zhumingwei.bond.tool.EncryptUtil.getDecodeStr
import com.zhumingwei.bond.tool.EncryptUtil.getEncryptStr
import com.zhumingwei.bond.tool.token.ITokenStore
import com.zhumingwei.bond.tool.token.MemoryTokenStore
import java.util.*

/**
 * @author zhumingwei
 * @date 2018/6/17 下午9:15
 */
object TokenManager {

    //过期时间
    val EX_TIME = (1000 * 60 * 10).toLong()
    //key 为用户id value Pair<token,Date>为过期时间。
    var tokenStore: ITokenStore = MemoryTokenStore()
    private val SPLIT_STR = "~~~";
    //获取token
    fun generateToken(account: Account): String {
        //根据时间，用户id。生成token
        val origins = account.uid.toString() + SPLIT_STR + System.currentTimeMillis()
        return getEncryptStr(origins)
    }


    fun getIDFromToken(encryptStr: String) = getDecodeStr(encryptStr).split(SPLIT_STR)[0].toIntOrNull()?.let { it } ?: 0

    fun getDecodeStrArray(encryptStr: String): Array<String> {
        return getDecodeStr(encryptStr).split(SPLIT_STR).dropLastWhile { it.isEmpty() }.toTypedArray()
    }


    fun put(uid: Int, tokendate: Pair<String, Date>) {
        tokenStore.put(uid,tokendate)
    }

    operator fun get(uid: Int): Pair<String, Date>? {
        //todo 如果过期删除token
        return tokenStore.get(uid)
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
            refreshExTime(getIDFromToken(token), token)
        }
    }

}




