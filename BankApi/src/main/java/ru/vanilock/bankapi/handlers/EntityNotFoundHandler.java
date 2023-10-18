package ru.vanilock.bankapi.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vanilock.bankapi.exceptions.FinanceOperationNotFound;
import ru.vanilock.bankapi.exceptions.UserNotFoundException;
import ru.vanilock.bankapi.model.ResponseJson;

@RestControllerAdvice
public class EntityNotFoundHandler{

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseJson> handleUserNotFound(Exception e){
        return new ResponseEntity<ResponseJson>(new ResponseJson(0, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FinanceOperationNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleFinanceOperationNotFound(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
