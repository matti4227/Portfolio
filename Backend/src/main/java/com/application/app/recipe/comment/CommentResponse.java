package com.application.app.recipe.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponse {
    private String comment;
    private Timestamp createdDate;
    private String username;
    private byte[] avatar;
}
