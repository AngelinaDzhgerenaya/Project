package com.example.project.form.benefits.repository;

import com.example.project.form.benefits.entity.BenefitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BenefitRepository extends JpaRepository<BenefitEntity, Long> {
}
