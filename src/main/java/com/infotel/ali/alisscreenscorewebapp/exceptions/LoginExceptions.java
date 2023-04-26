package com.infotel.ali.alisscreenscorewebapp.exceptions;

public class LoginExceptions {

    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
}
