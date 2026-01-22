package com.example.project.Users.request;

import com.example.project.Users.exception.BadRequestException;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String passportId;
    private String email;
    private String password;

    public void validate() throws BadRequestException {
        // Проверка на обязательные поля
        if (lastName == null || lastName.isBlank()) throw new BadRequestException("Фамилия обязательна для заполнения");
        if (firstName == null || firstName.isBlank()) throw new BadRequestException("Имя обязательно для заполнения");
        if (phoneNumber == null || phoneNumber.isBlank()) throw new BadRequestException("Номер телефона обязателен для заполнения");
        if (passportId == null || passportId.isBlank()) throw new BadRequestException("Номер паспорта обязателен для заполнения");
        if (email == null || email.isBlank()) throw new BadRequestException("Email обязателен для заполнения");
        if (password == null || password.isBlank()) throw new BadRequestException("Пароль обязателен для заполнения");

        // Проверка фамилии
        if (!firstName.matches("^[A-Za-zА-Яа-яЁё]+$")) {
            throw new BadRequestException("Имя может содержать только буквы русского и латинского алфавита");
        }

        // Проверка имени
        if (!lastName.matches("^[A-Za-zА-Яа-яЁё]+$")) {
            throw new BadRequestException("Фамилия может содержать только буквы русского и латинского алфавита");
        }

        // Проверка номера телефона
        if (!phoneNumber.matches("^\\+7\\d{10}$")) {
            throw new BadRequestException("Такого номера не существует");
        }

        // Проверка номера паспорта
        if (!passportId.matches("^\\d{10}$")) {
            throw new BadRequestException("Неверные серия и номер паспорта");
        }

        // Проверка email
        if (!email.matches("^[A-Za-z0-9._%+-]+@(mail\\.ru|gmail\\.com)$")) {
            throw new BadRequestException("Неверный формат email");
        }

        // Проверка длины пароля
        if (password.length() < 5 || password.length() > 12) {
            throw new BadRequestException("Неверная длина пароля");
        }

        // Проверка запрещённых символов в пароле
        if (!password.matches("^[^<>\\[\\]{}\\\\|;:А-Яа-яЁё]+$")) {
            throw new BadRequestException("Пароль содержит запрещённые символы");
        }
    }
}
