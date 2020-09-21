package com.application.app.fridge;

import com.application.app.ingredient.IngredientRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FridgeIngredientRequest {
    private List<IngredientRequest> ingredientRequests;
}
