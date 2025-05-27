package com.example.projectbase.exception;

public class UsernameExistedException extends RuntimeException {
    public UsernameExistedException() {
        super("Username already existed");
    }
}
