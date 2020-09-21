package com.application.app.recipe.comment;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.recipe.Recipe;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 1000)
    private String comment;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 24)
    @Column(updatable = false)
    private String username;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ApplicationUser user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Recipe recipe;
}
