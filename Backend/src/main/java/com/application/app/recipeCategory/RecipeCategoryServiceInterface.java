package com.application.app.recipeCategory;

import com.application.app.recipe.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecipeCategoryServiceInterface {

    List<RecipeCategory> getAllCategories();

    List<RecipeCategory> getCategoriesByCategoryRequests(List<RecipeCategoryRequest> categoryRequests);

    List<RecipeCategoryWithIdResponse> getCategories();

    List<RecipeCategory> getCategoriesByRecipe(Recipe recipe);

    List<Recipe> getRecipesByCategory(String categoryName);

    RecipeCategory getCategory(String categoryName);

    RecipeCategory addCategory(String name);

    RecipeCategory addRecipeToCategory(String categoryName, Long recipeId);

    void removeRecipeFromCategories(Long recipeId);//List<Long>

    Recipe getRecipeFromRecipeCategory(String categoryName, Long recipeId);

    void deleteIngredient(String name);
}
