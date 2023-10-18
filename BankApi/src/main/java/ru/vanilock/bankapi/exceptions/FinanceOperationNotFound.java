package ru.vanilock.bankapi.exceptions;

public class FinanceOperationNotFound extends Exception{

    public FinanceOperationNotFound() {
        super("Operation not found");
    }
}
