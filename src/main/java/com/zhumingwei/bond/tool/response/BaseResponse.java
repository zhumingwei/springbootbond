package com.zhumingwei.bond.tool.response;

/**
 * @author zhumingwei
 * @date 2018/6/17 下午12:08
 */
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public BaseResponse(){

    }
    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
