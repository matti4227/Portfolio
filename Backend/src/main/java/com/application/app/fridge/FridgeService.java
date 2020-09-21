package com.application.app.fridge;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.applicationUser.ApplicationUserService;
import com.application.app.ingredient.Ingredient;
import com.application.app.ingredient.IngredientRequest;
import com.application.app.ingredient.IngredientService;
import com.application.app.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FridgeService implements FridgeServiceInterface {

    @Autowired
    private ApplicationUserService userService;

    @Autowired
    private FridgeRepositoryInterface fridgeRepositoryInterface;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private FridgeRepository fridgeRepository;

    @Autowired
    private SecurityService securityService;

    @Override
    public void createFridge(Long id) {
        ApplicationUser user = userService.getUserById(id);
        Fridge fridge = new Fridge();
        fridge.setUser(user);
        fridgeRepositoryInterface.save(fridge);
    }

    @Override
    public Fridge getFridge(Long fridgeId) {
        return fridgeRepositoryInterface.findById(fridgeId).orElse(null);
    }

    @Override
    public Fridge getFridgeByUser() {
        String username = securityService.getUsernameFromUserDetails();
        ApplicationUser user = userService.getUserByName(username);

        return fridgeRepositoryInterface.findFridgeByUser(user);
    }

    @Override
    public Fridge updateFridge(List<IngredientRequest> ingredientRequests) {
        Fridge fridge = getFridgeByUser();

        if (fridge.getIngredients().size() > 0) {
            removeIngredients(fridge);
        }

        if (ingredientRequests.size() > 0) {
            List<Ingredient> ingredients = ingredientService.getIngredientsByIngredientRequests(ingredientRequests);
            fridge = addIngredients(fridge, ingredients);
        }

        return fridgeRepositoryInterface.save(fridge);
    }

    @Override
    public Fridge addIngredients(Fridge fridge, List<Ingredient> ingredients) {
        return fridgeRepository.addIngredientsToFridge(fridge, ingredients);
    }

    @Override
    public void removeIngredients(Fridge fridge) {
        List<Ingredient> ingredients = fridge.getIngredients();
        fridgeRepository.removeIngredientsFromFridge(fridge, ingredients);
    }
}
