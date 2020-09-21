package com.application.app.fridge;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.ingredient.Ingredient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "fridge")
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ApplicationUser user;

    @JsonManagedReference
    @ManyToMany(mappedBy = "fridges", fetch = FetchType.LAZY)
    private List<Ingredient> ingredients = new ArrayList<>();
}
