package com.example.project.form.help.controller;

import com.example.project.form.exception.BadRequestException;
import com.example.project.form.exception.FormNotFoundException;
import com.example.project.form.help.entity.HelpEntity;
import com.example.project.form.help.repository.HelpRepository;
import com.example.project.form.help.request.CreateHelpRequest;
import com.example.project.form.help.request.EditHelpRequest;
import com.example.project.form.help.response.HelpResponse;
import com.example.project.form.help.routes.HelpRoutes;
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
public class HelpApiController {
    @Autowired
    private final HelpRepository helpRepository;

    @PostMapping(HelpRoutes.CREATE)
    public String create(@ModelAttribute CreateHelpRequest request) throws BadRequestException {
        request.validate();
        HelpEntity help = helpRepository.save(request.entity());
        return "redirect:" + HelpRoutes.SUCCESSFUL;
    }

    @GetMapping(HelpRoutes.SUCCESSFUL)
    public String successfulCreate() {
        return "/form/successfulCreate";
    }
    @GetMapping(HelpRoutes.CREATE)
    public String createForm() {
        return "/form/helpForm";  // Имя файла index.html, без расширения .html
    }

    @GetMapping(HelpRoutes.EDIT)
    public String editForm(@PathVariable Long id) {

        return "/form/helpEditForm";  // Имя файла index.html, без расширения .html
    }

    @PutMapping(HelpRoutes.EDIT)
    public HelpResponse edit(@PathVariable Long id, @RequestBody EditHelpRequest request) throws FormNotFoundException {
        HelpEntity help = helpRepository.findById(id).orElseThrow(FormNotFoundException::new);

        help.setFullName(request.getFullName());
        help.setAge(request.getAge());
        help.setContactPhone(request.getContactPhone());
        help.setOtherContact(request.getOtherContact());
        help.setCity(request.getCity());
        help.setAvailability(request.getAvailability());
        help.setHelpGroup(request.getHelpGroup());
        help.setPersonCondition(request.getPersonCondition());
        help.setHelpNeeded(request.getHelpNeeded());
        help.setAdditionalInformation(request.getAdditionalInformation());


        helpRepository.save(help);
        return HelpResponse.of(help);
    }

    @GetMapping(HelpRoutes.BY_ID)
    public HelpResponse findById(@PathVariable Long id) throws FormNotFoundException {
        HelpEntity help = helpRepository.findById(id).orElseThrow(FormNotFoundException::new);

        return HelpResponse.of(help);
    }

    @DeleteMapping(HelpRoutes.BY_ID)
    public String delete(@PathVariable Long id) {

        helpRepository.deleteById(id);
        return HttpStatus.OK.name();
    }



    @GetMapping(HelpRoutes.SEARCH)
    public List<HelpResponse> search(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "") String city,
            @RequestParam(defaultValue = "") String personCondition,
            @RequestParam(defaultValue = "") String availability,
            @RequestParam(defaultValue = "") String helpGroup,
            @RequestParam(defaultValue = "") String helpNeeded,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);



        if(!query.isEmpty()) {
            System.out.println(query);
            ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                    .withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("personCondition", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("availability", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("helpGroup", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("helpNeeded", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            HelpEntity helpEntity= HelpEntity.builder()
                    .city(query) // если не передан город, не заполняем поле
                    .personCondition(query)
                    .availability(query)
                    .helpGroup(query)
                    .helpNeeded(query)
                    .build();
            Example<HelpEntity> example = Example.of(helpEntity, exampleMatcher);
            return helpRepository
                    .findAll(example, pageable)
                    .stream()
                    .map(HelpResponse::of)
                    .collect(Collectors.toList());
        }
        else {
            ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                    .withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("personCondition", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("availability", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("helpGroup", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("helpNeeded", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            HelpEntity helpEntity = HelpEntity.builder()
                    .city(city.isEmpty() ? null : city) // если не передан город, не заполняем поле
                    .personCondition(personCondition.isEmpty() ? null : personCondition)
                    .availability(availability.isEmpty() ? null : availability)
                    .helpGroup(helpGroup.isEmpty() ? null : helpGroup)
                    .helpNeeded(helpNeeded.isEmpty() ? null : helpNeeded)
                    .build();
            Example<HelpEntity> example = Example.of(helpEntity, exampleMatcher);
            return helpRepository
                    .findAll(example, pageable)
                    .stream()
                    .map(HelpResponse::of)
                    .collect(Collectors.toList());
        }


    }

}
