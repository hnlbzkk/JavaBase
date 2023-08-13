package com.xyz.base.param;

/**
 * 日志记录字段
 *
 * @author ZKKzs
 **/
public record LogParam(String time, String url, String method, String query, String code, String message, String ip, String host) {
    @Override
    public String toString() {
        return "{" +
                "\"time\": \"" + time + "\"" +
                "\"url\": \"" + url + "\"" +
                ", \"method\": \"" + method + "\"" +
                ", \"query\": \"" + query + "\"" +
                ", \"code\": \"" + code + "\"" +
                ", \"message\": \"" + message + "\"" +
                ", \"ip\": \"" + ip + "\"" +
                ", \"host\": \"" + host + "\"" +
                "}";
    }
}
