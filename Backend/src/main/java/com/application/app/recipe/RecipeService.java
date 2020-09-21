package com.application.app.recipe;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.applicationUser.ApplicationUserService;
import com.application.app.cookbook.Cookbook;
import com.application.app.cookbook.CookbookService;
import com.application.app.image.ImageCompDecomp;
import com.application.app.ingredient.Ingredient;
import com.application.app.ingredient.IngredientRequest;
import com.application.app.ingredient.IngredientService;
import com.application.app.recipe.comment.Comment;
import com.application.app.recipe.comment.CommentResponse;
import com.application.app.recipe.comment.CommentService;
import com.application.app.recipe.comment.RecipeCommentRequest;
import com.application.app.recipe.recipeIngredient.RecipeIngredient;
import com.application.app.recipe.recipeIngredient.RecipeIngredientService;
import com.application.app.recipe.specifications.*;
import com.application.app.recipe.vote.RecipeVoteRequest;
import com.application.app.recipe.vote.Vote;
import com.application.app.recipe.vote.VoteService;
import com.application.app.recipeCategory.RecipeCategory;
import com.application.app.recipeCategory.RecipeCategoryResponse;
import com.application.app.recipeCategory.RecipeCategoryService;
import com.application.app.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service(value = "recipeService")
public class RecipeService implements RecipeServiceInterface {

    @Autowired
    private RecipeRepositoryInterface recipeRepositoryInterface;

    @Autowired
    private ApplicationUserService userService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private RecipeIngredientService recipeIngredientService;

    @Autowired
    private RecipeCategoryService categoryService;

    @Autowired
    private CookbookService cookbookService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ImageCompDecomp imageCompDecomp;

    @Override
    public Recipe createRecipe(RecipeRequest recipeRequest) {
        ApplicationUser user = userService.getUser();
        Recipe recipe = recipeRepository.createRecipe(recipeRequest, user);

        recipe = recipeRepositoryInterface.save(recipe);

        if (recipeRequest.getRecipeIngredients().size() > 0) {
            List<Ingredient> ingredients = ingredientService.getIngredientsByIngredientRequests(recipeRequest.getRecipeIngredients());
            addIngredients(recipe, ingredients, recipeRequest.getRecipeIngredients());
        }

        recipe = recipeRepositoryInterface.save(recipe);

        if (recipeRequest.getCategories().size() > 0) {
            List<RecipeCategory> categories = categoryService.getCategoriesByCategoryRequests(recipeRequest.getCategories());
            addRecipeToCategories(recipe, categories);
        }
        recipe = recipeRepositoryInterface.save(recipe);
        return recipe;
    }

    @Override
    public Recipe getRecipe(Long id) {
        return recipeRepositoryInterface.findById(id).orElse(null);
    }

    @Override
    public RecipeResponse getSingleRecipe(Long id) {
        Recipe recipe = getRecipe(id);
        List<RecipeCategory> categories = categoryService.getCategoriesByRecipe(recipe);
        List<RecipeCategoryResponse> categoriesNames = new ArrayList<>();
        for (int x = 0; x < categories.size(); x++) {
            categoriesNames.add(new RecipeCategoryResponse(categories.get(x).getName()));
        }

        byte[] decompressedImage = getDecompressedImage(recipe.getPicture());

        ApplicationUser user = userService.getUserByName(recipe.getUsername());
        byte[] decompressedAvatar = userService.getDecompressedAvatar(user.getAvatar());

        List<CommentResponse> comments = getCommentsResponse(recipe.getComments());

        ApplicationUser userVote = userService.getUser();
        Vote vote = voteService.getVote(recipe, userVote);
        int score;
        if (vote != null) {
            score = vote.getScore();
        } else {
            score = 0;
        }

        boolean inCookbook;
        if (securityService.getUsernameFromUserDetails() == null) {
            inCookbook = false;
        } else {
            inCookbook = cookbookService.checkIfRecipeAlreadyInCookbook(recipe);
        }

        
        return new RecipeResponse(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getPreparation(),
                recipe.getPreparationTime(),
                recipe.getDifficulty(),
                recipe.getRating(),
                score,
                recipe.getCreatedDate(),
                recipe.getUsername(),
                decompressedAvatar,
                decompressedImage,
                recipe.getRecipeIngredients(),
                categoriesNames,
                comments,
                inCookbook
        );
    }

