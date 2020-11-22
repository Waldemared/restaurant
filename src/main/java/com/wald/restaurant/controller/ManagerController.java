package com.wald.restaurant.controller;

import com.wald.restaurant.model.Manager;
import com.wald.restaurant.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class ManagerController {
    private final ManagerService managerService;

    @Autowired
    ManagerController(final ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/")
    public RedirectView mainPage(final Model model) {
        return new RedirectView("/ingredients");
    }

    @GetMapping("/profile")
    public String getSelfPage(final Model model) {
        Manager manager = managerService.getSelf();
        model.addAttribute("manager", manager);
        return "profile";
    }

    @PostMapping("/register")
    public RedirectView register(final RedirectAttributes attributes,
                                 @RequestParam final String name,
                                 @RequestParam final String login,
                                 @RequestParam final String password) {
        attributes.addFlashAttribute("info", managerService.add(name, login, password));
        return new RedirectView("/register");
    }

    @PostMapping("/profile/update")
    public RedirectView profileUpdate(final RedirectAttributes attributes,
                                      @RequestParam final String name) {
        attributes.addFlashAttribute("updateInfo", managerService.update(name));
        return new RedirectView("/profile");
    }

    @GetMapping("/register")
    public String registerPage(final Model model) {
        Object attr = model.getAttribute("info");
        System.out.println(Optional.ofNullable(attr).toString());
        return "register";
    }
}
