package com.application.app.recipe.specifications;

import com.application.app.recipe.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class RecipeWithPreparationTime implements Specification<Recipe> {

    private int preparationTime;

    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (preparationTime == 0) {
            return cb.isTrue(cb.literal(true));
        }
        return cb.equal(root.get("preparationTime"), preparationTime);
    }
}
