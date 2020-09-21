package com.application.app.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepositoryInterface extends JpaRepository<Ingredient, Long> {
    Ingredient findByName(String ingredientName);
    List<Ingredient> findAllByOrderByName();
}
