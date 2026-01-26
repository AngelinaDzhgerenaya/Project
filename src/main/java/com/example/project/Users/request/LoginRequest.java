package com.example.project.users.request;

import com.example.project.users.entity.UserEntity;
import com.example.project.users.exception.BadRequestException;
import com.example.project.users.repository.UserRepository;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String phoneNumber;
    private String passportId;
    private String email;
    private String password;
    private String username;

    // Метод для проверки на валидность логина
    public void validate(UserRepository userRepository, PasswordEncoder passwordEncoder) throws BadRequestException {
        // Проверка, что хотя бы одно поле для логина не пустое
        if (email == null || email.isBlank()) {
            if (phoneNumber == null || phoneNumber.isBlank()) {
                if (passportId == null || passportId.isBlank()) {
                    throw new BadRequestException("Необходимо ввести хотя бы один из логинов: Email, Телефон или Паспорт");
                }
            }
        }

        UserEntity user = null;
        // Проверка существования пользователя в базе данных
        if (email != null && !email.isBlank()) {
            Optional<UserEntity> check = userRepository.findByEmail(email);
            if (check.isEmpty()) {
                throw new BadRequestException("Пользователь с таким email не найден");
            }
            user = check.get();
        }

        if (phoneNumber != null && !phoneNumber.isBlank()) {
            Optional<UserEntity> check = userRepository.findByPhoneNumber(phoneNumber);
            if (check.isEmpty()) {
                throw new BadRequestException("Пользователь с таким номером телефона не найден");
            }
            user = check.get();
        }

        if (passportId != null && !passportId.isBlank()) {
            Optional<UserEntity> check = userRepository.findByPassportId(passportId);
            if (check.isEmpty()) {
                throw new BadRequestException("Пользователь с таким паспортом не найден");
            }
            user = check.get();
        }

        // Проверка пароля
        if (password != null && !password.isBlank()) {
            // Сравниваем введенный пароль с хешированным паролем в базе данных
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadRequestException("Неверный пароль");
            }
        } else {
            throw new BadRequestException("Пароль обязателен для ввода");
        }

        username = user.getEmail();
    }


}
