package com.application.app.applicationUser;

import com.application.app.image.ImageCompDecomp;
import com.application.app.recipe.Recipe;
import com.application.app.recipe.RecipeService;
import com.application.app.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationUserService implements ApplicationUserServiceInterface {

    @Autowired
    private ApplicationUserRepositoryInterface userRepositoryInterface;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ImageCompDecomp imageCompDecomp;

    @Override
    public ApplicationUser createUser(ApplicationUserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        ApplicationUser user = userRepository.createUser(userRequest);
        return userRepositoryInterface.save(user);
    }

    @Override
    public ApplicationUser getUserById(Long id) {
        return userRepositoryInterface.findById(id).orElse(null);
    }

    @Override
    public ApplicationUser getUser() {
        String username = securityService.getUsernameFromUserDetails();
        ApplicationUser user = getUserByName(username);
        return user;
    }

    @Override
    public ApplicationUser getUserByCookbook(Long id) {
        return userRepositoryInterface.findByCookbookId(id);
    }

    @Override
    public ApplicationUser getUserByFridge(Long id) {
        return userRepositoryInterface.findByFridgeId(id);
    }

    @Override
    public ApplicationUser getUserByRecipe(Long id) {
        Recipe recipe = recipeService.getRecipe(id);
        return userRepositoryInterface.findByRecipes(recipe);
    }

    @Override
    public ApplicationUserResponse getResponseUser(ApplicationUser user) throws Exception {
        if (user != null) {
            byte[] decompressedAvatar = getDecompressedAvatar(user.getAvatar());

            return new ApplicationUserResponse(
                    user.getEmail(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    decompressedAvatar);
        } else {
            throw new Exception();
        }
    }

    private ApplicationUserWithIdResponse getResponseUserWithId(ApplicationUser user) throws Exception {
        if (user != null) {
            return new ApplicationUserWithIdResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName());
        } else {
            throw new Exception();
        }
    }

    @Override
    public ApplicationUser getUserByName(String username) {
        return userRepositoryInterface.findByUsername(username);
    }

//    @Override
//    public List<ApplicationUser> getUsers() {
//        return userRepositoryInterface.findAll();
//    }

    @Override
    public ApplicationUserPageResponse getAllUsers(int page) throws Exception {
        Page<ApplicationUser> recipePage = userRepositoryInterface.findAll(PageRequest.of(page, 15));

        List<ApplicationUserWithIdResponse> users = new ArrayList<>();
        for (int x = 0; x < recipePage.getTotalElements(); x++) {
            users.add(getResponseUserWithId(recipePage.getContent().get(x)));
        }
        return new ApplicationUserPageResponse(users, recipePage.getNumber(), recipePage.getTotalPages(), (int) recipePage.getTotalElements());
    }

    @Override
    public void updateUserPassword(ApplicationUserEditPasswordRequest userEditPasswordRequest) throws Exception {
        if (userEditPasswordRequest.getOldPassword() != null && userEditPasswordRequest.getNewPassword() != null) {
            ApplicationUser originalUser = getUser();
            if (passwordEncoder.matches(userEditPasswordRequest.getOldPassword(), originalUser.getPassword())) {
                userRepository.updateUserPassword(originalUser, userEditPasswordRequest.getNewPassword());
            } else {
                throw new Exception();
            }
        }
    }

    @Override
    public ApplicationUser updateUserAvatar(byte[] avatar) {
        ApplicationUser originalUser = getUser();
        byte[] compressedAvatar = imageCompDecomp.compressBytes(avatar);
        return userRepository.updateUserAvatar(originalUser, compressedAvatar);
    }

    @Override
    public ApplicationUser removeUserAvatar() {
        ApplicationUser originalUser = getUser();
        return userRepository.removeUserAvatar(originalUser);
    }

    @Override
    public void updateUserInfo(ApplicationUserEditInfoRequest userEditInfoRequest) {
        ApplicationUser originalUser = getUser();
        userRepository.updateUserInfo(originalUser, userEditInfoRequest);
    }

    @Override
    public void deleteUserByUsername(String username) {
        ApplicationUser user = getUserByName(username);
        userRepositoryInterface.delete(user);
    }

    @Override
    public byte[] getDecompressedAvatar(byte[] avatar) {
        if (avatar != null) {
            return imageCompDecomp.decompressBytes(avatar);
        } else {
            return null;
        }
    }
}