    @Override
    public RecipePageResponse getRecipesByParameters(String search, List<IngredientRequest> ingredientRequestList, String categoryName, int difficulty, int preparationTime, int sort, int page) {
        RecipeCategory recipeCategory = getCategoryFromParameter(categoryName);
        Specification<Recipe> spec = Specification
                .where(new RecipeWithDifficulty(difficulty))
                .and(new RecipeWithPreparationTime(preparationTime)
                .and(new RecipeWithRecipeCategory(recipeCategory))
                .and(new RecipeWithNameSearch(search)));

        if (ingredientRequestList.size() > 0) {
            List<Ingredient> ingredients = ingredientService.getIngredientsByIngredientRequests(ingredientRequestList);

            for (int x = 0; x < ingredients.size(); x++) {
                spec = spec.and(new RecipeWithIngredient(ingredients.get(x)));
            }
        }

        Sort s = getSortFromParameter(sort);
        Pageable pageRequest = PageRequest.of(page, 6, s);

        Page<Recipe> recipePage = recipeRepositoryInterface.findAll(spec, pageRequest);
        List<RecipeListResponse> recipes = getRecipeResponse(recipePage);

        return new RecipePageResponse(recipes, recipePage.getNumber(), recipePage.getTotalPages(), (int) recipePage.getTotalElements());
    }

    @Override
    public List<Recipe> getRecipesByCategory(RecipeCategory category) {
        return recipeRepositoryInterface.findRecipeByRecipeCategories(category);
    }

    @Override
    public RecipePageResponse getRecipesByNameSearch(String name, int page) {
        Specification<Recipe> spec = Specification.where(new RecipeWithNameSearch(name));

        Page<Recipe> recipePage = recipeRepositoryInterface.findAll(spec, PageRequest.of(page, 6));
        List<RecipeListResponse> recipes = getRecipeResponse(recipePage);
        return new RecipePageResponse(recipes, recipePage.getNumber(), recipePage.getTotalPages(), (int) recipePage.getTotalElements());
    }

    @Override
    public RecipePageResponse getRecipesByIngredients(List<IngredientRequest> ingredientRequestList, int page) {
        List<Ingredient> ingredients = ingredientService.getIngredientsByIngredientRequests(ingredientRequestList);
        Specification<Recipe> spec = Specification.where(new RecipeWithIngredient(ingredients.get(0)));

        if (ingredients.size() > 1) {
            for (int x = 1; x < ingredients.size(); x++) {
                spec = spec.and(new RecipeWithIngredient(ingredients.get(x)));
            }
        }

        Pageable pageRequest = PageRequest.of(page, 12);
        Page<Recipe> recipePage = recipeRepositoryInterface.findAll(spec, pageRequest);

        List<RecipeListResponse> recipes = getRecipeResponse(recipePage);
        return new RecipePageResponse(recipes, recipePage.getNumber(), recipePage.getTotalPages(), (int) recipePage.getTotalElements());
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepositoryInterface.deleteById(id);
    }

    @Override
    public void addRecipeToCookbook(Long recipeId) throws Exception {
        cookbookService.addRecipe(recipeId);
    }

    @Override
    public Recipe updateRecipe(Long recipeId, RecipeRequest recipeRequest) {
        Recipe originalRecipe = getRecipe(recipeId);
        Recipe recipe = recipeRepository.updateRecipe(originalRecipe, recipeRequest);

        recipe = recipeRepositoryInterface.save(recipe);


        removeIngredients(recipe);
        List<Ingredient> ingredients = ingredientService.getIngredientsByIngredientRequests(recipeRequest.getRecipeIngredients());
        addIngredients(recipe, ingredients, recipeRequest.getRecipeIngredients());


        recipe = recipeRepositoryInterface.save(recipe);


        List<RecipeCategory> categories = categoryService.getCategoriesByCategoryRequests(recipeRequest.getCategories());
        removeRecipeFromCategories(recipe);
        addRecipeToCategories(recipe, categories);

        return recipeRepositoryInterface.save(recipe);
    }

    @Override
    public void addRecipeToCategories(Recipe recipe, List<RecipeCategory> categories) {
        for (int x = 0; x < categories.size(); x++) {
            categoryService.addRecipeToCategory(categories.get(x).getName(), recipe.getId());
        }
    }

    @Override
    public void removeRecipeFromCategories(Recipe recipe) {
        categoryService.removeRecipeFromCategories(recipe.getId());
    }

    @Override
    public void addIngredients(Recipe recipe, List<Ingredient> ingredients, List<IngredientRequest> ingredientRequests) {
        recipeRepository.addIngredients(recipe, ingredients);
        List<RecipeIngredient> recipeIngredients = recipeIngredientService.createRecipeIngredients(recipe, ingredientRequests);

        recipe = recipeRepository.addRecipeIngredients(recipe, recipeIngredients);
        recipeRepositoryInterface.save(recipe);
    }

    @Override
    public void removeIngredients(Recipe recipe) {
        List<Ingredient> ingredients = recipe.getIngredients();
        recipeRepository.removeIngredients(recipe, ingredients);
        recipeIngredientService.deleteRecipeIngredients(recipe);
    }

    @Override
    public RecipeCategory getCategoryFromParameter(String categoryName) {
        if (categoryName == "") {
            return null;
        } else {
            return categoryService.getCategory(categoryName);
        }
    }

