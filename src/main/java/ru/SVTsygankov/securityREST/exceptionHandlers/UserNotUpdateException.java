package ru.SVTsygankov.securityREST.exceptionHandlers;

public class UserNotUpdateException extends RuntimeException{
    public UserNotUpdateException(String msg) {
        super(msg);
    }
}
