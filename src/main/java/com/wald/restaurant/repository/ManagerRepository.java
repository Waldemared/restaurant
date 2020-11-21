package com.wald.restaurant.repository;
import com.wald.restaurant.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Optional<Manager> findById(Integer id);
    Optional<Manager> findByLogin(String login);
}
