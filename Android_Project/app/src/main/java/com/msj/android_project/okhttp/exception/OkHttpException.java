package com.msj.android_project.okhttp.exception;

public  class OkHttpException extends Exception {

    private static final long serialVersionUID = 1L;

    private int code;

    private Object msg;

    public OkHttpException(int ecode, Object emsg) {
        this.code = ecode;
        this.msg = emsg;
    }

    public int getCode() {
        return code;
    }

    public Object getMsg() {
        return msg;
    }
}
