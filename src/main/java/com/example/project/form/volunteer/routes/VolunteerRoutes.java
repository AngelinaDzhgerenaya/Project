package com.example.project.form.volunteer.routes;

import com.example.project.base.routes.BaseRoutes;

public class VolunteerRoutes {
    private final static String ROOT = BaseRoutes.NOT_SECURED + "/volunteer";
    private final static String ROOTAPI =BaseRoutes.API + "/volunteer";
    public final static  String CREATE = ROOTAPI +"/form";
    public final static  String BY_ID = ROOT + "/{id}";
    public final static  String EDIT = ROOTAPI + "/edit/{id}";
    public final static  String DElETE = ROOTAPI+ "/{id}";
    public final static  String SEARCH = ROOT;
    public final static  String SUCCESSFUL= CREATE + "/successful";

}
