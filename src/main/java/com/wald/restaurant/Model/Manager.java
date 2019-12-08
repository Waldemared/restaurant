package com.wald.restaurant.Model;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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

    @ToString.Exclude
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Supply> supplies;

    @Column(name = "enabled")
    private Boolean enabled;
}