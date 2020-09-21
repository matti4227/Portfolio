package com.application.app.recipe.vote;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.recipe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Repository
public class VoteRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Vote createVote(Recipe recipe, ApplicationUser user, int score) {

        Vote vote = new Vote();
        vote.setRecipe(recipe);
        vote.setScore(score);
        vote.setUser(user);

        return vote;
    }

    public Vote updateVote(Vote vote, int score) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        vote.setScore(score);

        entityManager.merge(vote);
        entityManager.getTransaction().commit();

        return vote;
    }
}
