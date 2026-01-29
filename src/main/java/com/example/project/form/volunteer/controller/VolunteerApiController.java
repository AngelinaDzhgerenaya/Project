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
        return "/form/volunteerForm";  // Имя файла index.html, без расширения .html
    }

    @PutMapping(VolunteerRoutes.BY_ID)
    public VolunteerResponse edit(@PathVariable Long id, @RequestBody EditVolunteerRequest request) throws FormNotFoundException {
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
        return VolunteerResponse.of(volunteer);
    }

    @GetMapping(VolunteerRoutes.BY_ID)
    public VolunteerResponse findById(@PathVariable Long id) throws FormNotFoundException {
        VolunteerEntity volunteer = volunteerRepository.findById(id).orElseThrow(FormNotFoundException::new);

        return VolunteerResponse.of(volunteer);
    }

    @DeleteMapping(VolunteerRoutes.BY_ID)
    public String delete(@PathVariable Long id) {

        volunteerRepository.deleteById(id);
        return HttpStatus.OK.name();
    }



    @GetMapping(VolunteerRoutes.SEARCH)
    public List<VolunteerResponse> search(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "") String city,
            @RequestParam(defaultValue = "") String volunteerExperience,
            @RequestParam(defaultValue = "") String availability,
            @RequestParam(defaultValue = "") String preferredGroup,
            @RequestParam(defaultValue = "") String availableHelp,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);



        if(!query.isEmpty()) {
            System.out.println(query);
            ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                    .withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("volunteerExperience", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("availability", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("preferredGroup", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("availableHelp", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            VolunteerEntity volunteerEntity= VolunteerEntity.builder()
                    .city(query) // если не передан город, не заполняем поле
                    .volunteerExperience(query)
                    .availability(query)
                    .preferredGroup(query)
                    .availableHelp(query)
                    .build();
            Example<VolunteerEntity> example = Example.of(volunteerEntity, exampleMatcher);
            return volunteerRepository
                    .findAll(example, pageable)
                    .stream()
                    .map(VolunteerResponse::of)
                    .collect(Collectors.toList());
        }
        else {
            ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                    .withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("volunteerExperience", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("availability", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("preferredGroup", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("availableHelp", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            VolunteerEntity volunteerEntity = VolunteerEntity.builder()
                    .city(city.isEmpty() ? null : city) // если не передан город, не заполняем поле
                    .volunteerExperience(volunteerExperience.isEmpty() ? null : volunteerExperience)
                    .availability(availability.isEmpty() ? null : availability)
                    .preferredGroup(preferredGroup.isEmpty() ? null : preferredGroup)
                    .availableHelp(availableHelp.isEmpty() ? null : availableHelp)
                    .build();
            Example<VolunteerEntity> example = Example.of(volunteerEntity, exampleMatcher);
            return volunteerRepository
                    .findAll(example, pageable)
                    .stream()
                    .map(VolunteerResponse::of)
                    .collect(Collectors.toList());
        }


    }



}
