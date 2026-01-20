package com.example.project.Users.controller;

import com.example.project.Users.exception.BadRequestException;
import com.example.project.Users.exception.UserAlreadyExistException;
import com.example.project.Users.request.RegistrationRequest;
import com.example.project.Users.routes.UserRoutes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping()
public class UserApiController {

    private  RegistrationController registrationController;

    @GetMapping()
    public String index() {
        return "index";  // Имя файла index.html, без расширения .html
    }

    @GetMapping("/not-secured/registration")
    public String registration() {
        return "registration";  // Имя файла index.html, без расширения .html
    }

    @PostMapping(UserRoutes.REGISTRATION)
    public String registrationPost(@RequestBody RegistrationRequest request) throws BadRequestException, UserAlreadyExistException {
        registrationController.registration(request);
        return "index";
    }
}
