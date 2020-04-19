package com.example.sphtech.model.http;

public class ApiException extends RuntimeException {

    public int code;
    public String message;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
