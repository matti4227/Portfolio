package com.application.app.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class RecipeListResponse {
    private Long id;
    private String name;
    private String description;
    private int preparationTime;
    private int difficulty;
    private float rating;
    private Timestamp createdDate;
    private byte[] picture;
}