package com.wald.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "supply")
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager manager;

    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplier;

    private Boolean published;

    @Column(name = "created_timestamp")
    private LocalDateTime createdDateTime;

    @Column(name = "published_timestamp")
    private LocalDateTime publishedDateTime;

    @ToString.Exclude
    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplyItem> supplyItems;

    @Transient
    public LocalDateTime getLatestDateTime() {
        if (publishedDateTime == null)
            return createdDateTime;
        return publishedDateTime;
    }

    @Transient
    public String getFormattedCreationDateTime() {
        return this.createdDateTime.format(this.getDateTimeFormatter());
    }

    @Transient
    public String getFormattedPublishingDateTime() {
        return this.publishedDateTime.format(this.getDateTimeFormatter());
    }

    @Transient
    public String getFormattedCreationDate() {
        return this.createdDateTime.format(this.getDateFormatter());
    }

    @Transient
    public String getFormatterPublishingDate() {
        return this.publishedDateTime.format(this.getDateFormatter());
    }

    @Transient
    public DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy' в 'HH:mm");
    }

    @Transient
    public DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    @Transient
    public static List<Supply> getSuppliesSortedByDateTime(List<Supply> supplies) {
        return supplies.stream().sorted(Comparator.comparing(Supply::getLatestDateTime).reversed()).collect(Collectors.toList());
    }

    @Transient
    public static List<SupplyItem> getSupplyItemsSortedByDateTime(List<SupplyItem> supplyItems) {
        return supplyItems.stream().sorted(Comparator.comparing(SupplyItem::getSupply, Comparator.comparing(Supply::getLatestDateTime)).reversed()).collect(Collectors.toList());
    }
}
