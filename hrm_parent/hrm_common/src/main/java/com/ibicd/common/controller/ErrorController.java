package com.ibicd.common.controller;

import com.ibicd.common.entity.Result;
import com.ibicd.common.entity.ResultCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ErrorController
 * @Description 异常控制器
 * @Author Julie
 * @Date 2019/10/8 21:22
 * @Version 1.0
 */
@RestController
@CrossOrigin
public class ErrorController {

    @RequestMapping(value = "autherror")
    public Result autherror(int code){

        return code==1?new Result(ResultCode.UNAUTHENTICATED):new Result(ResultCode.UNAUTHORISE);
    }
}
