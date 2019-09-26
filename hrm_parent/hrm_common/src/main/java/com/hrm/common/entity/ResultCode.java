package com.hrm.common.entity;

/**
 * @ClassName ResultCode
 * @Description 返回状态码
 * @Author Julie
 * @Date 2019/9/14 16:50
 * @Version 1.0
 */
public enum ResultCode {

    SUCCESS(true,10000,"操作成功"),
//---系统错误返回码-----

    FAIL(false,10001,"操作失败!"),

    UNAUTHENTICATED(false,10002,"您还未登录"),

    UNAUTHORISE(false,10003,"权限不足"),

    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),

//---用户操作回码----
    MOBILE_OR_PWD_ERROR(false,20001,"用户名或密码错误!");

//---企业操作返回码----
//---权限操作返回码----
//---其他操作返回码----

    /**
     * 操作是否成功
     */
    boolean success;

    /**
     * 操作代码
     */
    int code;

    /**
     * 提示信息
     */
    String message;

    ResultCode(boolean success,int code,String message){

        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
}
