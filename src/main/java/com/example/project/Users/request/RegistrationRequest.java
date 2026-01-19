package com.example.project.Users.request;

import com.example.project.Users.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
        if (lastName == null|| lastName.isBlank()) throw new BadRequestException();
        if (firstName == null|| firstName.isBlank()) throw new BadRequestException();
        if (phoneNumber == null || phoneNumber.isBlank()) throw new BadRequestException();
        if (passportId == null || passportId.isBlank()) throw new BadRequestException();
        if (email == null || email.isBlank()) throw new BadRequestException();
        if (password == null || password.isBlank()) throw new BadRequestException();
    }
}
