package com.example.project.form.help.request;

import lombok.Data;

@Data
public class EditHelpRequest {
    private Long id;

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

    protected Boolean active;
}
