package com.application.app.recipe;

import com.application.app.recipe.comment.Comment;
import com.application.app.recipe.comment.CommentResponse;
import com.application.app.recipe.recipeIngredient.RecipeIngredient;
import com.application.app.recipeCategory.RecipeCategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RecipeResponse {
    private Long id;
    private String name;
    private String description;
    private String preparation;
    private int preparationTime;
    private int difficulty;
    private float rating;
    private int userRate;
    private Timestamp createdDate;
    private String username;
    private byte[] avatar;
    private byte[] picture;
    private List<RecipeIngredient> recipeIngredients;
    private List<RecipeCategoryResponse> categories;
    private List<CommentResponse> comments;
    private boolean inCookbook;
}
