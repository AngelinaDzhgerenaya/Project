package com.example.project.form.benefits.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "benefit")
public class BenefitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String title;
    private String description;
    private String receiving;

    @CreationTimestamp
    @Column(updatable = false)
    protected LocalDateTime createdAt;
    @UpdateTimestamp
    protected LocalDateTime updatedAt;

}
