package com.hrm.common.entity;


/**
 * @ClassName Result
 * @Description 返回结果实体类
 * @Author Julie
 * @Date 2019/9/14 16:50
 * @Version 1.0
 */

//@Data
//    @NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    private boolean success;//是否成功
    private Integer code;// 返回码
    private String message;//返回信息
    private Object data;// 返回数据


    public Result(ResultCode code) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
    }

    public Result(ResultCode  resultCode,Object data){
        this.success  = resultCode.success;
        this.code = resultCode.code;
        this.message = resultCode.message;
        this.data = data;

    }

    public Result(Integer code,String message,boolean success){
        this.code = code;
        this.message = message;
        this.success = success;

    }

    public static Result SUCCESS(){

        return new Result(ResultCode.SUCCESS);
    }

    public static Result ERROR(){

        return new Result(ResultCode.SERVER_ERROR);
    }

    public static Result FAIL(){
        return new Result(ResultCode.FAIL);
    }
}
