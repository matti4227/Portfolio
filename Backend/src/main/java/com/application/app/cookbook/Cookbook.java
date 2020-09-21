package com.application.app.cookbook;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.recipe.Recipe;
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
@Table(name = "cookbook")
public class Cookbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ApplicationUser user;

    @JsonManagedReference
    @ManyToMany(mappedBy = "cookbooks", fetch = FetchType.LAZY)
    private List<Recipe> recipes = new ArrayList<>();

}
