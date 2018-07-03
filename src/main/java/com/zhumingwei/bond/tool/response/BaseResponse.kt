package com.zhumingwei.bond.tool.response

/**
 * @author zhumingwei
 * @date 2018/6/17 下午12:08
 */
data class BaseResponse<T>(
        var code: Int = 0,
        var message: String? = null,
        var data: T? = null
) {


}
