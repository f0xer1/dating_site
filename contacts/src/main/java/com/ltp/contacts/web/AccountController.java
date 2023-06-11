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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {
    UserService userService;
    UserRepository userRepository;
    InvitationService invitationService;
    KeywordService keywordService;

    //Home
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("users", userService.getUsersWithoutAuthenticated());
        return "home";
    }

    //Setting
    @GetMapping("/setting")
    public String accountSetting(Model model) {
        model.addAttribute("description", userService.getAuthenticatedUser().getUserDescription());
        model.addAttribute("keywords", keywordService.getKeywordsByUserId(userService.getAuthenticatedUser().getId()));
        return "account_setting";
    }

    @PostMapping("/change")
    public String updateAccount(@Valid UserDescription userDescription, BindingResult bindingResult, @RequestParam("keywords") String keywords, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "account_setting";
        }
        if (!keywords.isBlank()) {
            keywordService.deleteAllKeywordsByUserId(userService.getAuthenticatedUser());
            keywordService.saveKeywordsForUser(keywords, userService.getAuthenticatedUser());
        }
        userService.updateUserDescription(userDescription);
        redirectAttributes.addFlashAttribute("successMessage", "Параметри успішно змінено");
        return "redirect:/account/setting";
    }


    //    Messages
    @GetMapping("/messages")
    public String getMessageForm(Model model) {
        System.out.println(invitationService.getInvitationByRecipient(userService.getAuthenticatedUser()));
        model.addAttribute("requests", invitationService.getInvitationByRecipient(userService.getAuthenticatedUser()));
        return "messages";
    }

    @GetMapping("/requestPage")
    public String requestPage(Long id, String value) {
        invitationService.updateInvitationById(id, value);
        return "redirect:/account/messages";
    }

    //Опис
    @PostMapping("/get-information")
    public String getInformation(@RequestParam("userId") Long userId, Model model) {
        User sender = userService.getAuthenticatedUser();
        User recipient = userRepository.findById(userId).orElse(null);
        String result = invitationService.getInvitationStatus(sender, recipient);
        if (result != null && result.equals("accept")) {
            System.out.println(recipient);
            model.addAttribute("user", recipient);
        } else if (result != null && result.equals("rejected")) {
            model.addAttribute("message", "Користувач відхилив ваш запит");
        } else if (result != null && result.equals("pending")) {
            model.addAttribute("message", "Зачекайте коли користувач прийме ваше запрошення");
        } else {
            model.addAttribute("message", "Надішліть запит на перегляд користувачу ");
        }
        return "description";
    }
    //Відправка запрошення

    @PostMapping("/send-invitation")
    public String sendInvitation(@RequestParam("userId") Long userId) {
        User sender = userService.getAuthenticatedUser();
        User recipient = userRepository.findById(userId).orElse(null);

        if (sender != null && recipient != null) {
            invitationService.sendInvitation(sender, recipient);
        }

        return "redirect:/account/home";
    }

    //Пошук


    @GetMapping("/search")
    public String getSearch(Model model, @RequestParam("keyword") String keyword, RedirectAttributes redirectAttributes) {
        if (keyword.isBlank()) {
            redirectAttributes.addFlashAttribute("badMessage", "Пустий пошук");
            return "redirect:/account/home";
        }
        model.addAttribute("successMessage", keyword);
        List<User> users = userService.getUsersWithoutAuthenticatedForListUser(keywordService.getUsersByKeyword(keyword));
        model.addAttribute("users", users);
        if (users.size() == 0) {
            redirectAttributes.addFlashAttribute("nullMessage", "Не знайдено");
            return "redirect:/account/home";
        }
        return "home";
    }
}
