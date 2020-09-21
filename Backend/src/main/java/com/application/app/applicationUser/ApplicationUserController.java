package com.application.app.applicationUser;

import com.application.app.cookbook.CookbookService;
import com.application.app.fridge.FridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.application.app.security.SecurityConstants.SIGN_UP_URL;

@RestController
@RequestMapping
public class ApplicationUserController {

    @Autowired
    private ApplicationUserService userService;

    @Autowired
    private CookbookService cookbookService;

    @Autowired
    private FridgeService fridgeService;

    @PostMapping(value = SIGN_UP_URL)
    public ResponseEntity<?> createUser(@RequestBody ApplicationUserRequest userRequest) {
        try {
            ApplicationUser userCreated = userService.createUser(userRequest);
            cookbookService.createCookbook(userCreated.getId());
            fridgeService.createFridge(userCreated.getId());

            return new ResponseEntity<>(userCreated, HttpStatus.OK);
        } catch(Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity<ApplicationUserResponse> getUser() {
        try {
            ApplicationUser user = userService.getUser();
            ApplicationUserResponse response = userService.getResponseUser(user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<ApplicationUserResponse> getUserByUsername(@PathVariable(value = "username") String username) {
        try {
            ApplicationUser user = userService.getUserByName(username);
            ApplicationUserResponse response = userService.getResponseUser(user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/users")
    public ResponseEntity<ApplicationUserPageResponse> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page) {
        try {
            ApplicationUserPageResponse response = userService.getAllUsers(page);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping(value = "/user/password")
    public ResponseEntity<?> updateUserPassword(@RequestBody ApplicationUserEditPasswordRequest userEditPasswordRequest) {
        try {
            userService.updateUserPassword(userEditPasswordRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping(value = "/user/info")
    public ResponseEntity<?> updateUserInfo(@RequestBody ApplicationUserEditInfoRequest userEditInfoRequest) {
        try {
            userService.updateUserInfo(userEditInfoRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping(value = "/user/avatar", consumes = "multipart/form-data")
    public ResponseEntity<ApplicationUser> updateUserAvatar(@RequestPart(value = "avatar") MultipartFile file) {
        try {
            ApplicationUser response = userService.updateUserAvatar(file.getBytes());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping(value = "/user/avatar/remove")
    public ResponseEntity<ApplicationUser> removeUserAvatar() {
        try {
            ApplicationUser response = userService.removeUserAvatar();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "username") String username) {
        try {
            userService.deleteUserByUsername(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception er) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
