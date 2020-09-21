package com.application.app.recipe;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.ingredient.Ingredient;
import com.application.app.recipe.recipeIngredient.RecipeIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository(value = "recipeRepository")
public class RecipeRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Recipe createRecipe(RecipeRequest recipeRequest, ApplicationUser user) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeRequest.getName());
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setPreparation(recipeRequest.getPreparation());
        recipe.setDifficulty(recipeRequest.getDifficulty());
        recipe.setPreparationTime(recipeRequest.getPreparationTime());
        recipe.setUsername(user.getUsername());
        recipe.setUser(user);

        return recipe;
    }

    public Recipe updateRecipe(Recipe originalRecipe, RecipeRequest recipe) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        if (recipe.getName() != null) {
            originalRecipe.setName(recipe.getName());
        }
        if (recipe.getDescription() != null) {
            originalRecipe.setDescription(recipe.getDescription());
        }
        if (recipe.getPreparation() != null) {
            originalRecipe.setPreparation(recipe.getPreparation());
        }
        originalRecipe.setDifficulty(recipe.getDifficulty());
        originalRecipe.setPreparationTime(recipe.getPreparationTime());

        entityManager.merge(originalRecipe);
        entityManager.getTransaction().commit();

        return originalRecipe;
    }

    public Recipe updateRecipeRating(Recipe recipe, float rating) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        recipe.setRating(rating);

        entityManager.merge(recipe);
        entityManager.getTransaction().commit();

        return recipe;
    }

    public Recipe addIngredients(Recipe recipe, List<Ingredient> ingredients) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        for (int x = 0; x < ingredients.size(); x++) {
            recipe.getIngredients().add(ingredients.get(x));
            ingredients.get(x).getRecipes().add(recipe);
        }

        entityManager.merge(recipe);
        entityManager.getTransaction().commit();

        return recipe;
    }

    public Recipe removeIngredients(Recipe recipe, List<Ingredient> ingredients) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        for (int x = 0; x < ingredients.size(); x++) {
            ingredients.get(x).getRecipes().remove(recipe);
        }

        recipe.getIngredients().clear();

        entityManager.merge(recipe);
        entityManager.getTransaction().commit();

        return recipe;
    }

    public Recipe addRecipeIngredients(Recipe recipe, List<RecipeIngredient> recipeIngredients) {
        for (int x = 0; x < recipeIngredients.size(); x++) {
            recipe.getRecipeIngredients().add(recipeIngredients.get(x));
        }

        return recipe;
    }

    public Recipe updateRecipeImage(Recipe recipe, byte[] image) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        if (image != null) {
            recipe.setPicture(image);
        }

        entityManager.merge(recipe);
        entityManager.getTransaction().commit();

        return recipe;
    }

//    public ApplicationUser removeUserAvatar(ApplicationUser originalUser) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//
//        if (originalUser.getAvatar() != null) {
//            originalUser.setAvatar(null);
//        }
//
//        entityManager.merge(originalUser);
//        entityManager.getTransaction().commit();
//
//        return originalUser;
//    }
}
