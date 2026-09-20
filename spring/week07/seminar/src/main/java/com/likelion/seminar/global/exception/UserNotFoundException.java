package com.likelion.seminar.global.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 사용자입니다.";


    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }


}