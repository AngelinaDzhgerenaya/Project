package com.example.project.form.help.controller;

import com.example.project.form.exception.BadRequestException;
import com.example.project.form.exception.FormNotFoundException;
import com.example.project.form.help.entity.HelpEntity;
import com.example.project.form.help.repository.HelpRepository;
import com.example.project.form.help.request.CreateHelpRequest;
import com.example.project.form.help.request.EditHelpRequest;
import com.example.project.form.help.routes.HelpRoutes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        return "/form/helpCreateForm";  // Имя файла index.html, без расширения .html
    }

    @GetMapping(HelpRoutes.EDIT)
    public String edit(@PathVariable Long id, Model model) throws FormNotFoundException {
        HelpEntity help = helpRepository.findById(id).orElseThrow(FormNotFoundException::new);//ищем по id заявку
        model.addAttribute("help", help);
        return "/form/helpEditForm";
    }
    @PostMapping(HelpRoutes.EDIT)
    public String edit(@PathVariable Long id, @ModelAttribute EditHelpRequest request) throws FormNotFoundException {
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
        return "redirect:" + HelpRoutes.SUCCESSFUL;
    }
    @GetMapping(HelpRoutes.BY_ID)
    public String findById(@PathVariable Long id, Model model) throws FormNotFoundException {
        HelpEntity help = helpRepository.findById(id).orElseThrow(FormNotFoundException::new);
        model.addAttribute("help", help);
        return "/form/helpPersonForm";
    }

    @DeleteMapping(HelpRoutes.BY_ID)
    public String delete(@PathVariable Long id) {

        helpRepository.deleteById(id);
        return HttpStatus.OK.name();
    }



    @GetMapping(HelpRoutes.SEARCH)
    public String search(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "") String city,
            @RequestParam(defaultValue = "") String personCondition,
            @RequestParam(defaultValue = "") String availability,
            @RequestParam(defaultValue = "") String helpGroup,
            @RequestParam(defaultValue = "") String helpNeeded,
            //@RequestParam(defaultValue = "0") int page,
            //@RequestParam(defaultValue = "10") int size)
            Model model) {

        //Pageable pageable = PageRequest.of(page, size);
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

        List<HelpEntity> helps =
                helpRepository.findAll(Example.of(helpEntity, exampleMatcher));

        // 2) Если query заполнен — фильтруем дополнительно вручную
        if (!query.isEmpty()) {
            String q = query.toLowerCase();

            helps = helps.stream()
                    .filter(v ->
                            (v.getCity() != null && v.getCity().toLowerCase().contains(q)) ||
                                    (v.getPersonCondition() != null && v.getPersonCondition().toLowerCase().contains(q)) ||
                                    (v.getAvailability() != null && v.getAvailability().toLowerCase().contains(q)) ||
                                    (v.getHelpGroup() != null && v.getHelpGroup().toLowerCase().contains(q)) ||
                                    (v.getHelpNeeded() != null && v.getHelpNeeded().toLowerCase().contains(q))
                    )
                    .toList();
        }

        //  Передаём список в HTML
        model.addAttribute("helps", helps);

        //  Чтобы query и остальные оставались в поле поиска
        model.addAttribute("query", query);
        model.addAttribute("city", city);
        model.addAttribute("personCondition", personCondition);
        model.addAttribute("availability", availability);
        model.addAttribute("helpGroup", helpGroup);
        model.addAttribute("helpNeeded", helpNeeded);

        return "/form/helpForms";


    }
    }

