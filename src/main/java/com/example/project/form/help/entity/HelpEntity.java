package com.example.project.form.help.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "formHelp")
public class HelpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Long userId;

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

    protected String status= "Активно";

    @CreationTimestamp
    @Column(updatable = false)
    protected LocalDateTime createdAt;
    @UpdateTimestamp
    protected LocalDateTime updatedAt;
}

/*
fullName — полное имя
age — возраст
contactPhone — телефон
otherContact — другие контакты
city — город
availability — доступное время (дни недели, время суток)
helpGroup - кому нужна помощь
personCondition — состояние здоровья / жизненная ситуация
helpNeeded — Какая помощь требуется
Additional Information - Дополнительная информация
 */
