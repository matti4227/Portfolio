package com.application.app.recipe;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.cookbook.Cookbook;
import com.application.app.ingredient.Ingredient;
import com.application.app.recipe.comment.Comment;
import com.application.app.recipe.recipeIngredient.RecipeIngredient;
import com.application.app.recipe.vote.Vote;
import com.application.app.recipeCategory.RecipeCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(max = 100)
    private String name;

    @NotBlank
    @NotNull
    @Size(max = 1000)
    private String description;

    @NotBlank
    @NotNull
    @Size(max = 10000)
    private String preparation;

    @NotNull
    @Min(1)
    @Max(3)
    @Column(name = "preparation_time")
    private int preparationTime;

    @NotNull
    @Min(1)
    @Max(3)
    private int difficulty;

    private float rating;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();

    @JsonManagedReference
    @OrderBy(value = "createdDate DESC")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @NotBlank
    @Size(min=3, max = 24)
    @Column(updatable = false)
    private String username;
    
    @Lob
    private byte[] picture;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ApplicationUser user;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cookbook_recipe",
            joinColumns = @JoinColumn(name = "id_recipe"),
            inverseJoinColumns = @JoinColumn(name = "id_cookbook")
    )
    private List<Cookbook> cookbooks = new ArrayList<>();

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "category_recipe",
            joinColumns = @JoinColumn(name = "id_recipe"),
            inverseJoinColumns = @JoinColumn(name = "id_recipe_category")
    )
    private List<RecipeCategory> recipeCategories = new ArrayList<>();

    @JsonManagedReference
    @ManyToMany(mappedBy = "recipes", fetch = FetchType.LAZY)
    private List<Ingredient> ingredients = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();
}
