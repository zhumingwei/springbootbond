package com.zhumingwei.bond.tool.msm.save

import java.util.*

/**
 *
 *   @author zhumingwei
 *   @date 2018/6/21 下午1:57
 */

interface IMsgCodeStore {

    fun put(phone: String, codedate: Pair<String, Date>)

    fun get(phone: String): Pair<String, Date>?
    fun delete(phone: String)

}

class MemoryCodeStore : IMsgCodeStore {
    override fun put(phone: String, codedate: Pair<String, Date>) {
        cacheIDtoCodedate.put(phone,codedate)
    }

    override fun delete(phone: String) {
        cacheIDtoCodedate.remove(phone)
    }

    val cacheIDtoCodedate = HashMap<String, Pair<String, Date>>()
    override fun get(phone: String): Pair<String, Date>? {
        return cacheIDtoCodedate.get(phone)
    }

}
