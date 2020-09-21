package com.application.app.ingredient;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IngredientServiceInterface {

    Ingredient addIngredient(String ingredientName);

    List<Ingredient> getAllIngredients();

    List<IngredientResponse> getIngredients();

    List<Ingredient> getIngredientsByIngredientRequests(List<IngredientRequest> ingredientRequests);

    Ingredient getIngredientByName(String ingredientName);

    Ingredient getIngredientById(Long ingredientId);

    void deleteIngredientById(Long ingredientId);

    void deleteIngredient(Long ingredientId);

    IngredientPageResponse getIngredientsPage(int page);
}
