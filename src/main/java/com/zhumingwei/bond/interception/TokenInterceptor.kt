package com.zhumingwei.bond.interception

import com.zhumingwei.bond.TOKEN_NAME
import com.zhumingwei.bond.tool.*
import com.zhumingwei.bond.tool.TokenManager.checkUserExTimeAndTokenRight
import com.zhumingwei.bond.tool.TokenManager.refreshExTime
import com.zhumingwei.bond.tool.response.BaseResponse
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author zhumingwei
 * @date 2018/6/18 下午5:29
 */
class TokenInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse?, handler: Any?): Boolean {
        var result = false

        val token = request.getNotNullHeader(TOKEN_NAME)
        var uid = request.getIdFromToken()
        if (!token.isEmpty()) {
            result = checkUserExTimeAndTokenRight(uid, token)
        }

        if (result) {
            token?.let { refreshExTime(uid, token) }
        } else {
            ServletUtil.createResponse(BaseResponse<Any>().apply {
                ResponseCode.TOKEN_ERROR.setCodeMessage(this);
                data = Unit
            }, response!!)
        }
        return result
    }


    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: Exception?) {

    }

    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {

    }

}
