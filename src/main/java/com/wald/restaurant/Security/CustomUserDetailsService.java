package com.wald.restaurant.Security;
import com.wald.restaurant.Model.Manager;
import com.wald.restaurant.Repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private ManagerRepository managerRepository;

    CustomUserDetailsService(@Autowired ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new CustomUserDetails(this.managerRepository.findByLogin(s).orElseThrow());
    }
}
