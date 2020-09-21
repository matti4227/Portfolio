package com.application.app.cookbook;

import com.application.app.recipe.Recipe;
import com.application.app.recipe.RecipePageResponse;

public interface CookbookServiceInterface {

    void createCookbook(Long id);

    Cookbook getCookbook(Long cookbookId);

    Recipe getRecipeFromCookbook(Long recipeId);

    Cookbook addRecipe(Long recipeId) throws Exception;

    RecipePageResponse getCookbookByCookbook(int page);

    Cookbook getCookbookByUser();

    Cookbook removeRecipeFromCookbook(Long recipeId);

    boolean checkIfRecipeAlreadyInCookbook(Recipe recipe);
}
