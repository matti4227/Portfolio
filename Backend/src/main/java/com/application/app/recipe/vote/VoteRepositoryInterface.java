package com.application.app.recipe.vote;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepositoryInterface extends JpaRepository<Vote, Long> {
    List<Vote> findAllByRecipe(Recipe recipe);

    Vote findByUserAndRecipe(ApplicationUser user, Recipe recipe);
}
