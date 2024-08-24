package com.daybison.analytree_plus.controller;

import android.content.ContentValues;
import android.content.Context;

import com.daybison.analytree_plus.configs.DatabaseHelper;
import com.daybison.analytree_plus.entities.Portion;

import java.util.ArrayList;

public class PortionController extends DatabaseHelper {
    private String INSERT_PORTION_QUERY;

    public PortionController(Context context) {
        super(context);
    }

    public void save(Portion portion){

        ContentValues values = new ContentValues();
        values.put("id", portion.getId()); // Inserir o ID
        values.put("name", portion.getName()); // Inserir o nome
        values.put("deleted", portion.getDeleted() ? 1 : 0); // Inserir o status booleano
        values.put("created_in", portion.getCreated_in()); // Inserir a data formatada
        values.put("qtySeedling", portion.getQtySeedling());

        this.saveData("portions", values);
    }

    public ArrayList<Portion> getAllPortions(){
        return this.findAllPortion();
    }

    public Portion getPortionsById(String id){
        return this.findPortionById(id);
    }

    public void delete(String id_portion){
        super.deletePortionAndSeedlings(id_portion);
    }
}
