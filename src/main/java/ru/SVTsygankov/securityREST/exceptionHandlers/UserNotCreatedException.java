package ru.SVTsygankov.securityREST.exceptionHandlers;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String msg) {
            super(msg);

    }
}

