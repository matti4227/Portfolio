package com.application.app.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RecipePageResponse {
    private List<RecipeListResponse> content;
    private int currentPage;
    private int totalPages;
    private int totalResults;
}
