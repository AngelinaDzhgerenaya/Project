package com.example.project.form.benefits.repository;

import com.example.project.form.benefits.entity.BenefitEntity;
import com.example.project.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BenefitRepository extends JpaRepository<BenefitEntity, Long> {
    BenefitEntity findTopByOrderByIdDesc();
    //Optional<BenefitEntity> findTopByOrderByIdDesc();
}
