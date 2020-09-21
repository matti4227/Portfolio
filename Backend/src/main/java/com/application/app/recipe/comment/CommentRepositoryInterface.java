package com.application.app.recipe.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepositoryInterface extends JpaRepository<Comment, Long> {
}
