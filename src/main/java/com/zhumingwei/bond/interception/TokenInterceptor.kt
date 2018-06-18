package com.zhumingwei.bond.interception

import com.zhumingwei.bond.TOKEN_NAME
import com.zhumingwei.bond.tool.ResponseCode
import com.zhumingwei.bond.tool.ServletUtil
import com.zhumingwei.bond.tool.TokenManager
import com.zhumingwei.bond.tool.TokenManager.checkUserExTimeAndTokenRight
import com.zhumingwei.bond.tool.TokenManager.refreshExTime
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author zhumingwei
 * @date 2018/6/18 下午5:29
 */
class TokenInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
        var result = true
        val token = request?.getHeader(TOKEN_NAME)
        token?.let {
            result = checkUserExTimeAndTokenRight(TokenManager.getIDFromToken(token),token);
        } ?: run {
            result = false
        }
        if (result) {
            token?.let { refreshExTime(TokenManager.getIDFromToken(token),token) }
        }else{
            ServletUtil.responseError(response, ResponseCode.TOKEN_ERROR)
        }
        return result
    }



    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: Exception?) {

    }

    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {

    }

}
