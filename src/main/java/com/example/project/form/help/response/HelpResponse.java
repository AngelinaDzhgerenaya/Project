package com.example.project.form.help.response;

import com.example.project.form.help.entity.HelpEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class HelpResponse {
    protected Long id;

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

    public static HelpResponse of(HelpEntity item) {
        return HelpResponse.builder()
                .id(item.getId())
                .fullName(item.getFullName())
                .age(item.getAge())
                .contactPhone(item.getContactPhone())
                .otherContact(item.getOtherContact())
                .city(item.getCity())
                .availability(item.getAvailability())
                .helpGroup(item.getHelpGroup())
                .personCondition(item.getPersonCondition())
                .helpNeeded(item.getHelpNeeded())
                .additionalInformation(item.getAdditionalInformation())
                .build();
    }
}
