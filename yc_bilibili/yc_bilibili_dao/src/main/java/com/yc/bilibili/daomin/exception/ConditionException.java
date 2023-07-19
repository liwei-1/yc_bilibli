package com.yc.bilibili.daomin.exception;

public class ConditionException extends RuntimeException{

    //序列版本号
    private static final long serialVersionUID = 1L;
    private String code;
    public  ConditionException(String code,String name){
        super(name);
        this.code = code;
    }
    public ConditionException(String name){
        super(name);
        code = "500";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
