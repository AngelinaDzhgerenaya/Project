package com.example.project.users.controller;

import com.example.project.users.entity.UserEntity;
import com.example.project.users.exception.BadRequestException;
import com.example.project.users.exception.UserAlreadyExistException;
import com.example.project.users.repository.UserRepository;
import com.example.project.users.request.LoginRequest;
import com.example.project.users.request.RegistrationRequest;
import com.example.project.users.routes.UserRoutes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        if(check.isPresent());

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
    public String login(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";  // Имя файла index.html, без расширения .html
    }

    @RequestMapping(UserRoutes.ME)
    public String mePage(Authentication authentication, Model model) {
        model.addAttribute("user", authentication.getPrincipal());
        return "me";
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
