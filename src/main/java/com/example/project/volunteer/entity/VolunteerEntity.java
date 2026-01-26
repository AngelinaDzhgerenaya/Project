package com.example.project.volunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
/*1. Общие данные (General Information):
fullName — полное имя
age — возраст
contactPhone — телефон
otherContact — другие контакты
city — город

2. Обо мне (About Me):
volunteerExperience — опыт волонтёрской деятельности
availability — доступное время (дни недели, время суток)
preferredGroup — предпочитаемая группа людей (например, пожилые, дети)
skills — специальные навыки (медицинская помощь, помощь с обучением и т.д.)

3. Какая помощь доступна (Available Help):
physicalHelp — физическая помощь (уборка, переноска и т.д.)
psychologicalSupport — психологическая поддержка
educationHelp — помощь в обучении
financialSupport — финансовая помощь
otherHelp — другая помощь

4. Дополнительная информация (Additional Information):
whyVolunteer — почему вы решили стать волонтёром
preferences — предпочтения по типу людей или задач
trainingNeeded — требуется ли дополнительное обучение*/
