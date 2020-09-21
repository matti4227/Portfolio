package com.application.app.recipe;

import com.application.app.ingredient.IngredientRequest;
import com.application.app.recipeCategory.RecipeCategoryRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeRequest {

    private String name;
    private String description;
    private String preparation;
    private int preparationTime;
    private int difficulty;
    private List<IngredientRequest> recipeIngredients;
    private List<RecipeCategoryRequest> categories;

}
