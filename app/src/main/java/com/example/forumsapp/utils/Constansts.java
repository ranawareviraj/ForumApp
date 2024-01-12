package com.example.forumsapp.utils;

public class Constansts {

    // Fragment arguments
    public static final String ARG_PARAM_FORUM = "ARG_PARAM_FORUM";
    public static final String ARG_PARAM_AUTH = "ARG_PARAM_AUTH";

    // JSON keys
    public static final String STATUS = "status";
    public static final String TOKEN = "token";
    public static final String USER_ID = "user_id";
    public static final String USER_FNAME = "user_fname";
    public static final String USER_LNAME = "user_lname";
    public static final String THREAD_ID = "thread_id";
    public static final String TITLE = "title";
    public static final String CREATED_AT = "created_at";
    public static final String CREATED_BY = "created_by";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String FNAME = "fname";
    public static final String LNAME = "lname";
    public static final String MESSAGE_ID = "message_id";
    public static final String MESSAGE = "message";

    // Headers
    public static final String AUTHORIZATION = "Authorization";

    // API URLs
    public static final String ADD_THREAD_URL = "https://www.theappsdr.com/api/thread/add";
    public static final String ADD_MESSAGE_URL = "https://www.theappsdr.com/api/message/add";
    public static final String DELETE_THREAD_URL = "https://www.theappsdr.com/api/thread/delete/";
    public static final String GET_FORUMS_URL = "https://www.theappsdr.com/api/threads";
    public static final String LOGIN_API_URL = "https://www.theappsdr.com/api/login";
    public static final String REGISTER_API_URL = "https://www.theappsdr.com/api/register";
    public static final String GET_MESSAGES_URL = "https://www.theappsdr.com/api/messages/";

    // Fragment titles
    public static final String FORUM_MESSAGES_FRAGMENT_TITLE = "Forum Messages";
    public static final String CREATE_FORUM_FRAGMENT_TITLE = "Create Forum";
    public static final String FORUMS_FRAGMENT_TITLE = "Forums";
    public static final String REGISTER_FRAGMENT_TITLE = "Register";
    public static final String LOGIN_FRAGMENT_TITLE = "Login";
}
