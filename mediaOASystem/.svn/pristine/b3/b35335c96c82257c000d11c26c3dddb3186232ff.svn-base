package com.yuyu.soft.util;

/**
 * 系统业务异常类
 *                       
 * @Filename: BusinessException.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 6832695224568830049L;

    private Integer status;
    
    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(String message, Integer status) {
        super(message);
        this.setStatus(status);
    }
    

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
