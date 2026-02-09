package com.example.project.form.volunteer.repository;

import com.example.project.form.volunteer.entity.VolunteerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<VolunteerEntity, Long> {
    Optional<VolunteerEntity> findByUserId(Long userId);
}
