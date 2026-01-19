package com.example.project.Users.controller;

import com.example.project.Users.entity.UserEntity;
import com.example.project.Users.exception.BadRequestException;
import com.example.project.Users.exception.UserAlreadyExistException;
import com.example.project.Users.repository.UserRepository;
import com.example.project.Users.request.RegistrationRequest;
import com.example.project.Users.response.UserResponse;
import com.example.project.Users.routes.UserRoutes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserApiControlller {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @PostMapping(UserRoutes.REGISTRATION)
    public UserResponse registration(@RequestBody RegistrationRequest request) throws BadRequestException, UserAlreadyExistException {
        request.validate();

        Optional<UserEntity> check = userRepository.findByEmail(request.getEmail());
        if(check.isPresent()) throw new UserAlreadyExistException();
        check = userRepository.findByPhoneNumber(request.getEmail());
        if(check.isPresent()) throw new UserAlreadyExistException();
        check = userRepository.findByPassportId(request.getEmail());
        if(check.isPresent()) throw new UserAlreadyExistException();



        UserEntity client = UserEntity.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .phoneNumber(request.getPhoneNumber())
                .passportId(request.getPassportId())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        client = userRepository.save(client);
        return UserResponse.of(client);
    }
}
