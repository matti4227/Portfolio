package com.application.app.recipeCategory;

import com.application.app.recipe.Recipe;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category")
public class RecipeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @JsonManagedReference
    @ManyToMany(mappedBy = "recipeCategories", fetch = FetchType.LAZY)
    private List<Recipe> recipes = new ArrayList<>();
}
