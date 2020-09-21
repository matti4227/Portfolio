package com.application.app.applicationUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationUserPageResponse {
    private List<ApplicationUserWithIdResponse> content;
    private int currentPage;
    private int totalPages;
    private int totalResults;
}
