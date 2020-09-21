package com.application.app.cookbook;

import com.application.app.applicationUser.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookbookRepositoryInterface extends JpaRepository<Cookbook, Long> {
    Cookbook findCookbookByUser(ApplicationUser user);
}
