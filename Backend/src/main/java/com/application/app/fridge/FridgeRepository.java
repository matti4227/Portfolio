package com.application.app.fridge;

import com.application.app.ingredient.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class FridgeRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Fridge addIngredientsToFridge(Fridge fridge, List<Ingredient> ingredients) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        for (int x = 0; x < ingredients.size(); x++) {
            fridge.getIngredients().add(ingredients.get(x));
            ingredients.get(x).getFridges().add(fridge);
        }

        entityManager.merge(fridge);
        entityManager.getTransaction().commit();

        return fridge;
    }

    public Fridge removeIngredientsFromFridge(Fridge fridge, List<Ingredient> ingredients) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        for (int x = 0; x < ingredients.size(); x++) {
            ingredients.get(x).getFridges().remove(fridge);
        }

        fridge.getIngredients().clear();

        entityManager.merge(fridge);
        entityManager.getTransaction().commit();

        return fridge;
    }
}
