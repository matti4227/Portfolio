package com.application.app.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "")
    public ResponseEntity<Ingredient> addIngredient(@RequestBody IngredientRequest ingredientRequest) {
        try {
            Ingredient response = ingredientService.addIngredient(ingredientRequest.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(value = "")
    public ResponseEntity<List<IngredientResponse>> getIngredients() {
        try {
            List<IngredientResponse> response = ingredientService.getIngredients();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping(value = "/page")
    public ResponseEntity<IngredientPageResponse> getIngredientsPage(@RequestParam(value = "page", defaultValue = "0") int page) {
        try {
            IngredientPageResponse response = ingredientService.getIngredientsPage(page);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteIngredient(@PathVariable(value = "id") Long id) {
        try {
            ingredientService.deleteIngredient(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
