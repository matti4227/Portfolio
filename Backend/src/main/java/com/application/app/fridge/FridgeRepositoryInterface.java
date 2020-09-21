package com.application.app.fridge;

import com.application.app.applicationUser.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepositoryInterface extends JpaRepository<Fridge, Long> {
    Fridge findFridgeByUser(ApplicationUser user);
}
