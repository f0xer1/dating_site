package com.ltp.contacts.web;

import com.ltp.contacts.pojo.User;
import com.ltp.contacts.pojo.UserDescription;
import com.ltp.contacts.repository.UserRepository;
import com.ltp.contacts.service.InvitationService;
import com.ltp.contacts.service.KeywordService;
import com.ltp.contacts.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
public class UserController {

    UserService userService;
    UserRepository userRepository;
    InvitationService invitationService;
    KeywordService keywordService;


    @GetMapping("/")
    public String getRegistration(@ModelAttribute("user") User user) {
        return "register";
    }

    @PostMapping("/registrationSubmit")
    public String createUser(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (user.getPassword().length() < 8 || user.getUsername().length() > 15) {
            bindingResult.addError(new FieldError("user", "password",
                    "Розмір повинен бути від 8 до 15 символів"));
        }
        if (!user.getPassword().matches("^[a-zA-Z0-9]+$")) {
            bindingResult.addError(new FieldError("user", "password",
                    "Поле повинно містити тільки цифри і латинські літери"));
        }
        if (!userService.unicityUser(user)) {
            bindingResult.addError(new FieldError("user", "username",
                    "Такий користувач існує"));
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "Реєстрація пройшла успішно!" +
                " Тепер авторизуйтесь");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin() {
        if (userService.isUserAuthenticated()) {
            return "home";
        }
        return "login";
    }



}