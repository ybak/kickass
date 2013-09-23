package org.kickass.web.vo;

import com.google.gson.Gson;

public class HomeResponse {
    private static final Gson gson = new Gson();
    
    public static final String ERROR_500 = new HomeResponse(500).toString();
    public static final String ERROR_501 = new HomeResponse(501).toString();
    
    
    private int code;
    private String token;
    private Long uid;

    public HomeResponse() {
    }

    public HomeResponse(int code) {
        this.code = code;
    }

    public HomeResponse(int code, String token, Long uid) {
        this.code = code;
        this.token = token;
        this.uid = uid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

}
