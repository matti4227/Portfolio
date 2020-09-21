package com.application.app.recipe.recipeIngredient;

import com.application.app.recipe.Recipe;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeIngredientRepository {

    public RecipeIngredient createRecipeIngredient(Recipe recipe, String ingredientName, String amount) {

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setName(ingredientName);
        recipeIngredient.setAmount(amount);

        return recipeIngredient;
    }
}
