package com.example.project.form.volunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "formVolunteer")
public class VolunteerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    //@Column(nullable = false, unique = true)
    //private String applicationNumber;
    protected Long userId;

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

    /*  @PrePersist
    private void generateApplicationNumber() {
        this.applicationNumber = UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }*/
}
/*
fullName — полное имя
age — возраст
contactPhone — телефон
otherContact — другие контакты
city — город
volunteerExperience — опыт волонтёрской деятельности
availability — доступное время (дни недели, время суток)
preferredGroup — предпочитаемая группа людей (например, пожилые, дети)
Available Help -Какая помощь доступна
Additional Information - Дополнительная информация
 */
