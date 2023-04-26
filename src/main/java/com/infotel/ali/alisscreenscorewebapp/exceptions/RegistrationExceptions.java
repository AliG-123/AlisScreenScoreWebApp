package com.infotel.ali.alisscreenscorewebapp.exceptions;

public class RegistrationExceptions {

    public static class UsernameTakenException extends RuntimeException {
        public UsernameTakenException(String message) {
            super(message);
        }
    }

    public static class EmailTakenException extends RuntimeException {
        public EmailTakenException(String message) {
            super(message);
        }
    }
}
