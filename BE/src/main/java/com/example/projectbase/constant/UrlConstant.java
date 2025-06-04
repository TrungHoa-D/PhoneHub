package com.example.projectbase.constant;

public class UrlConstant {

  public static class Auth {
    private static final String PRE_FIX = "/auth";

    public static final String LOGIN = PRE_FIX + "/login";
    private Auth() {
    }
  }

  public static class User {
    private static final String PRE_FIX = "/user";
    private static final String AUTH = "/admin";

    public static final String GET_USERS =AUTH + PRE_FIX;
    public static final String GET_USER =AUTH +  PRE_FIX + "/{userId}";
    public static final String GET_CURRENT_USER = PRE_FIX + "/current";
    public static final String CREATE_USER =AUTH +  PRE_FIX;
    public static final String UPDATE_USER =AUTH +  PRE_FIX + "/{userId}";
    public static final String DELETE_USER =AUTH +  PRE_FIX + "/{userId}";
    private User() {
    }
  }

  public static class Phone {
    private static final String PRE_FIX = "/phone";
    private static final String AUTH = "/admin";
    public static final String CREATE_PHONE = AUTH + PRE_FIX;
    public static final String GET_ALL_PHONE = PRE_FIX;
    public static final String GET_FILTERED_PHONE = PRE_FIX + "/filtered";
    public static final String GET_PHONE_BY_BRAND = PRE_FIX+ "/brand";
    public static final String GET_PHONE_BY_NAME = PRE_FIX+ "/name";
    public static final String GET_PHONE_BY_RAM = PRE_FIX+ "/ram";
    public static final String GET_PHONE_BY_ROM = PRE_FIX+ "/rom";
    public static final String GET_PHONE_BY_SCREEN = PRE_FIX+ "/screen";
    public static final String GET_PHONE_BY_ID = PRE_FIX+ "/id";
    public static final String GET_PHONE_BY_PRICE_RANGE = PRE_FIX+ "/priceRange";
    public static final String UPDATE_PHONE =AUTH +  PRE_FIX;
    public static final String DELETE_PHONE =AUTH +  PRE_FIX;
    private Phone() {}
  }

  public static class Review{
    private static final String PRE_FIX = "/review";
    public static final String GET_ALL_REVIEW = PRE_FIX;
    public static final String GET_REVIEW_BY_ID = PRE_FIX + "/id";
    public static final String GET_REVIEW_BY_USER_ID = PRE_FIX + "/user";
    public static final String GET_REVIEW_BY_PHONE_ID = PRE_FIX + "/phone";
    public static final String CREATE_REVIEW = PRE_FIX + "/review";
    public static final String UPDATE_REVIEW = PRE_FIX + "/review";
    public static final String DELETE_REVIEW = PRE_FIX + "/review";
  }

}
