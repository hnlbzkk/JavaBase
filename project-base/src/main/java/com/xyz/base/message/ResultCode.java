package com.xyz.base.message;

/**
 * @author ZKKzs
 **/
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS("SUC0000", "成功"),

    PARAM_INVALID("ERR1001", "参数校验异常"),
    PARAM_NULL("ERR1002", "缺少参数"),
    PARAM_TYPE("ERR1003", "参数格式有误"),
    PARAM_UNSUPPORTED("ERR1004", "不支持的请求类型"),

    USER_NOT_LOGIN("ERR2001", "用户未登录"),
    USER_REGISTER("ERR2002", "该账号注册失败"),
    USER_INVALID("ERR2003", "用户账号无效"),
    USER_NOT_EXIST("ERR2004", "该用户不存在"),
    USER_HAS_EXISTED("ERR2005", "该用户已存在"),
    USER_LOGIN_ERROR("ERR2006", "用户登录失败"),
    USER_LOGOUT_ERROR("ERR2007", "用户退出登录失败"),
    USER_ACCOUNT_VERIFY("ERR2008", "用户验证失败"),
    USER_ACCOUNT_FORBIDDEN("ERR2009", "用户权限不足"),
    USER_MODIFY_ERROR("ERR2010", "用户修改信息失败"),

    IP_FORBIDDEN("ERR3001","该IP被禁止,请稍后再试"),

    OPERATION_ERROR("ERR9997", "操作失败"),
    TOKEN_ERROR("ERR9998", "token验证失败"),
    ERROR("ERR9999", "未知错误"),
    ;

    /**
     * 状态码
     */
    private final String code;
    /**
     * 详细信息
     */
    private final String message;

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
