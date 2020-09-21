package com.application.app.applicationUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Repository
public class ApplicationUserRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApplicationUser createUser(ApplicationUserRequest userRequest) {
        ApplicationUser user = new ApplicationUser();
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());

        return user;
    }

    public ApplicationUser updateUserPassword(ApplicationUser originalUser, String newPassword) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        originalUser.setPassword(passwordEncoder.encode(newPassword));

        entityManager.merge(originalUser);
        entityManager.getTransaction().commit();

        return originalUser;
    }

    public ApplicationUser updateUserAvatar(ApplicationUser originalUser, byte[] avatar) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        if (avatar != null) {
            originalUser.setAvatar(avatar);
        }

        entityManager.merge(originalUser);
        entityManager.getTransaction().commit();

        return originalUser;
    }

    public ApplicationUser removeUserAvatar(ApplicationUser originalUser) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        if (originalUser.getAvatar() != null) {
            originalUser.setAvatar(null);
        }

        entityManager.merge(originalUser);
        entityManager.getTransaction().commit();

        return originalUser;
    }

    public void updateUserInfo(ApplicationUser originalUser, ApplicationUserEditInfoRequest userEditInfoRequest) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        if (userEditInfoRequest.getFirstName() != null) {
            originalUser.setFirstName(userEditInfoRequest.getFirstName());
        }
        if (userEditInfoRequest.getLastName() != null) {
            originalUser.setLastName(userEditInfoRequest.getLastName());
        }

        entityManager.merge(originalUser);
        entityManager.getTransaction().commit();
    }
}
