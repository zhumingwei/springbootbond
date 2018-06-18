package com.zhumingwei.bond.tool;

/**
 * @author zhumingwei
 * @date 2018/6/17 下午12:08
 */
public class BaseResponse<T> {
    private int code;
    private String message;
    private T item;

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

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
