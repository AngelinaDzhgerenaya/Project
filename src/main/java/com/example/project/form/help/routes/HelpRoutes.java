package com.example.project.form.help.routes;

import com.example.project.base.routes.BaseRoutes;

public class HelpRoutes {
    private final static String ROOT = BaseRoutes.NOT_SECURED + "/help";
    private final static String ROOTAPI =BaseRoutes.API + "/help";
    public final static  String CREATE = ROOTAPI +"/form";
    public static final String BY_ID = ROOT + "/{id}";
    public final static  String EDIT = ROOTAPI +"/edit/{id}";
    public final static  String DElETE = ROOTAPI+ "/{id}";
    public final static  String SEARCH = ROOT;
    public final static  String SUCCESSFUL= CREATE + "/successful";

}
