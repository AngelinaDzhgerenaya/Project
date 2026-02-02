package com.example.project.form.volunteer.controller;


import com.example.project.form.exception.BadRequestException;
import com.example.project.form.volunteer.entity.VolunteerEntity;
import com.example.project.form.exception.FormNotFoundException;
import com.example.project.form.volunteer.repository.VolunteerRepository;
import com.example.project.form.volunteer.request.CreateVolunteerRequest;
import com.example.project.form.volunteer.request.EditVolunteerRequest;
import com.example.project.form.volunteer.response.VolunteerResponse;
import com.example.project.form.volunteer.routes.VolunteerRoutes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@Controller
@RequestMapping()
@RequiredArgsConstructor
public class VolunteerApiController {

    @Autowired
    private final VolunteerRepository volunteerRepository;

    @PostMapping(VolunteerRoutes.CREATE)
    public String create(@ModelAttribute CreateVolunteerRequest request) throws BadRequestException {
        request.validate();
        volunteerRepository.save(request.entity());
        return "redirect:" + VolunteerRoutes.SUCCESSFUL;
    }

    @GetMapping(VolunteerRoutes.SUCCESSFUL)
    public String successfulCreate() {
        return "/form/successfulCreate";
    }

    @GetMapping(VolunteerRoutes.CREATE)
    public String createForm() {
        return "/form/volunteerCreateForm";  // Имя файла index.html, без расширения .html
    }

    @GetMapping(VolunteerRoutes.EDIT)
    public String editForm(@PathVariable Long id, Model model) throws FormNotFoundException {
        VolunteerEntity volunteer = volunteerRepository.findById(id).orElseThrow(FormNotFoundException::new);//ищем по id заявку
        model.addAttribute("volunteer", volunteer);
        return "/form/volunteerEditForm";  // Имя файла index.html, без расширения .html
    }

    @PostMapping(VolunteerRoutes.EDIT)
    public String edit(@PathVariable Long id, @ModelAttribute EditVolunteerRequest request) throws FormNotFoundException {
        VolunteerEntity volunteer = volunteerRepository.findById(id).orElseThrow(FormNotFoundException::new);

        volunteer.setFullName(request.getFullName());
        volunteer.setAge(request.getAge());
        volunteer.setContactPhone(request.getContactPhone());
        volunteer.setOtherContact(request.getOtherContact());
        volunteer.setCity(request.getCity());
        volunteer.setVolunteerExperience(request.getVolunteerExperience());
        volunteer.setAvailability(request.getAvailability());
        volunteer.setPreferredGroup(request.getPreferredGroup());
        volunteer.setAvailableHelp(request.getAvailableHelp());
        volunteer.setAdditionalInformation(request.getAdditionalInformation());


        volunteerRepository.save(volunteer);
        return "redirect:" + VolunteerRoutes.SUCCESSFUL;
    }

    @GetMapping(VolunteerRoutes.BY_ID)
    public String findById(@PathVariable Long id, Model model) throws FormNotFoundException {
        VolunteerEntity volunteer = volunteerRepository.findById(id).orElseThrow(FormNotFoundException::new);
        model.addAttribute("volunteer", volunteer);
        return "/form/volunteerPersonForm";
    }

    @DeleteMapping(VolunteerRoutes.BY_ID)
    public String delete(@PathVariable Long id) {

        volunteerRepository.deleteById(id);
        return HttpStatus.OK.name();
    }


    @GetMapping(VolunteerRoutes.SEARCH)
    public String search(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "") String city,
            @RequestParam(defaultValue = "") String volunteerExperience,
            @RequestParam(defaultValue = "") String availability,
            @RequestParam(defaultValue = "") String preferredGroup,
            @RequestParam(defaultValue = "") String availableHelp,
            //@RequestParam(defaultValue = "0") int page,
            //@RequestParam(defaultValue = "10") int size,
            Model model) {

        //Pageable pageable = PageRequest.of(page, size);

        ExampleMatcher filterMatcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("volunteerExperience", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("availability", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("preferredGroup", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("availableHelp", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        VolunteerEntity filterEntity = VolunteerEntity.builder()
                .city(city.isEmpty() ? null : city)
                .volunteerExperience(volunteerExperience.isEmpty() ? null : volunteerExperience)
                .availability(availability.isEmpty() ? null : availability)
                .preferredGroup(preferredGroup.isEmpty() ? null : preferredGroup)
                .availableHelp(availableHelp.isEmpty() ? null : availableHelp)
                .build();

        List<VolunteerEntity> volunteers =
                volunteerRepository.findAll(Example.of(filterEntity, filterMatcher));

        // 2) Если query заполнен — фильтруем дополнительно вручную
        if (!query.isEmpty()) {
            String q = query.toLowerCase();

            volunteers = volunteers.stream()
                    .filter(v ->
                            (v.getCity() != null && v.getCity().toLowerCase().contains(q)) ||
                                    (v.getVolunteerExperience() != null && v.getVolunteerExperience().toLowerCase().contains(q)) ||
                                    (v.getAvailability() != null && v.getAvailability().toLowerCase().contains(q)) ||
                                    (v.getPreferredGroup() != null && v.getPreferredGroup().toLowerCase().contains(q)) ||
                                    (v.getAvailableHelp() != null && v.getAvailableHelp().toLowerCase().contains(q))
                    )
                    .toList();
        }


        //  Передаём список в HTML
        model.addAttribute("volunteers", volunteers);

        //  Чтобы query и остальные оставались в поле поиска
        model.addAttribute("query", query);
        model.addAttribute("city", city);
        model.addAttribute("volunteerExperience", volunteerExperience);
        model.addAttribute("availability", availability);
        model.addAttribute("preferredGroup", preferredGroup);
        model.addAttribute("availableHelp", availableHelp);

        return "/form/volunteerForms";

    }
}

