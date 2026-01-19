package com.example.project.Users.routes;

import com.example.project.base.routes.BaseRoutes;

public class UserRoutes {
    private final static String ROOT = BaseRoutes.API + "/client";

    public final static  String REGISTRATION = BaseRoutes.NOT_SECURED + "/v1/registration";

    public final static  String ME = ROOT + "/me";
}