    @Override
    public Sort getSortFromParameter(int sort) {
        if (sort == 1) {
            return Sort.by("createdDate").ascending();
        } else if (sort == 2) {
            return Sort.by("createdDate").descending();
        } else if (sort == 3) {
            return Sort.by("rating").ascending();
        } else if (sort == 4) {
            return Sort.by("rating").descending();
        } else {
            return Sort.by("createdDate").descending();
        }
    }

    @Override
    public void addScoreToRecipe(Long recipeId, RecipeVoteRequest recipeVoteRequest) {
        Recipe recipe = getRecipe(recipeId);
        ApplicationUser user = userService.getUser();

        List<Vote> votes = voteService.getVotesByRecipe(recipe);
        Boolean alreadyVoted = userAlreadyVoted(user.getId(), votes);

        if (alreadyVoted == false) {
            voteService.createVote(recipe, user, recipeVoteRequest.getScore());

            recipe = getRecipe(recipeId);

            updateRating(recipe, recipe.getVotes());
        } else {
            voteService.updateVote(recipe, user, recipeVoteRequest.getScore());

            recipe = getRecipe(recipeId);

            updateRating(recipe, recipe.getVotes());
        }
    }

    @Override
    public void updateRating(Recipe recipe, List<Vote> votes) {
        int[] scores = getScores(votes);
        float rating = calculateRating(scores);

        recipeRepository.updateRecipeRating(recipe, rating);
    }

    @Override
    public Boolean userAlreadyVoted(Long userId, List<Vote> votes) {
        Boolean voted = false;

        for (int x = 0; x < votes.size(); x++) {
            if (userId == votes.get(x).getUser().getId()) {
                voted = true;
            }
        }
        return voted;
    }

    @Override
    public void addCommentToRecipe(Long recipeId, RecipeCommentRequest recipeCommentRequest) {
        Recipe recipe = getRecipe(recipeId);
        String username = securityService.getUsernameFromUserDetails();
        ApplicationUser user = userService.getUserByName(username);

        commentService.createComment(recipe, user, recipeCommentRequest.getComment());
    }

    @Override
    public RecipePageResponse getUserRecipes(String username, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        ApplicationUser user = userService.getUserByName(username);
        Page<Recipe> recipePage = recipeRepositoryInterface.findAllByUser(user, pageRequest);
        List<RecipeListResponse> recipes = getRecipeResponse(recipePage);
        return new RecipePageResponse(recipes, recipePage.getNumber(), recipePage.getTotalPages(), (int) recipePage.getTotalElements());
    }

    @Override
    public Page<Recipe> getRecipesByCookbook(Cookbook cookbook, int page) {
        Pageable pageRequest = PageRequest.of(page, 12);
        return recipeRepositoryInterface.findAllByCookbooks(cookbook, pageRequest);
    }

    @Override
    public void updateRecipeImage(Long id, byte[] bytes) {
        Recipe recipe = getRecipe(id);
        byte[] compressedImage = imageCompDecomp.compressBytes(bytes);
        recipeRepository.updateRecipeImage(recipe, compressedImage);
    }

    @Override
    public void removeRecipeImage() {

    }

    private int[] getScores(List<Vote> votes) {
        int[] scores = new int[votes.size()];

        for (int x = 0; x < votes.size(); x++) {
            scores[x] = votes.get(x).getScore();
        }

        return scores;
    }

    private float calculateRating(int[] scores) {
        int sum = Arrays.stream(scores).sum();
        return (float) sum / scores.length;
    }

    private byte[] getDecompressedImage(byte[] image) {
        if (image != null) {
            return imageCompDecomp.decompressBytes(image);
        } else {
            return null;
        }
    }

    private List<CommentResponse> getCommentsResponse(List<Comment> comments) {
        List<CommentResponse> commentsResponses = new ArrayList<>();
        byte[] avatar;

        for (int x = 0; x < comments.size(); x++) {
            avatar = userService.getDecompressedAvatar(comments.get(x).getUser().getAvatar());
            commentsResponses.add(new CommentResponse(
                    comments.get(x).getComment(),
                    comments.get(x).getCreatedDate(),
                    comments.get(x).getUsername(),
                    avatar
            ));
        }

        return commentsResponses;
    }

    @Override
    public List<RecipeListResponse> getRecipeResponse(Page<Recipe> recipePage) {

        List<RecipeListResponse> recipes = new ArrayList<>();
        Recipe recipe;
        byte[] decompressedImage;

        for (int x = 0; x < recipePage.getContent().size(); x++) {
            recipe = recipePage.getContent().get(x);
            decompressedImage = getDecompressedImage(recipe.getPicture());
            recipes.add(new RecipeListResponse(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getDescription(),
                    recipe.getPreparationTime(),
                    recipe.getDifficulty(),
                    recipe.getRating(),
                    recipe.getCreatedDate(),
                    decompressedImage
            ));
        }

        return recipes;
    }
}
