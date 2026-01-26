package com.example.project.volunteer.request;

import com.example.project.users.exception.BadRequestException;
import com.example.project.volunteer.entity.VolunteerEntity;
import lombok.Data;

@Data
public class CreateVolunteerRequest {
    private String fullName;
    private String age;
    private String contactPhone;
    private String otherContact;
    private String city;
    private String volunteerExperience;
    private String availability;
    private String preferredGroup;
    private String availableHelp;
    private String additionalInformation;
    public void validate() throws BadRequestException {
        if (fullName == null || fullName.isBlank()) throw new BadRequestException("Ф.И.О. обязательно для заполнения");
        if (age == null || age.isBlank()) throw new BadRequestException("Возраст обязателен для заполнения");
        if (contactPhone == null || contactPhone.isBlank()) throw new BadRequestException("Номер обязателен для заполнения");
        if (city == null || city.isBlank()) throw new BadRequestException("Город обязателен для заполнения");
    }

    public VolunteerEntity entity(){
        return VolunteerEntity.builder()
                .fullName(fullName)
                .age(age)
                .contactPhone(contactPhone)
                .otherContact(otherContact)
                .city(city)
                .volunteerExperience(volunteerExperience)
                .availability(availability)
                .preferredGroup(preferredGroup)
                .availableHelp(availableHelp)
                .additionalInformation(additionalInformation)
                .build();
    }
}
