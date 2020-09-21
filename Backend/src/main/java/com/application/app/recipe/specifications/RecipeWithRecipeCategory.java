package com.application.app.recipe.specifications;

import com.application.app.recipe.Recipe;
import com.application.app.recipeCategory.RecipeCategory;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class RecipeWithRecipeCategory implements Specification<Recipe> {

    private RecipeCategory category;

    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (category == null) {
            return cb.isTrue(cb.literal(true));
        }
        return cb.isMember(category, root.get("recipeCategories"));
    }
}
