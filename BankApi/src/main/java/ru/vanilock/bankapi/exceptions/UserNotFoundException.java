package ru.vanilock.bankapi.exceptions;

public class UserNotFoundException extends Exception{

    public UserNotFoundException() {
        super("User not found");
    }
}
