package com.zhumingwei.bond

import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment


/**
 * @author zhumingwei
 * @date 2018/6/18 下午6:00
 */

const val LOGIN_URL = "/user/login"
const val USER_INFO_URL = "/user/info"
const val USER_UPDATE = "/user/update"
const val REGISTER_URL = "/user/register"
const val MODIFY_PASSWORD = "/user/modify/password"

const val TOKEN_NAME = "IVANKA"
@Autowired
private lateinit var env: Environment

// todo 配置文件配置
class BondConstant {

    companion object  {
        val DEFAULT_AVATAR =  "http://p9yjgmoug.bkt.clouddn.com/default"
        val NIU7_ACCESSKEY = "uB-9nDVe9nZ6b-yl2EbEZf-WZHWICYwl_Se_whE5"
        val NIU7_SECRETKEY = "N7klHPuBzJhI-EGaGbZs1KFtbMai7o3vKRgh2l--"
        val MEG_ACCESSKEYID = "LTAISJkwKHeaRnnw"
        val MEG_ACCESSKEYSECRET = "SS4oWFYnkKlfmBdAfLCdHnH1mHp6V3"
    }
}
