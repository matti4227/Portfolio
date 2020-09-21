package com.application.app.security;

public class SecurityConstants {
    public static final String SIGN_UP_URL = "/register";
    public static final String AUTHENTICATE_URL = "/authenticate";
    public static final String RECIPES = "/recipes";
    public static final String RECIPE_CATEGORIES = "/recipeCategories";
    public static final String SINGLE_RECIPE = "/recipes/**";
    public static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;
    public static final String TOKEN_SECRET = "ufasyvdcrfbe2g834bfub8weydb8f234ibnsoijfd9u294n";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
