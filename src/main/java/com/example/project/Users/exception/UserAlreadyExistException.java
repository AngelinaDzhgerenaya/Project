package com.example.project.Users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException() {
        super("Пользователь уже существует");
    }

    // Конструктор с кастомным сообщением
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
