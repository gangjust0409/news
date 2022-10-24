package com.just.news.exception;

public class SqlConnectionException extends RuntimeException {
    public SqlConnectionException() {
        super("connection error, message：null！");
    }
}
