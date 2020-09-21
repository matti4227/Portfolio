package com.application.app.applicationUser;

import java.util.List;

public interface ApplicationUserServiceInterface {

    ApplicationUser createUser(ApplicationUserRequest user);

    ApplicationUser getUserById(Long id);

    ApplicationUser getUser();

    ApplicationUser getUserByCookbook(Long id);

    ApplicationUser getUserByFridge(Long id);

    ApplicationUser getUserByRecipe(Long id);

    ApplicationUserResponse getResponseUser(ApplicationUser user) throws Exception;

    ApplicationUserPageResponse getAllUsers(int page) throws Exception;

    ApplicationUser getUserByName(String username);

//    List<ApplicationUser> getUsers();

    void updateUserPassword(ApplicationUserEditPasswordRequest userEditPasswordRequest) throws Exception;

    ApplicationUser updateUserAvatar(byte[] avatar);

    byte[] getDecompressedAvatar(byte[] avatar);

    ApplicationUser removeUserAvatar();

    void updateUserInfo(ApplicationUserEditInfoRequest userEditInfoRequest);

    void deleteUserByUsername(String username);
}
