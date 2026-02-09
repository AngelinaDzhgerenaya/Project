package com.example.project.form.help.repository;

import com.example.project.form.help.entity.HelpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HelpRepository extends JpaRepository<HelpEntity, Long> {
    Optional<HelpEntity> findByUserId(Long userId);
}
