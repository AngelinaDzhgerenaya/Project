package com.example.project.form.volunteer.request;

import lombok.Data;

@Data
public class EditVolunteerRequest {
    private Long id;

    private Long userId;

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
    private Boolean Active;


}
