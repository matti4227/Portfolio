package com.application.app.cookbook;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.applicationUser.ApplicationUserService;
import com.application.app.recipe.*;
import com.application.app.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CookbookService implements CookbookServiceInterface {

    @Autowired
    private CookbookRepositoryInterface cookbookRepositoryInterface;

    @Autowired
    private CookbookRepository cookbookRepository;

    @Autowired
    private RecipeRepositoryInterface recipeRepositoryInterface;

    @Autowired
    private ApplicationUserService userService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private SecurityService securityService;

    @Override
    public void createCookbook(Long id) {
        ApplicationUser user = userService.getUserById(id);
        Cookbook cookbook = new Cookbook();
        cookbook.setUser(user);
        cookbookRepositoryInterface.save(cookbook);
    }

    @Override
    public Cookbook getCookbook(Long cookbookId) {
        return cookbookRepositoryInterface.findById(cookbookId).orElse(null);
    }

    @Override
    public Recipe getRecipeFromCookbook(Long recipeId) {
        Recipe recipe = recipeService.getRecipe(recipeId);
        return recipe;
    }

    @Override
    public Cookbook addRecipe(Long recipeId) throws Exception {
        Recipe recipe = recipeRepositoryInterface.findById(recipeId).orElse(null);
        Cookbook cookbook = getCookbookByUser();

        if (checkIfRecipeAlreadyInCookbook(recipe) == true) {
            throw new Exception();
        } else {
            cookbook = cookbookRepository.addRecipe(cookbook, recipe);
            cookbookRepositoryInterface.save(cookbook);
            return cookbook;
        }
    }

    @Override
    public RecipePageResponse getCookbookByCookbook(int page) {
        String username = securityService.getUsernameFromUserDetails();
        ApplicationUser user = userService.getUserByName(username);

        Cookbook cookbook = cookbookRepositoryInterface.findCookbookByUser(user);
        Page<Recipe> recipePage = recipeService.getRecipesByCookbook(cookbook, page);
        List<RecipeListResponse> recipes = recipeService.getRecipeResponse(recipePage);

        return new RecipePageResponse(recipes, recipePage.getNumber(), recipePage.getTotalPages(), (int) recipePage.getTotalElements());
    }

    @Override
    public Cookbook getCookbookByUser() {
        String username = securityService.getUsernameFromUserDetails();
        ApplicationUser user = userService.getUserByName(username);

       return cookbookRepositoryInterface.findCookbookByUser(user);
    }

    @Override
    public Cookbook removeRecipeFromCookbook(Long recipeId) {
        Recipe recipe = getRecipeFromCookbook(recipeId);
        Cookbook cookbook = getCookbookByUser();

        cookbook = cookbookRepository.removeRecipe(cookbook, recipe);

        return cookbookRepositoryInterface.save(cookbook);
    }

    @Override
    public boolean checkIfRecipeAlreadyInCookbook(Recipe recipe) {
        Cookbook cookbook = getCookbookByUser();
        boolean flag = false;

        for (int x = 0; x < cookbook.getRecipes().size(); x++) {
            if (cookbook.getRecipes().get(x).getId() == recipe.getId()) {
                flag = true;
                break;
            }
        }

        return flag;
    }

}
