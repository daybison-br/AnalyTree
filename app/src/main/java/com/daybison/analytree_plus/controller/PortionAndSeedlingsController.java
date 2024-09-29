package com.daybison.analytree_plus.controller;

import android.content.Context;

import com.daybison.analytree_plus.configs.DatabaseHelper;
import com.daybison.analytree_plus.entities.PortionAndSeedlings;

import java.util.ArrayList;

public class PortionAndSeedlingsController extends DatabaseHelper {
    public PortionAndSeedlingsController(Context context) {
        super(context);
    }


    public ArrayList<PortionAndSeedlings> getAllPortionAndSeedlings() {
        return super.findAllPortionAndSeedlings();
    }




}
