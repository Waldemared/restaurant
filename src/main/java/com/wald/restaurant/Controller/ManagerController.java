package com.wald.restaurant.Controller;

import com.wald.restaurant.Model.Manager;
import com.wald.restaurant.Repository.ManagerRepository;
import com.wald.restaurant.Security.CustomUserDetails;
import com.wald.restaurant.Service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ManagerController {
    ManagerService managerService;
    ManagerRepository managerRepository;

    ManagerController(@Autowired ManagerService managerService,
                      @Autowired ManagerRepository managerRepository) {
        this.managerService = managerService;
        this.managerRepository = managerRepository;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        return "index";
    }

    @GetMapping("/profile")
    public String getSelfPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Manager manager = null;
        if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))
            manager = ((CustomUserDetails)auth.getPrincipal()).getManager();
        model.addAttribute("let", 100);
        model.addAttribute("manager", manager);
        return "profile";
    }

    @PostMapping("/register")
    public RedirectView register(RedirectAttributes attributes,
                                 @RequestParam String name,
                                 @RequestParam String login,
                                 @RequestParam String password) {
        attributes.addFlashAttribute("info", managerService.add(name, login, password));
        RedirectView redirectView = new RedirectView("/register");
        return redirectView;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        Object attr = model.getAttribute("info");
        System.out.println(Optional.ofNullable(attr).toString());
        return "register";
    }

}
