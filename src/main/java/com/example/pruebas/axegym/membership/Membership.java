package com.example.pruebas.axegym.membership;

import com.example.pruebas.axegym.client.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = true)
    @JsonBackReference
    private Client client;


    @ManyToOne
    @JoinColumn(name = "membership_plan_id")
    private MembershipPlan membershipPlan;

    private LocalDate starDate;

    private LocalDate expirationDate;

    private Boolean active;


    public LocalDate calculateExpirationDate(){
        if (membershipPlan != null){
            switch (membershipPlan.getDuration()){
                case DAILY:
                    return starDate.plusDays(1);
                case WEEKLY:
                    return starDate.plusWeeks(1);
                case BIWEEKLY:
                    return starDate.plusWeeks(2);
                case MONTHLY:
                    return starDate.plusMonths(1);
                default:
                    throw  new IllegalArgumentException("not valid duration");
            }
        }
        return null;
    }
    public Boolean isActive(){
        return this.active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MembershipPlan getMembershipPlan() {
        return membershipPlan;
    }

    public void setMembershipPlan(MembershipPlan membershipPlan) {
        this.membershipPlan = membershipPlan;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getStarDate() {
        return starDate;
    }

    public void setStarDate(LocalDate starDate) {
        this.starDate = starDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
