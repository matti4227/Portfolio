package com.application.app.recipe.specifications;

import com.application.app.recipe.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class RecipeWithNameSearch implements Specification<Recipe> {

    private String name;

    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (name == null) {
            return cb.isTrue(cb.literal(true));
        }
        return cb.like(root.get("name"), "%" + name + "%");
    }
}
