package com.application.app.recipe.vote;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.recipe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService implements VoteServiceInterface {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteRepositoryInterface voteRepositoryInterface;

    @Override
    public Vote createVote(Recipe recipe, ApplicationUser user, int score) {
        Vote vote = voteRepository.createVote(recipe, user, score);
        return voteRepositoryInterface.save(vote);
    }

    @Override
    public Vote getVote(Recipe recipe, ApplicationUser user) {
        return voteRepositoryInterface.findByUserAndRecipe(user, recipe);
    }

    @Override
    public List<Vote> getVotesByRecipe(Recipe recipe) {
        return voteRepositoryInterface.findAllByRecipe(recipe);
    }

    @Override
    public void updateVote(Recipe recipe, ApplicationUser user, int score) {
        Vote vote = voteRepositoryInterface.findByUserAndRecipe(user, recipe);
        voteRepository.updateVote(vote, score);
    }
}
