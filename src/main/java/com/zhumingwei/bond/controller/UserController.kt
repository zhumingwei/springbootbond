package com.zhumingwei.bond.controller


import com.zhumingwei.bond.entity.Account
import com.zhumingwei.bond.entity.User
import com.zhumingwei.bond.service.UserService
import com.zhumingwei.bond.tool.ResponseCode
import com.zhumingwei.bond.tool.ServletUtil
import com.zhumingwei.bond.tool.response.BaseResponse
import org.apache.ibatis.annotations.Param
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.HashMap

@RestController
@RequestMapping(path = arrayOf("/user"))
class UserController : BaseController() {

    @Autowired
    internal var userService: UserService? = null

    @RequestMapping(path = arrayOf("/info"), method = arrayOf(RequestMethod.POST))
    fun info(request: HttpServletRequest, response: HttpServletResponse, @Param("id") id: Int): String {
        val user = userService!!.queryById(id)
        return user?.toString() ?: "获取信息失败"
    }

    @RequestMapping(path = arrayOf("/login"), method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    fun login(request: HttpServletRequest, response: HttpServletResponse, password: String, @Param("phonenum") phonenum: String) {
        val account = userService!!.loginByPwd(phonenum, password)
        val result = BaseResponse<Account>()
        if (account != null) {
            ResponseCode.SUCCESS.setCodeMessage(result)
            account.clear()
            account.userdetail.clear()
            result.setItem(account)
            HashMap
            result.setItem()
        } else {
            ResponseCode.LOGIN_ERROR.setCodeMessage(result)
        }

        ServletUtil.createErrorResponse(result, response)
    }


}
