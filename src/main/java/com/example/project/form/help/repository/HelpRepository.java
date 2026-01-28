package com.example.project.form.help.repository;

import com.example.project.form.help.entity.HelpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpRepository extends JpaRepository<HelpEntity, Long> {
}
