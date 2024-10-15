package com.swpu.contactserver.common.result;

import lombok.Data;

@Data
public class Result<T> {

    private static final Integer HTTP_OK = 200;
    private static final Integer HTTP_ERROR = 500;
    private static final Integer HTTP_UNAUTHORIZED = 401;

    private Integer code;
    private String msg;
    private T data;

    // 链式编程：方法返回的数据类型是当前类本身
    public Result<T> success(Integer code,String msg){
        this.code = code;
        this.msg = msg;
        return this;
    }

    public Result<T> success(String msg){
        this.success(HTTP_OK,msg);//200魔数
        return this;
    }

    public Result<T> success(){
        this.success("操作成功");
        return this;
    }

    public Result<T> error(String msg){
        this.success(HTTP_ERROR,msg);
        return this;
    }
    public Result<T> error(){
        this.error("操作失败");
        return this;
    }

    public Result<T> unAuthorized(String msg){
        this.success(HTTP_UNAUTHORIZED,msg);
        return this;
    }
    public Result<T> unAuthorized(){
        this.error("操作失败");
        return this;
    }
    public Result<T> put(T data){
        this.data = data;
        return this;
    }
}
