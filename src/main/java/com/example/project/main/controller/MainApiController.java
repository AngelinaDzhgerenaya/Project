package com.example.project.main.controller;

import com.example.project.form.benefits.repository.BenefitRepository;
import com.example.project.form.help.repository.HelpRepository;
import com.example.project.form.volunteer.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.example.project.form.benefits.entity.BenefitEntity;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class MainApiController {

    @Autowired
    private final BenefitRepository benefitRepository;

    @Autowired
    private final VolunteerRepository volunteerRepository;

    @Autowired
    private final HelpRepository helpRepository;

    @GetMapping()
    public String index(Model model) {

        model.addAttribute("volunteerCount", volunteerRepository.count());
        model.addAttribute("helpCount", helpRepository.count());
        model.addAttribute("benefitCount", benefitRepository.count());

        BenefitEntity lastBenefit = benefitRepository.findTopByOrderByIdDesc();

        model.addAttribute("lastBenefit", lastBenefit);

        return "index";  // Имя файла index.html, без расширения .html
    }


}
