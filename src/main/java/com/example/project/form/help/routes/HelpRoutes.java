package com.example.project.form.help.routes;

import com.example.project.base.routes.BaseRoutes;

public class HelpRoutes {
    private final static String ROOT = BaseRoutes.NOT_SECURED + "/help";
    public final static  String CREATE = ROOT +"/form";
    public static final String BY_ID = ROOT + "/{id}";
    public final static  String EDIT = BY_ID +"/edit";
    public final static  String DElETE = BY_ID;
    public final static  String SEARCH = ROOT;
}
