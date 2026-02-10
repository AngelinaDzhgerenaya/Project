package com.example.project.users.request;

import com.example.project.users.exception.BadRequestException;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditUserRequest {
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String passportId;

    public void validate() throws BadRequestException {
        // Проверка на обязательные поля
        if (lastName == null || lastName.isBlank()) throw new BadRequestException("Фамилия обязательна для заполнения");
        if (firstName == null || firstName.isBlank()) throw new BadRequestException("Имя обязательно для заполнения");
        if (phoneNumber == null || phoneNumber.isBlank())
            throw new BadRequestException("Номер телефона обязателен для заполнения");
        if (passportId == null || passportId.isBlank())
            throw new BadRequestException("Номер паспорта обязателен для заполнения");

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
    }
}
