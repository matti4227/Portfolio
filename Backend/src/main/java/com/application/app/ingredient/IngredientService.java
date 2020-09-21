package com.application.app.ingredient;

import com.application.app.recipe.recipeIngredient.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService implements IngredientServiceInterface {

    @Autowired
    private IngredientRepositoryInterface ingredientRepositoryInterface;

    @Autowired
    private RecipeIngredientService recipeIngredientService;

    @Override
    public Ingredient addIngredient(String ingredientName) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        return ingredientRepositoryInterface.save(ingredient);
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepositoryInterface.findAllByOrderByName();
    }

    @Override
    public List<IngredientResponse> getIngredients() {
        List<Ingredient> ingredients = getAllIngredients();
        List<IngredientResponse> ingredientsResponse = new ArrayList<>();

        for (int x = 0; x < ingredients.size(); x++) {
            ingredientsResponse.add(new IngredientResponse(ingredients.get(x).getName()));
        }

        return ingredientsResponse;
    }

    @Override
    public List<Ingredient> getIngredientsByIngredientRequests(List<IngredientRequest> ingredientRequests) {
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient;

        for (int x = 0; x < ingredientRequests.size(); x++) {
            ingredient = getIngredientByName(ingredientRequests.get(x).getName());
            ingredients.add(ingredient);
        }

        return ingredients;
    }

    @Override
    public Ingredient getIngredientByName(String ingredientName) {
        return ingredientRepositoryInterface.findByName(ingredientName);
    }

    @Override
    public Ingredient getIngredientById(Long ingredientId) {
        return ingredientRepositoryInterface.findById(ingredientId).orElse(null);
    }

    @Override
    public void deleteIngredientById(Long ingredientId) {
        ingredientRepositoryInterface.deleteById(ingredientId);
    }

    @Override
    public void deleteIngredient(Long ingredientId) {
        Ingredient ingredient = getIngredientById(ingredientId);
        recipeIngredientService.deleteRecipeIngredientsByRecipeIngredients(ingredient.getName());
        deleteIngredientById(ingredientId);
    }

    @Override
    public IngredientPageResponse getIngredientsPage(int page) {
        Pageable pageRequest = PageRequest.of(page, 25, Sort.by("name").ascending());
        Page<Ingredient> ingredientPage = ingredientRepositoryInterface.findAll(pageRequest);

        return new IngredientPageResponse(ingredientPage.getContent(), ingredientPage.getNumber(), ingredientPage.getTotalPages(), (int) ingredientPage.getTotalElements());
    }
}
