package com.zhumingwei.bond.tool

import com.zhumingwei.bond.TOKEN_NAME
import javax.servlet.http.HttpServletRequest

/**
 * @author zhumingwei
 * @date 2018/6/21 下午2:14
 */
infix fun <A, B, C> Pair<A, B>.tri(that: C): Triple<A, B, C> = Triple(this.first, this.second, that)

fun String.isPhoneNum(): Boolean = StringUtil.checkPhoneNum(this)

fun String.isMsgCode(): Boolean {
    return this.matches(Regex("^\\d{6}$"))
}

fun HttpServletRequest.getIdFromToken():Int{
    var token:String = getHeader(TOKEN_NAME)
    return token?.let {
        TokenManager.getIDFromToken(it)
    }
}