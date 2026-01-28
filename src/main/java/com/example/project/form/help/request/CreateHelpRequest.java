package com.example.project.form.help.request;

import com.example.project.form.exception.BadRequestException;
import com.example.project.form.help.entity.HelpEntity;
import com.example.project.form.volunteer.entity.VolunteerEntity;
import lombok.Data;

@Data
public class CreateHelpRequest {

    protected String fullName;
    protected String age;
    protected String contactPhone;
    protected String otherContact;
    protected String city;
    protected String availability;
    protected String helpGroup;
    protected String personCondition;
    protected String helpNeeded;
    protected String additionalInformation;

    public void validate() throws BadRequestException {
        if (fullName == null || fullName.isBlank()) throw new BadRequestException("Ф.И.О. обязательно для заполнения");
        if (age == null || age.isBlank()) throw new BadRequestException("Возраст обязателен для заполнения");
        if (contactPhone == null || contactPhone.isBlank()) throw new BadRequestException("Номер обязателен для заполнения");
        if (city == null || city.isBlank()) throw new BadRequestException("Город обязателен для заполнения");
    }

    public HelpEntity entity(){
        return HelpEntity.builder()
                .fullName(fullName)
                .age(age)
                .contactPhone(contactPhone)
                .otherContact(otherContact)
                .city(city)
                .availability(availability)
                .helpGroup(helpGroup)
                .personCondition(personCondition)
                .helpNeeded(helpNeeded)
                .additionalInformation(additionalInformation)
                .build();
    }
}
