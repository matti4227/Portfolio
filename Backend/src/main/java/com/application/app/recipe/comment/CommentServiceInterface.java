package com.application.app.recipe.comment;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.recipe.Recipe;
import org.springframework.stereotype.Service;

@Service
public interface CommentServiceInterface {
    void createComment(Recipe recipe, ApplicationUser user, String comment);
}
