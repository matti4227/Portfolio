package com.application.app.fridge;

import com.application.app.ingredient.IngredientRequest;
import com.application.app.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
@RestController
@RequestMapping(value = "/fridge")
public class FridgeController {

    @Autowired
    private FridgeService fridgeService;

    @GetMapping(value = "")
    public ResponseEntity<Fridge> getFridge() {
        try {
            Fridge response = fridgeService.getFridgeByUser();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Fridge> updateFridge(@RequestBody List<IngredientRequest> ingredientRequestList) {
        try {
            Fridge response = fridgeService.updateFridge(ingredientRequestList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
