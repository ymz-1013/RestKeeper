package com.restkeeper.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果通用对象封装
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<D> implements Serializable {

    private static final long serialVersionUID = -2145852753254617071L;

    //是否成功
    private boolean success;

    //异常信息
    private String message;

    //200表示正常,300接口返回false，400调用异常
    private int code;

    //泛型，用来存储原有controller返回值
    private D data;

    public BaseResponse() {
        this.code = 200;
        this.message = "success";
        this.success = true;
    }


    public BaseResponse(D data){
        this();
        this.data = data;
    }

    public BaseResponse(boolean success){
        this();
        this.success = success;
        if(success == false){
            this.message = "操作失败";
            this.code = 300;
        }
    }

    public BaseResponse(boolean success,String msg){
        this(success);
        this.message = msg;
    }

    public BaseResponse(int code,String msg){
        this(false,msg);
        this.code = code;
    }
}
