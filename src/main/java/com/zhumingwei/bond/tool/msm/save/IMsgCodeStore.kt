package com.zhumingwei.bond.tool.msm.save

import java.util.*

/**
 *
 *   @author zhumingwei
 *   @date 2018/6/21 下午1:57
 */

interface IMsgCodeStore {

    fun put(uid: Int, codedate: Pair<String, Date>)

    fun get(uid: Int): Pair<String, Date>?
    fun delete(uid: Int)

}

class MemoryCodeStore : IMsgCodeStore {
    override fun delete(uid: Int) {
        cacheIDtoCodedate.remove(uid)
    }

    val cacheIDtoCodedate = HashMap<Int, Pair<String, Date>>()
    override fun get(uid: Int): Pair<String, Date>? {
        return cacheIDtoCodedate.get(uid)
    }

    override fun put(uid: Int, codedate: Pair<String, Date>) {
        cacheIDtoCodedate.put(uid,codedate)
    }

}
