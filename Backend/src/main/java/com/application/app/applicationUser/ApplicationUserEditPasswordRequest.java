package com.application.app.applicationUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationUserEditPasswordRequest {
    private String oldPassword;
    private String newPassword;
}
