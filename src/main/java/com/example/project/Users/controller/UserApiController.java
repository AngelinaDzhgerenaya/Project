package com.example.project.users.controller;

import com.example.project.form.exception.FormNotFoundException;
import com.example.project.form.help.entity.HelpEntity;
import com.example.project.form.help.repository.HelpRepository;
import com.example.project.form.volunteer.entity.VolunteerEntity;
import com.example.project.form.volunteer.repository.VolunteerRepository;
import com.example.project.users.entity.UserEntity;
import com.example.project.users.exception.BadRequestException;
import com.example.project.users.exception.UserAlreadyExistException;
import com.example.project.users.repository.UserRepository;
import com.example.project.users.request.EditUserRequest;
import com.example.project.users.request.RegistrationRequest;
import com.example.project.users.routes.UserRoutes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@Controller
@RequestMapping()
@RequiredArgsConstructor
public class UserApiController {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final VolunteerRepository volunteerRepository;

    @Autowired
    private final HelpRepository helpRepository;



    @GetMapping("/not-secured/registration")
    public String registration(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {

            return "redirect:"+UserRoutes.ACCOUNT;
        }
        return "registration";  // Имя файла index.html, без расширения .html
    }

    @PostMapping(UserRoutes.REGISTRATION)
    public String registrationPost(@ModelAttribute RegistrationRequest request) throws BadRequestException, UserAlreadyExistException {
        request.validate();

        Optional<UserEntity> check = userRepository.findByEmail(request.getEmail());
        if(check.isPresent()) throw new UserAlreadyExistException("Пользователь с таким email уже существует");
        check = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if(check.isPresent()) throw new UserAlreadyExistException("Пользователь с таким номером уже существует");
        check = userRepository.findByPassportId(request.getPassportId());
        if(check.isPresent());

        UserEntity client = UserEntity.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .phoneNumber(request.getPhoneNumber())
                .passportId(request.getPassportId())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(client);
        return "redirect:/not-secured/login";
    }

    @GetMapping("/not-secured/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {

            return "redirect:"+UserRoutes.ACCOUNT;
        }
        return "login";  // Имя файла index.html, без расширения .html
    }

    @GetMapping(UserRoutes.ACCOUNT)
    public String mePage(Authentication authentication, Model model) {
        String username = authentication.getName();
        UserEntity user = userRepository.findByEmail(username).orElseThrow();
        model.addAttribute("user", user);
        return "/account/account";
    }


    @GetMapping(UserRoutes.NOTME)
    public String notmePage( ) {
        return "notme";
    }

    @GetMapping(UserRoutes.FORMS)
    public String forms(Authentication authentication, Model model ) throws FormNotFoundException {
        String username = authentication.getName();
        UserEntity user = userRepository.findByEmail(username).orElseThrow();
        Optional<VolunteerEntity> volunteerForm = volunteerRepository.findByUserId(user.getId());
        Optional<HelpEntity> helpForm = helpRepository.findByUserId(user.getId());

        volunteerForm.ifPresent(volunteer -> model.addAttribute("volunteerForm", volunteer));
        helpForm.ifPresent(help -> model.addAttribute("helpForm", help));

        return "/account/accountForms";
    }

    @GetMapping(UserRoutes.EDIT)
    public String accountEdit(Authentication authentication, Model model) {
        String username = authentication.getName();
        UserEntity user = userRepository.findByEmail(username).orElseThrow();

        model.addAttribute("user", user);
        return "/account/accountEdit";
    }

    @PostMapping(UserRoutes.EDIT)
    public String account(Authentication authentication, @ModelAttribute EditUserRequest request)  {
        String username = authentication.getName();
        UserEntity user = userRepository.findByEmail(username).orElseThrow();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassportId(request.getPassportId());

        userRepository.save(user);
        return "redirect:"+ UserRoutes.ACCOUNT;
    }

    @GetMapping(UserRoutes.EMAIL)
    public String emailEdit() {

        return "/account/emailEdit";
    }
    @PostMapping(UserRoutes.EMAIL)
    public String email(@RequestParam String oldEmail, @RequestParam String newEmail,Authentication authentication, RedirectAttributes redirectAttributes){
        String username = authentication.getName();
        UserEntity user = userRepository.findByEmail(username).orElseThrow();

        if (!user.getEmail().equals(oldEmail)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Старая почта указана неверно");
            return "redirect:"+ UserRoutes.EMAIL;
        }

        if (userRepository.findByEmail(newEmail).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Эта почта уже используется");
            return "redirect:"+ UserRoutes.EMAIL;
        }
        user.setEmail(newEmail);
        userRepository.save(user);
        return "redirect:/not-secured/logout";
    }


    @GetMapping(UserRoutes.PASSWORD)
    public String passwordEdit() {
        return "/account/passwordEdit";
    }
    @PostMapping(UserRoutes.PASSWORD)
    public String password(@RequestParam String oldPassword, @RequestParam String newPassword,Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        UserEntity user = userRepository.findByEmail(username).orElseThrow();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Старый пароль указан неверно");
            return "redirect:"+ UserRoutes.PASSWORD;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "redirect:/not-secured/logout";
    }


    /*
    maria.petrova@gmail.com
    9087654321
    1239874560
    qwerty123
     */


}
