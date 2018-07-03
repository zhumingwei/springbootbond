package com.zhumingwei.bond.controller

import com.zhumingwei.bond.tool.ResponseCode
import com.zhumingwei.bond.tool.ServletUtil
import com.zhumingwei.bond.tool.TokenManager
import com.zhumingwei.bond.tool.response.BaseResponse
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author zhumingwei
 * @date 2018/6/17 下午2:22
 */
open class BaseController {

    fun responseError(response: HttpServletResponse?, code: ResponseCode) {
        response?.let {
            ServletUtil.createResponse(BaseResponse<Any>().apply {
                code.setCodeMessage(this)
                this.data = Unit
            }, it)

        }
    }

    fun responseSuccess(response: HttpServletResponse?, data: Any) {
        response?.let {
            ServletUtil.createResponse(BaseResponse<Any>().apply {
                ResponseCode.SUCCESS.setCodeMessage(this)
                this.data = data
            }, it)
        }
    }

    fun responseMessage(response: HttpServletResponse?, o: BaseResponse<*>) {
        response?.let { ServletUtil.createResponse(o, it) }
    }



}
