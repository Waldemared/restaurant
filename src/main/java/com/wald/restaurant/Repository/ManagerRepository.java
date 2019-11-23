package com.wald.restaurant.Repository;

import com.wald.restaurant.Model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Optional<Manager> findById(Integer id);
    Optional<Manager> findByLogin(String login);
}
