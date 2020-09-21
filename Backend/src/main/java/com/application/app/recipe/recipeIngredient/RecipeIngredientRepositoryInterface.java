package com.application.app.recipe.recipeIngredient;

import com.application.app.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepositoryInterface extends JpaRepository<RecipeIngredient, Long> {
    List<RecipeIngredient> findAllByRecipe(Recipe recipe);
    void deleteAllByName(String name);
    void deleteByName(String name);
    List<RecipeIngredient> getAllByName(String name);
}
