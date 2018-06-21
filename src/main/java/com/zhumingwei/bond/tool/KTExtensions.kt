package com.zhumingwei.bond.tool

/**
 * @author zhumingwei
 * @date 2018/6/21 下午2:14
 */
infix fun <A, B, C> Pair<A, B>.tri(that: C): Triple<A, B, C> = Triple(this.first, this.second, that)
