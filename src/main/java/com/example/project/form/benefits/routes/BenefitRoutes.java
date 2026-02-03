package com.example.project.form.benefits.routes;

import com.example.project.base.routes.BaseRoutes;

public class BenefitRoutes {
    private final static String ROOT = BaseRoutes.NOT_SECURED + "/benefit";
    public static final String BY_ID = ROOT + "/{id}";
    public final static  String SEARCH = ROOT;

}
