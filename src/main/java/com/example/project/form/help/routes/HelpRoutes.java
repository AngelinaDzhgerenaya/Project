package com.example.project.form.help.routes;

import com.example.project.base.routes.BaseRoutes;

public class HelpRoutes {
    private final static String ROOT = BaseRoutes.NOT_SECURED + "/help";
    public final static  String CREATE = ROOT +"/form";
    public static final String BY_ID = ROOT + "/{id}";
    public final static  String EDIT = ROOT +"/edit/{id}";
    public final static  String DElETE = BY_ID;
    public final static  String SEARCH = ROOT;
    public final static  String SUCCESSFUL= CREATE + "/successful";
}
