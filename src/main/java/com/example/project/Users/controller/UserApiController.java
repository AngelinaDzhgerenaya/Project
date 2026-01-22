package com.example.project.Users.controller;

import com.example.project.Users.entity.UserEntity;
import com.example.project.Users.exception.BadRequestException;
import com.example.project.Users.exception.UserAlreadyExistException;
import com.example.project.Users.repository.UserRepository;
import com.example.project.Users.request.LoginRequest;
import com.example.project.Users.request.RegistrationRequest;
import com.example.project.Users.response.UserResponse;
import com.example.project.Users.routes.UserRoutes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping()
@RequiredArgsConstructor
public class UserApiController {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;

    @GetMapping()
    public String index() {
        return "index";  // Имя файла index.html, без расширения .html
    }

    @GetMapping("/not-secured/registration")
    public String registration() {
        return "registration";  // Имя файла index.html, без расширения .html
    }

    @PostMapping(UserRoutes.REGISTRATION)
    public String registrationPost(@ModelAttribute RegistrationRequest request) throws BadRequestException, UserAlreadyExistException {
        request.validate();

        Optional<UserEntity> check = userRepository.findByEmail(request.getEmail());
        if(check.isPresent()) throw new UserAlreadyExistException("Пользователь с таким email уже существует");
        check = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if(check.isPresent()) throw new UserAlreadyExistException("Пользователь с таким номером уже существует");
        check = userRepository.findByPassportId(request.getPassportId());
        if(check.isPresent()) throw new UserAlreadyExistException("Пользователь с таким пасспортом уже существует");

        UserEntity client = UserEntity.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .phoneNumber(request.getPhoneNumber())
                .passportId(request.getPassportId())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(client);
        return "redirect:/";
    }

    @GetMapping("/not-secured/login")
    public String login() {
        return "login";  // Имя файла index.html, без расширения .html
    }

    @PostMapping(UserRoutes.LOGIN)
    public String loginPost(@ModelAttribute LoginRequest request) throws BadRequestException, UserAlreadyExistException {
        request.validate(userRepository, passwordEncoder);

        System.out.println(request.getEmail());
        System.out.println(request.getPhoneNumber());
        System.out.println(request.getPassportId());
        System.out.println(request.getPassword());
        System.out.println(request.getUsername());


        // Попытка аутентификации
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Проверяем, прошла ли аутентификация успешно
        if (auth.isAuthenticated()) {
            System.out.println("Authentication successful for user: " + auth.getName());
        } else {
            System.out.println("Authentication failed");
        }

        // Сохраняем аутентификацию в контексте Security
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/api/v1/user/me";

    }

    @GetMapping(UserRoutes.LOGIN)
    public String loginGet(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Пользователь реально авторизован
        if (auth != null && auth.isAuthenticated()) {

            String username = auth.getName(); // логин пользователя
            Optional<UserEntity> user = userRepository.findByEmail(username);
            user.ifPresent(u -> model.addAttribute("userName", u.getFirstName()));

            return "me";
        }

        // Пользователь не авторизован
        return "redirect:" + UserRoutes.NOTME;

    }

    @RequestMapping(UserRoutes.ME)
    public String mePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            Optional<UserEntity> user = userRepository.findByEmail(username);
            user.ifPresent(u -> model.addAttribute("userName", u.getFirstName()));
            return "me"; // Переход на страницу профиля
        }
        return "redirect:/not-secured/notme"; // Перенаправление на страницу ошибки
    }


    @GetMapping(UserRoutes.NOTME)
    public String notmePage( ) {
        return "notme";
    }

    /*
    maria.petrova@gmail.com
    9087654321
    1239874560
    qwerty123
     */


}
