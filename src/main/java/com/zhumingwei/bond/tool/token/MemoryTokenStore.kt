package com.zhumingwei.bond.tool.token

import com.zhumingwei.bond.tool.TokenManager

import java.util.Date
import java.util.HashMap

/**
 * @author zhumingwei
 * @date 2018/6/20 下午10:30
 */
class MemoryTokenStore : ITokenStore {
    val cacheIDtoExTime = HashMap<Int, Pair<String, Date>>()

    override fun put(uid: Int, tokendate: Pair<String, Date>) {
        cacheIDtoExTime[uid] = tokendate
    }

    override fun get(uid: Int): Pair<String, Date>? {
        return cacheIDtoExTime.get(uid)
    }
}
