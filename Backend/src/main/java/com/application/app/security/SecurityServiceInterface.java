package com.application.app.security;

public interface SecurityServiceInterface {
    Boolean isSecuredUpdateUser(Long userId);

    Boolean isSecuredUpdateUserAvatar(Long userId);

    Boolean isSecuredGetCookbook(Long cookbookId);

    Boolean isSecuredRemoveRecipeFromCookbook(Long cookbookId);

    Boolean isSecuredGetFridge(Long fridgeId);

    Boolean isSecuredUpdateFridge(Long fridgeId);

    Boolean isSecuredUpdateRecipe(Long recipeId);

    Boolean isSecuredDeleteRecipe(Long recipeId);

    Boolean isSecuredAddRecipeToCookbook(Long cookbookId);

    String getUsernameFromUserDetails();
}
