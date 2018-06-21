package com.zhumingwei.bond.tool.token

import com.zhumingwei.bond.tool.TokenManager
import java.util.*

/**
 * @author zhumingwei
 * @date 2018/6/20 下午10:29
 */
interface ITokenStore{

    fun put(uid: Int, tokendate: Pair<String, Date>)

    fun get(uid: Int): Pair<String, Date>?

}


