package com.application.app.recipe.comment;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.recipe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements CommentServiceInterface {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentRepositoryInterface commentRepositoryInterface;

    @Override
    public void createComment(Recipe recipe, ApplicationUser user, String recipeComment) {
        Comment comment = commentRepository.createComment(recipe, user, recipeComment);
        commentRepositoryInterface.save(comment);
    }
}
