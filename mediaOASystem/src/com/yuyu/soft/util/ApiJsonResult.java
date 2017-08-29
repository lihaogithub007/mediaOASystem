package com.yuyu.soft.util;

import java.io.Serializable;
import java.util.ArrayList;

public class ApiJsonResult<T> implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID     = -9109380696684464301L;

    //正常
    public static final int   STATUS_SUCESS        = 200;

    //403 验证key错误
    public static final int   STATUS_KEYERROR      = 403;

    //500 服务器内部错误
    public static final int   STATUS_SERVERERROR   = 500;

    //400 业务异常
    public static final int   STATUS_BUSINESSERROR = 400;

    public ApiJsonResult() {
    }

    public ApiJsonResult(T rows) {
        this.rows = rows;
    }

    public ApiJsonResult(String errorMessage) {
        this.message = errorMessage;
    }

    private Boolean success = true;

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private T rows;

    public T getRows() {
        return rows == null ? (T) new ArrayList() : rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    private String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Integer status = 200;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
