package com.yuki.bigdata.dto;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class ActionRest {

    private int code;
    private String msg;
    private Object data;
    private Map<String, Object> ext;

    /**
     * 成功编码
     */
    private static final int SUCCESS_CODE = 0;
    /**
     * 失败编码
     */
    private static final int ERROR_CODE = 1;

    private static final int PARAMS_ERROR = 2;

    public ActionRest() {
        this.code = SUCCESS_CODE;
        this.msg = "操作成功";
    }

    public static ActionRest error() {
        return error(ERROR_CODE, "系统异常，请联系管理员");
    }

    public static ActionRest error(Throwable e) {
        return error(ERROR_CODE, getString(e));
    }

    public static ActionRest error(String msg) {
        return error(ERROR_CODE, msg);
    }


    public static ActionRest paramsError() {
        return error(PARAMS_ERROR, "参数错误！");
    }

    public static ActionRest error(int code, String msg) {
        ActionRest result = new ActionRest();
        result.code = code;
        result.msg = msg;
        return result;
    }

    public static ActionRest sucess(Object data) {
        ActionRest result = new ActionRest();
        result.setData(data);
        return result;
    }

    public static ActionRest sucess() {
        return new ActionRest();
    }

    public ActionRest put(String key, Object value) {
        this.ext.put(key, value);
        return this;
    }

    public ActionRest putMap(Map<String, Object> map) {
        this.ext.putAll(map);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getExt() {
        return ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    private static String getString(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
}
