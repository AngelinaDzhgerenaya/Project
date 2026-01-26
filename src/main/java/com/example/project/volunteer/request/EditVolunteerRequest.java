package com.example.project.volunteer.request;

import com.example.project.volunteer.entity.VolunteerEntity;
import lombok.Data;

@Data
public class EditVolunteerRequest {
    private Long id;
    //@Column(nullable = false, unique = true)
    //private String applicationNumber;

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


}
