package com.application.app.applicationUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationUserWithIdResponse {
    private Long id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
}
