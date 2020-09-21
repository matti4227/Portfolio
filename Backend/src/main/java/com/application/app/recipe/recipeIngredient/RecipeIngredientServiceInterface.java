package com.application.app.recipe.recipeIngredient;

import com.application.app.ingredient.IngredientRequest;
import com.application.app.recipe.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecipeIngredientServiceInterface {

    List<RecipeIngredient> createRecipeIngredients(Recipe recipe, List<IngredientRequest> ingredientRequest);

    void deleteRecipeIngredients(Recipe recipe);

    void deleteRecipeIngredientsByRecipeIngredients(String name);

    List<RecipeIngredient> getRecipeIngredientsByName(String name);
}
