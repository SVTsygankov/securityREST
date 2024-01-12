package ru.SVTsygankov.securityREST.exceptionHandlers;

public class NoSuchUserException extends RuntimeException{
    public NoSuchUserException(String msg) {
        super(msg);
    }
}
