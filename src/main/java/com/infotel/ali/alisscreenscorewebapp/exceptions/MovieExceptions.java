package com.infotel.ali.alisscreenscorewebapp.exceptions;

public class MovieExceptions {

    public static class MovieExistsException extends RuntimeException {
        public MovieExistsException(String message) {
            super(message);
        }
    }
}
