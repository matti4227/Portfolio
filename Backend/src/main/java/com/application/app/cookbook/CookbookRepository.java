package com.application.app.cookbook;

import com.application.app.recipe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Repository(value = "cookbookRepository")
public class CookbookRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Cookbook addRecipe(Cookbook cookbook, Recipe recipe) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        cookbook.getRecipes().add(recipe);
        recipe.getCookbooks().add(cookbook);

        entityManager.merge(cookbook);
        entityManager.getTransaction().commit();

        return cookbook;
    }

    public Cookbook removeRecipe(Cookbook cookbook, Recipe recipe) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        cookbook.getRecipes().remove(recipe);
        recipe.getCookbooks().remove(cookbook);


        entityManager.merge(cookbook);
        entityManager.getTransaction().commit();

        return cookbook;
    }
}
