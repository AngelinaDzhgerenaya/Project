package com.example.project.volunteer.controller;

import com.example.project.users.exception.UserAlreadyExistException;
import com.example.project.users.repository.UserRepository;
import com.example.project.volunteer.entity.VolunteerEntity;
import com.example.project.volunteer.exception.FormNotFoundException;
import com.example.project.volunteer.repository.VolunteerRepository;
import com.example.project.volunteer.request.CreateVolunteerRequest;
import com.example.project.volunteer.request.EditVolunteerRequest;
import com.example.project.volunteer.response.VolunteerResponse;
import com.example.project.volunteer.routes.VolunteerRoutes;
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
    public VolunteerResponse create(@RequestBody CreateVolunteerRequest request) {
        VolunteerEntity volunteer;
        volunteer = volunteerRepository.save(request.entity());
        return VolunteerResponse.of(volunteer);
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
            @RequestParam(defaultValue = "") String city,
            @RequestParam(defaultValue = "") String volunteerExperience,
            @RequestParam(defaultValue = "") String availability,
            @RequestParam(defaultValue = "") String preferredGroup,
            @RequestParam(defaultValue = "") String availableHelp,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("volunteerExperience", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("availability", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("preferredGroup", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("availableHelp", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<VolunteerEntity> example = Example.of(
                VolunteerEntity.builder()
                        .city(city)
                        .volunteerExperience(volunteerExperience)
                        .availability(availability)
                        .preferredGroup(preferredGroup)
                        .availableHelp(availableHelp)
                        .build(), exampleMatcher);

        return volunteerRepository
                .findAll(example, pageable)
                .stream()
                .map(VolunteerResponse::of)
                .collect(Collectors.toList());
    }

    @GetMapping(VolunteerRoutes.CREATE)
    public String registration() {
        return "form";  // Имя файла index.html, без расширения .html
    }

}
