package com.application.app.recipeCategory;

import com.application.app.recipe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/recipeCategories")
public class RecipeCategoryController {

    @Autowired
    private RecipeCategoryService recipeCategoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<RecipeCategory> createRecipeCategory(@RequestBody RecipeCategoryRequest recipeCategoryRequest ) {
        try {
            RecipeCategory response = recipeCategoryService.addCategory(recipeCategoryRequest.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<List<RecipeCategoryWithIdResponse>> getAllCategories() {
        try {
            List<RecipeCategoryWithIdResponse> response = recipeCategoryService.getCategories();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/category")
    public ResponseEntity<List<Recipe>> getCategory(@RequestBody RecipeCategoryRequest recipeCategoryRequest) {
        try {
            List<Recipe> response = recipeCategoryService.getRecipesByCategory(recipeCategoryRequest.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{name}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "name") String name) {
        try {
            recipeCategoryService.deleteIngredient(name);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
