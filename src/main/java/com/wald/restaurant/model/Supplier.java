package com.wald.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "supplier")
@Data
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "supplier_name")
    private String name;

    @Column(name = "time_weekdays_begin")
    private LocalTime weekDaysBeginTime;

    @Column(name = "time_weekdays_end")
    private LocalTime weekDaysEndTime;

    @Column(name = "time_weekends_begin")
    private LocalTime weekEndsBeginTime;

    @Column(name = "time_weekends_end")
    private LocalTime weekEndsEndTime;

    @ManyToMany(mappedBy = "suppliers", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Ingredient> ingredients;

    @ToString.Exclude
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<Supply> supplies;
}
