package com.application.app.recipeCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecipeCategoryWithIdResponse {
    private Long id;
    private String name;
}