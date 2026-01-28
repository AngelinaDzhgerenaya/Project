package com.example.project.form.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormAlreadyExistException extends Exception {
    public FormAlreadyExistException() {
        super("У вас уже есть заявка");
    }

    // Конструктор с кастомным сообщением
    public FormAlreadyExistException(String message) {
        super(message);
    }
}
