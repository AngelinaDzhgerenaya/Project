package com.example.project.users.request;
import com.example.project.users.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ForgotPasswordRequest {

    // email -> код
    private final Map<String, String> codes = new ConcurrentHashMap<>();
    // email -> время истечения
    private final Map<String, Instant> expire = new ConcurrentHashMap<>();
    @Autowired
    private final EmailService emailService;

    // ===== создать код =====
    public void sendCode(String email) {
        String code = String.valueOf(100000 + new Random().nextInt(900000));
        codes.put(email, code);
        expire.put(email, Instant.now().plusSeconds(600)); // 10 минут

        String subject = "Код восстановления пароля";
        String text = "Ваш код для восстановления пароля: " + code;

        emailService.sendSimpleMessage(email, subject, text);
    }

    // ===== проверить код =====
    public boolean verifyCode(String email, String code) {

        String saved = codes.get(email);
        Instant exp = expire.get(email);

        if (saved == null || exp == null) return false;

        if (Instant.now().isAfter(exp)) {
            codes.remove(email);
            expire.remove(email);
            return false;
        }

        return saved.equals(code);
    }

    // ===== удалить после использования =====
    public void clear(String email) {
        codes.remove(email);
        expire.remove(email);
    }

    public String validate(String password)  {
        // Проверка длины пароля
        if (password.length() < 5 || password.length() > 12) {
            return "Неверная длина пароля";
        }

        // Проверка запрещённых символов в пароле
        if (!password.matches("^[^<>\\[\\]{}\\\\|;:А-Яа-яЁё]+$")) {
            return "Пароль содержит запрещённые символы";
        }
        return null;
    }
}
