package com.application.app.ingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class IngredientPageResponse {
    private List<Ingredient> content;
    private int currentPage;
    private int totalPages;
    private int totalResults;
}
