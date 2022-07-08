package com.lzk.originalemailapi.base;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private int count = 0;

    private String msg;

    private int code;

    private T data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseResult(){}

    public BaseResult(Integer code, String msg,T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public String toJson() {
        return JSON.toJSONString(this);
    }


    @Override
    public String toString() {
        return "BaseResult{" +
                "id=" + id +
                ", count=" + count +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
