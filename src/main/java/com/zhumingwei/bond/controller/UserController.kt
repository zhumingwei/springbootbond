package com.zhumingwei.bond.controller


import com.zhumingwei.bond.*
import com.zhumingwei.bond.entity.Account
import com.zhumingwei.bond.entity.User
import com.zhumingwei.bond.service.UserService
import com.zhumingwei.bond.tool.*
import com.zhumingwei.bond.tool.ServletUtil.responseError
import com.zhumingwei.bond.tool.ServletUtil.responseSuccess
import com.zhumingwei.bond.tool.TokenManager.checkUserExTimeAndTokenRight
import com.zhumingwei.bond.tool.TokenManager.refreshExTime
import org.apache.ibatis.annotations.Param
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class UserController : BaseController() {

    @Autowired
    lateinit var userService: UserService

    @RequestMapping(path = [USER_INFO_URL], method = [(RequestMethod.POST)])
    fun info(request: HttpServletRequest, response: HttpServletResponse) {
        var token = request.getHeader(TOKEN_NAME)
        var uid = TokenManager.getIDFromToken(token)
        if (checkUserExTimeAndTokenRight(uid,token)) {
            val user = userService.queryById(uid)
            user?.let {
                responseSuccess(response, user)
            } ?: run {
                responseError(response, ResponseCode.REQUEST_ERROR)
            }
        } else {
            responseError(response, ResponseCode.TOKEN_ERROR)
        }
    }

    @RequestMapping(path = [LOGIN_URL], method = [(RequestMethod.POST)])
    fun login(request: HttpServletRequest, response: HttpServletResponse, @Param("password") password: String, @Param("phonenum") phonenum: String) {
        var account = userService.loginByPwd(phonenum, password)
        account?.let {
            var token = TokenManager.generateToken(account)
            responseSuccess(response, mapOf<String, Any>(
                    "nickname" to account.userdetail.nickname,
                    "token" to token,
                    "phonenum" to account.phonenumber,
                    "uid" to account.uid,
                    "avatar" to account.userdetail.avatar
            ))
            //添加用户id和token
            refreshExTime(account.uid, token);
        } ?: run {
            responseError(response, ResponseCode.LOGIN_ERROR)
        }
    }

    @RequestMapping(path = [REGISTER_URL],method = [(RequestMethod.POST)])
    fun rigister(request: HttpServletRequest,response: HttpServletResponse){
        //todo 检查验证码是否正确
        var phonenum = request.getParameter("phonenum")
        //todo 检查手机号码正确性
        if (!StringUtil.checkPhoneNum(phonenum)){
            responseError(response,ResponseCode.REQUEST_ERROR)
        }
        var password = request.getParameter("password")
        var account:Account = Account().apply {
            this.password = password
            this.phonenumber = phonenum
            this.createby = 0
            this.userdetail = User().apply {
                nickname = "手机用户"+phonenumber.substring(7)
                cid = 0
                is_delete = 0
            }
        }
        userService.register(account)
        responseSuccess(response,"注册成功");
    }

    //修改密码根据手机号验证码修改
    fun modifyPassword(request: HttpServletRequest, response: HttpServletResponse, @Param("newpwd") newpwd: String, @Param("phonenum") phonenum: String) {
        //todo 先发送验证码接口整理好
    }

    @RequestMapping(path = [USER_UPDATE], method = [(RequestMethod.POST)])
    fun updateUser(request: HttpServletRequest, response: HttpServletResponse, @ModelAttribute user: User) {
        //todo 更新用户数据 参数传递有问题
        LogUtil.loge(user)
    }

}
