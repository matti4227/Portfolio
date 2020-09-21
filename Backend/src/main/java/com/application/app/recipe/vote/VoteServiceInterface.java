package com.application.app.recipe.vote;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.recipe.Recipe;

import java.util.List;

public interface VoteServiceInterface {
    Vote createVote(Recipe recipe, ApplicationUser user, int score);

    Vote getVote(Recipe recipe, ApplicationUser user);

    List<Vote> getVotesByRecipe(Recipe recipe);

    void updateVote(Recipe recipe, ApplicationUser user, int score);
}
