package com.example.project.form.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FormNotFoundException extends Exception {
    public FormNotFoundException() {
        super("Заявка не найдена");
    }

    // Конструктор с кастомным сообщением
    public FormNotFoundException(String message) {
        super(message);
    }
}
