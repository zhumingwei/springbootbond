package com.zhumingwei.bond.tool;

import com.zhumingwei.bond.tool.response.BaseResponse;

/**
 * @author zhumingwei
 * @date 2018/6/17 下午12:09
 */
public enum  ResponseCode {

    REQUEST_ERROR("请求参数问题错误",50001),
    LOGIN_ERROR("用户名或密码错误",50002),
    TOKEN_ERROR("TOKEN 失效",50003),
    PHONE_ERROR("错误的手机号",50004),
    SEND_MESSAGE_ERROR("验证啊发送失败",50005),
    MESSAGE_VALIDATE_ERROR("验证码错误",50006),
    REGISTER_ERROR("注册失败",50007),
    MODIFY_ERROR("修改用户信息失败",50008),
    SUCCESS("success",20000);

    private ResponseCode(String name, int index) {
        this.message = name;
        this.code = index;
    }
    private String message;
    private int code;

    public String getMessage(){
        return message;
    }

    public int getCode(){
        return code;
    }

    public void setCodeMessage(BaseResponse response){
        response.setCode(this.code);
        response.setMessage(this.message);
    }
}
