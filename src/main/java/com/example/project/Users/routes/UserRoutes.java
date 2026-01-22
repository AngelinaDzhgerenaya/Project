package com.example.project.Users.routes;

import com.example.project.base.routes.BaseRoutes;

public class UserRoutes {
    private final static String ROOT = BaseRoutes.API + "/user";

    public final static  String REGISTRATION = BaseRoutes.NOT_SECURED + "/v1/registration";

    public final static  String LOGIN = BaseRoutes.NOT_SECURED + "/v1/login";

    public final static  String ME = ROOT + "/me";
    public final static  String LOGOUT = BaseRoutes.NOT_SECURED + "/logout";
    public final static  String NOTME = BaseRoutes.NOT_SECURED + "/notme";
}
