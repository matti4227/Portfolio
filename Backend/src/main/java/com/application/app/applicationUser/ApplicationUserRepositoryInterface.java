package com.application.app.applicationUser;

import com.application.app.recipe.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepositoryInterface extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);

    Optional<ApplicationUser> findById(Long id);

    ApplicationUser findByCookbookId(Long id);

    ApplicationUser findByFridgeId(Long id);

    ApplicationUser findByRecipes(Recipe recipe);

    Page<ApplicationUser> findAll(Pageable pageable);

    void deleteByUsername(String username);
}
