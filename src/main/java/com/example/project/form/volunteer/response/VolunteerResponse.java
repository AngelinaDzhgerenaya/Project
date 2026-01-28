package com.example.project.form.volunteer.response;

import com.example.project.form.volunteer.entity.VolunteerEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class VolunteerResponse {
    protected Long id;

    protected String fullName;
    protected String age;
    protected String contactPhone;
    protected String otherContact;
    protected String city;
    protected String volunteerExperience;
    protected String availability;
    protected String preferredGroup;
    protected String availableHelp;
    protected String additionalInformation;

    public static VolunteerResponse of(VolunteerEntity item) {
        return VolunteerResponse.builder()
                .id(item.getId())
                .fullName(item.getFullName())
                .age(item.getAge())
                .contactPhone(item.getContactPhone())
                .otherContact(item.getOtherContact())
                .city(item.getCity())
                .volunteerExperience(item.getVolunteerExperience())
                .availability(item.getAvailability())
                .preferredGroup(item.getPreferredGroup())
                .availableHelp(item.getAvailableHelp())
                .additionalInformation(item.getAdditionalInformation())
                .build();
    }
}
