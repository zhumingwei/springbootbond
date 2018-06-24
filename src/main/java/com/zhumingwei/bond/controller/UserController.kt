package com.zhumingwei.bond.controller


import com.zhumingwei.bond.*
import com.zhumingwei.bond.entity.Account
import com.zhumingwei.bond.entity.User
import com.zhumingwei.bond.service.UserService
import com.zhumingwei.bond.tool.*
import com.zhumingwei.bond.tool.TokenManager.checkUserExTimeAndTokenRight
import com.zhumingwei.bond.tool.TokenManager.refreshExTime
import com.zhumingwei.bond.tool.msm.MessageManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 用户系统
 * 1，登录 ok
 * 2，注册 ok
 * 3，修改用户信息 ok
 * 4，修改密码
 * 5，修改头像（主要是依赖图片服务）ok
 * 6，获取用户信息 ok
 */
@RestController
class UserController : BaseController() {

    @Autowired
    private lateinit var userService: UserService


    @RequestMapping(path = [LOGOUT_URL],method = [RequestMethod.POST])
    fun logout(request: HttpServletRequest, response: HttpServletResponse){
        var id = request.getIdFromToken()
        if (id != 0){
            responseSuccess(response,"成功")
        }else{
            responseSuccess(response,"成功")
        }
    }

    //有password 用密码登录 没有用验证码登录
    @RequestMapping(path = [LOGIN_URL], method = [RequestMethod.POST])
    fun login(request: HttpServletRequest, response: HttpServletResponse) {
        val phonenum = request.getNotNullParameter("phonenum");
        val code = request.getNotNullParameter("code")
        val password = request.getNotNullParameter("password")

        var account: Account? = null

        if (!phonenum.isPhoneNum()) {
            responseError(response, ResponseCode.REQUEST_ERROR)
        }

        if (!password.isEmpty()) {
            account = userService.getAccountByPnumPwd(phonenum, password)
        } else {
            if (!code.isMsgCode() || !MessageManager.getLoginCode(phonenum).equals(code)) {
                responseError(response, ResponseCode.MESSAGE_VALIDATE_ERROR)
            }
            MessageManager.deleteLoginCode(phonenum)
            account = userService.getAccountBypnum(phonenum)
        }

        account?.let {
            val token = TokenManager.generateToken(account)
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


    @RequestMapping(path = [REGISTER_URL], method = [(RequestMethod.POST)])
    fun register(request: HttpServletRequest, response: HttpServletResponse) {
        val phonenum = request.getNotNullParameter("phonenum")
        val code = request.getNotNullParameter("code")
        val password = request.getNotNullParameter("password")

        if (!phonenum.isPhoneNum() || !code.isMsgCode() || password.isEmpty()) {
            responseError(response, ResponseCode.REQUEST_ERROR)
        }
        if (!MessageManager.getRegisterCode(phonenum).equals(code)) {
            responseError(response, ResponseCode.MESSAGE_VALIDATE_ERROR)
        }
        MessageManager.deleteRegisterCode(phonenum)

        val account: Account = Account().apply {
            this.password = EncryptUtil.getSaltMD5(password)
            this.phonenumber = phonenum
            this.userdetail = User().apply {
                nickname = "手机用户" + phonenumber.substring(7)
                cid = 0
                is_delete = 0
                user_level = 0
                user_state = 0
            }
        }
        val i = userService.register(account)
        if (i == 0) {
            responseError(response, ResponseCode.REGISTER_ERROR)
        } else {
            responseSuccess(response, "注册成功");
        }
    }

    @RequestMapping(path = [USER_INFO_URL], method = [RequestMethod.POST, RequestMethod.GET])
    fun info(request: HttpServletRequest, response: HttpServletResponse) {
        val token = request.getHeader(TOKEN_NAME)
        val uid = TokenManager.getIDFromToken(token)
        if (checkUserExTimeAndTokenRight(uid, token)) {
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

    @RequestMapping(path = [USER_UPDATE], method = [(RequestMethod.POST)])
    fun updateUser(request: HttpServletRequest, response: HttpServletResponse, @RequestBody user: User) {
        val uid = request.getIdFromToken()
        val du = userService.queryById(uid)
        var oldavatar: String? = null
        if (!user.avatar.isEmpty() && user.avatar != du.avatar) {
            oldavatar = du.avatar
            du.avatar = user.avatar

        }
        if (!user.nickname.isEmpty()) {
            du.nickname = user.nickname
        }
        du.updateby = uid.toLong()
        val i = userService.updateUser(du)
        if (i != 0) {
            responseSuccess(response, "修改成功")
        } else {
            responseError(response, ResponseCode.MODIFY_ERROR)
        }
        oldavatar?.let {
            FileController.delete(oldavatar)
        }
    }


    @RequestMapping(path = [MODIFY_PASSWORD], method = [(RequestMethod.POST)])
    fun modifyPassword(request: HttpServletRequest, response: HttpServletResponse) {
        val phonenum = request.getNotNullParameter("phonenum")
        val code = request.getNotNullParameter("code")
        val password = request.getNotNullParameter("password")
        val uid = request.getIdFromToken()
        if (!phonenum.isPhoneNum() || !code.isMsgCode() || password.isEmpty()) {
            responseError(response, ResponseCode.REQUEST_ERROR)
        }

        if (MessageManager.getChangePwdCode(phonenum) != code) {
            responseError(response, ResponseCode.MESSAGE_VALIDATE_ERROR)
        }
        MessageManager.deleteChangePwdCode(phonenum)
        var i = userService.updatePassword(Account().apply {
            this.uid = uid
            this.password = password
            this.updateby = uid.toLong()
        })
        if (i != 0) {
            responseSuccess(response, "修改成功")
        } else {
            responseError(response, ResponseCode.MODIFY_ERROR)
        }
    }

    @RequestMapping(path = [MODIFY_PASSWORD_BYPWD], method = [(RequestMethod.POST)])
    fun modifyPasswordByPwd(request: HttpServletRequest, response: HttpServletResponse) {
        val phonenum = request.getNotNullParameter("phonenum")
        val code = request.getNotNullParameter("oldpwd")
        val password = request.getNotNullParameter("pwd")
        val uid = request.getIdFromToken()
        if (!phonenum.isPhoneNum() || code.isEmpty() || password.isEmpty()) {
            responseError(response, ResponseCode.REQUEST_ERROR)
        }
        var account:Account? = userService.getAccountByPnumPwd(phonenum,password);
        var i = 0
        account?.let {
            i = userService.updatePassword(Account().apply {
                this.uid = uid
                this.password = password
                this.updateby = uid.toLong()
            })
        }

        if (i != 0) {
            responseSuccess(response, "修改成功")
        } else {
            responseError(response, ResponseCode.MODIFY_ERROR)
        }
    }

}
