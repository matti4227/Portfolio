package com.application.app.recipe;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.cookbook.Cookbook;
import com.application.app.ingredient.Ingredient;
import com.application.app.recipeCategory.RecipeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepositoryInterface extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
    Optional<Recipe> findById(Long id);

    Page<Recipe> findAll(Pageable pageable);

    List<Recipe> findRecipeByRecipeCategories(RecipeCategory category);

    Page<Recipe> findAllByUser(ApplicationUser user, Pageable pageable);

    Page<Recipe> findAllByCookbooks(Cookbook cookbook, Pageable pageable);
}
