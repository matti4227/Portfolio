package com.application.app.recipe.comment;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.recipe.Recipe;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {

    public Comment createComment(Recipe recipe, ApplicationUser user, String recipeComment) {

        Comment comment = new Comment();
        comment.setRecipe(recipe);
        comment.setUser(user);
        comment.setComment(recipeComment);
        comment.setUsername(user.getUsername());

        return comment;
    }
}
