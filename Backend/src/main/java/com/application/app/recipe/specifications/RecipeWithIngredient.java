package com.application.app.recipe.specifications;

import com.application.app.ingredient.Ingredient;
import com.application.app.recipe.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class RecipeWithIngredient implements Specification<Recipe> {

    private Ingredient ingredient;

    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.isMember(ingredient, root.get("ingredients"));
    }
}
