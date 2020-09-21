package com.application.app.recipeCategory;

import com.application.app.recipe.Recipe;
import com.application.app.recipe.RecipeRepositoryInterface;
import com.application.app.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RecipeCategoryService implements RecipeCategoryServiceInterface {

    @Autowired
    private RecipeCategoryRepositoryInterface recipeCategoryRepositoryInterface;

    @Autowired
    private RecipeRepositoryInterface recipeRepositoryInterface;

    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    @Autowired
    private RecipeService recipeService;

    @Override
    public List<RecipeCategory> getAllCategories() {
        return recipeCategoryRepositoryInterface.findAllByOrderByName();
    }

    @Override
    public List<RecipeCategoryWithIdResponse> getCategories() {
        List<RecipeCategory> categories = getAllCategories();
        List<RecipeCategoryWithIdResponse> categoriesResponse = new ArrayList<>();

        for (int x = 0; x < categories.size(); x++) {
            categoriesResponse.add(new RecipeCategoryWithIdResponse(categories.get(x).getId(), categories.get(x).getName()));
        }

        return categoriesResponse;
    }

    @Override
    public List<RecipeCategory> getCategoriesByCategoryRequests(List<RecipeCategoryRequest> categoryRequests) {
        List<RecipeCategory> categories = new ArrayList<>();
        RecipeCategory category;

        for (int x = 0; x < categoryRequests.size(); x++) {
            category = getCategory(categoryRequests.get(x).getName());
            categories.add(category);
        }

        return categories;
    }

    @Override
    public List<RecipeCategory> getCategoriesByRecipe(Recipe recipe) {
        return recipeCategoryRepositoryInterface.findRecipeCategoriesByRecipes(recipe);
    }

    @Override
    public RecipeCategory getCategory(String categoryName) {
        return recipeCategoryRepositoryInterface.findByName(categoryName);
    }

    public List<Recipe> getRecipesByCategory(String categoryName) {
        RecipeCategory category = getCategory(categoryName);
        return recipeService.getRecipesByCategory(category);
    }

    @Override
    public RecipeCategory addCategory(String name) {
        RecipeCategory recipeCategory = new RecipeCategory();
        recipeCategory.setName(name);
        return recipeCategoryRepositoryInterface.save(recipeCategory);
    }

    @Override
    public RecipeCategory addRecipeToCategory(String categoryName, Long recipeId) {
        Recipe recipe = recipeService.getRecipe(recipeId);
        RecipeCategory recipeCategory = getCategory(categoryName);

        recipeCategory = recipeCategoryRepository.addRecipe(recipeCategory, recipe);
        return recipeCategoryRepositoryInterface.save(recipeCategory);
    }

    @Override
    public void removeRecipeFromCategories(Long recipeId) {
        Recipe recipe = recipeService.getRecipe(recipeId);
        List<RecipeCategory> categories = getCategoriesByRecipe(recipe);

        for (int x = 0; x < categories.size(); x++) {
            recipeCategoryRepository.removeRecipe(categories.get(x), recipe);
        }
    }

    @Override
    public Recipe getRecipeFromRecipeCategory(String categoryName, Long recipeId) {
        RecipeCategory recipeCategory = getCategory(categoryName);
        Recipe recipe = null;

        List<Recipe> recipes = recipeCategory.getRecipes();
        for (int x = 0; x < recipes.size(); x++) {
            if (recipeId == recipes.get(x).getId()) {
                recipe = recipes.get(x);
                break;
            }
        }
        return recipe;
    }

    @Override
    public void deleteIngredient(String name) {
        RecipeCategory category = recipeCategoryRepositoryInterface.findByName(name);
        recipeCategoryRepositoryInterface.delete(category);
    }
}
