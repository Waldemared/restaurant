package com.wald.restaurant.Service;

import com.wald.restaurant.Model.Manager;
import com.wald.restaurant.Repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ManagerService {
    ManagerRepository managerRepository;
    PasswordEncoder passwordEncoder;

    public ManagerService(@Autowired ManagerRepository managerRepository,
                          @Autowired PasswordEncoder passwordEncoder) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Manager getById(Integer id) {
        return managerRepository.findById(id).orElse(null);
    }

    public List<Manager> getAll() {
        return managerRepository.findAll();
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
}
