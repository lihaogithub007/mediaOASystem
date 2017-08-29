package com.yuyu.soft.util;

public class ResultMsg {

    private boolean result; //处理结果 true：成功 false:失败

    private String  msg;   //处理信息

    private Object  data;  //处理结果集

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
