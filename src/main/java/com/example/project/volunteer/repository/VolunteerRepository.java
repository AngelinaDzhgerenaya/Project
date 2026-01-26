package com.example.project.volunteer.repository;

import com.example.project.users.entity.UserEntity;
import com.example.project.volunteer.entity.VolunteerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<VolunteerEntity, Long> {
}
