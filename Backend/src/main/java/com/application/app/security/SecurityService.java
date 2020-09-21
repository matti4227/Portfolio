package com.application.app.security;

import com.application.app.applicationUser.ApplicationUser;
import com.application.app.applicationUser.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements SecurityServiceInterface {

    @Autowired
    private ApplicationUserService userService;

    @Override
    public Boolean isSecuredUpdateUser(Long userId) {
        ApplicationUser user = userService.getUser();
        return compare(user.getUsername(), getUsernameFromUserDetails());
    }

    @Override
    public Boolean isSecuredUpdateUserAvatar(Long userId) {
        ApplicationUser user = userService.getUser();
        return compare(user.getUsername(), getUsernameFromUserDetails());
    }

    @Override
    public Boolean isSecuredGetCookbook(Long cookbookId) {
        ApplicationUser user = userService.getUserByCookbook(cookbookId);
        return compare(user.getUsername(), getUsernameFromUserDetails());
    }

    @Override
    public Boolean isSecuredRemoveRecipeFromCookbook(Long cookbookId) {
        ApplicationUser user = userService.getUserByCookbook(cookbookId);
        return compare(user.getUsername(), getUsernameFromUserDetails());
    }

    @Override
    public Boolean isSecuredGetFridge(Long fridgeId) {
        ApplicationUser user = userService.getUserByFridge(fridgeId);
        return compare(user.getUsername(), getUsernameFromUserDetails());
    }

    @Override
    public Boolean isSecuredUpdateFridge(Long fridgeId) {
        ApplicationUser user = userService.getUserByFridge(fridgeId);
        return compare(user.getUsername(), getUsernameFromUserDetails());
    }

    @Override
    public Boolean isSecuredUpdateRecipe(Long recipeId) {
        ApplicationUser user = userService.getUserByRecipe(recipeId);
        return compare(user.getUsername(), getUsernameFromUserDetails());
    }

    @Override
    public Boolean isSecuredDeleteRecipe(Long recipeId) {
        ApplicationUser user = userService.getUserByRecipe(recipeId);
        return compare(user.getUsername(), getUsernameFromUserDetails());
    }

    @Override
    public Boolean isSecuredAddRecipeToCookbook(Long cookbookId) {
        ApplicationUser user = userService.getUserByCookbook(cookbookId);
        return compare(user.getUsername(), getUsernameFromUserDetails());
    }

    @Override
    public String getUsernameFromUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser") {
            return null;
        } else {
            String username = ((UserDetails)auth.getPrincipal()).getUsername();
            return username;
        }
    }

    private Boolean compare(String username, String usernameFromUserDetails) {
        return username.matches(usernameFromUserDetails);
    }

}
