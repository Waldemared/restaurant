package com.wald.restaurant.Service;

import com.wald.restaurant.Model.Manager;
import com.wald.restaurant.Repository.ManagerRepository;
import com.wald.restaurant.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ManagerService {
    private ManagerRepository managerRepository;
    private PasswordEncoder passwordEncoder;

    public ManagerService(@Autowired ManagerRepository managerRepository,
                          @Autowired PasswordEncoder passwordEncoder) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    public Manager getSelf() {
        return managerRepository.findById(((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getManager().getId()).get();
    }

    public Set<String> add(String name, String login, String password) {
        Set<String> info = new HashSet<>();
        if (managerRepository.findByLogin(login).isPresent())
            info.add("exists");
        if (login.equals(""))
            info.add("login");
        if (name.equals(""))
            info.add("name");
        if (password.equals(""))
            info.add("password");
        if (info.size() > 0)
            return info;
        info.add("success");
        Manager manager = new Manager();
        manager.setName(name);
        manager.setLogin(login);
        manager.setPassword(passwordEncoder.encode(password));
        manager.setEnabled(true);
        managerRepository.save(manager);
        return info;
    }

    public Set<String> update(String name) {
        Set<String> info = new HashSet<>();
        if (name.equals(""))
            info.add("empty");
        if (info.size() > 0)
            return info;
        info.add("success");
        Manager manager = managerRepository.findById(this.getSelf().getId()).get();
        manager.setName(name);
        managerRepository.save(manager);
        return info;
    }
}
