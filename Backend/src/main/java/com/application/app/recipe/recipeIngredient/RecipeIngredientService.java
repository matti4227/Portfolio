package com.application.app.recipe.recipeIngredient;

import com.application.app.ingredient.IngredientRequest;
import com.application.app.recipe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeIngredientService implements RecipeIngredientServiceInterface {

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private RecipeIngredientRepositoryInterface recipeIngredientRepositoryInterface;

    @Override
    public List<RecipeIngredient> createRecipeIngredients(Recipe recipe, List<IngredientRequest> ingredientRequests) {

        RecipeIngredient recipeIngredient;
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();

        for (int x = 0; x < ingredientRequests.size(); x++) {
            recipeIngredient = recipeIngredientRepository.createRecipeIngredient(
                    recipe, ingredientRequests.get(x).getName(), ingredientRequests.get(x).getAmount());
            recipeIngredientRepositoryInterface.save(recipeIngredient);
            recipeIngredients.add(recipeIngredient);
        }

        return recipeIngredients;
    }

    @Override
    public void deleteRecipeIngredients(Recipe recipe) {
        List<RecipeIngredient> recipeIngredients = recipeIngredientRepositoryInterface.findAllByRecipe(recipe);
        for (int x = 0; x < recipeIngredients.size(); x++) {
            recipeIngredientRepositoryInterface.deleteById(recipeIngredients.get(x).getId());
        }
    }

    @Override
    public void deleteRecipeIngredientsByRecipeIngredients(String name) {
        List<RecipeIngredient> ingredients = getRecipeIngredientsByName(name);
        recipeIngredientRepositoryInterface.deleteAll(ingredients);
    }

    @Override
    public List<RecipeIngredient> getRecipeIngredientsByName(String name) {
       return recipeIngredientRepositoryInterface.getAllByName(name);
    }
}
