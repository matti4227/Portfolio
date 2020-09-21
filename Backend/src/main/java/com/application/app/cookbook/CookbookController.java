package com.application.app.cookbook;

import com.application.app.recipe.Recipe;
import com.application.app.recipe.RecipePageResponse;
import com.application.app.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
@RestController
@RequestMapping(value = "/cookbook")
public class CookbookController {

    @Autowired
    private CookbookService cookbookService;

    @GetMapping(value = "")
    public ResponseEntity<RecipePageResponse> getCookbook(@RequestParam(value = "page", defaultValue = "0") int page) {
        try {
            RecipePageResponse response = cookbookService.getCookbookByCookbook(page);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{recipeId}")
    public ResponseEntity<Recipe> getRecipeFromCookbook(@PathVariable Long recipeId) {
        try {
            Recipe response = cookbookService.getRecipeFromCookbook(recipeId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{recipeId}")
    public ResponseEntity<Cookbook> removeRecipe(@PathVariable Long recipeId) {
        try {
            Cookbook response = cookbookService.removeRecipeFromCookbook(recipeId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
