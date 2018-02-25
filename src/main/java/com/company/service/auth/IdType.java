package com.company.service.auth;


public enum IdType {
    APP_ID,
    EMAIL,
    PHONE_NUMBER,
    /**
     * EMAIL или PHONE_NUMBER
     */
    LOGIN,
    FACEBOOK_ID,
    GOOGLE_ID,
    VK_ID,
    TWITTER_ID}