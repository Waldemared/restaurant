package com.wald.restaurant.Model;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "manager", schema = "restaurant")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "manager_name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;
}