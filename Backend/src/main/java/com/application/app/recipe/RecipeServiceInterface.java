package com.application.app.recipe;

import com.application.app.cookbook.Cookbook;
import com.application.app.ingredient.Ingredient;
import com.application.app.ingredient.IngredientRequest;
import com.application.app.recipe.comment.RecipeCommentRequest;
import com.application.app.recipe.vote.RecipeVoteRequest;
import com.application.app.recipe.vote.Vote;
import com.application.app.recipeCategory.RecipeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecipeServiceInterface {

    Recipe createRecipe(RecipeRequest recipe);

    Recipe getRecipe(Long id);

    RecipeResponse getSingleRecipe(Long id);

    RecipePageResponse getRecipesByParameters(String search, List<IngredientRequest> ingredientRequestList, String categoryName, int difficulty, int preparationTime, int sort, int page);

    RecipePageResponse getRecipesByNameSearch(String name, int page);

    RecipePageResponse getRecipesByIngredients(List<IngredientRequest> ingredientRequestList, int page);

    List<Recipe> getRecipesByCategory(RecipeCategory category);

    Recipe updateRecipe(Long id, RecipeRequest recipeRequest);

    void deleteRecipe(Long id);

    void addRecipeToCookbook(Long recipeId) throws Exception;

    void addRecipeToCategories(Recipe recipe, List<RecipeCategory> categories);

    void removeRecipeFromCategories(Recipe recipe);

    void addIngredients(Recipe recipe, List<Ingredient> ingredients, List<IngredientRequest> ingredientRequests);

    void removeIngredients(Recipe recipe);

    RecipeCategory getCategoryFromParameter(String categoryName);

    Sort getSortFromParameter(int sort);

    void addScoreToRecipe(Long recipeId, RecipeVoteRequest recipeVoteRequest) throws Exception;

    void updateRating(Recipe recipe, List<Vote> votes);

    Boolean userAlreadyVoted(Long userId, List<Vote> votes);

    void addCommentToRecipe(Long recipeId, RecipeCommentRequest recipeCommentRequest);

    RecipePageResponse getUserRecipes(String username, int page, int size);

    Page<Recipe> getRecipesByCookbook(Cookbook cookbook, int page);

    void updateRecipeImage(Long id, byte[] bytes);

    void removeRecipeImage();

    List<RecipeListResponse> getRecipeResponse(Page<Recipe> recipePage);

//    Ingredient getIngredientFromRecipe(Long recipeId, String ingredientName);
}
