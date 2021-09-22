package com.autoleasing.exception;

public class FileIsNotUploadedException extends Exception {
    String message;

    public FileIsNotUploadedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
