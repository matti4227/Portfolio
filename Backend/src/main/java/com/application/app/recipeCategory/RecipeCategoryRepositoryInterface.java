package com.application.app.recipeCategory;

import com.application.app.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeCategoryRepositoryInterface extends JpaRepository<RecipeCategory, Long> {

    RecipeCategory findByName(String name);

    List<RecipeCategory> findRecipeCategoriesByRecipes(Recipe recipe);

    List<RecipeCategory> findAllByOrderByName();
}
