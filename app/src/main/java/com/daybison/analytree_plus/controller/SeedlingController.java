package com.daybison.analytree_plus.controller;

import android.content.ContentValues;
import android.content.Context;

import com.daybison.analytree_plus.configs.DatabaseHelper;
import com.daybison.analytree_plus.entities.Portion;
import com.daybison.analytree_plus.entities.Seedling;

import java.util.ArrayList;

public class SeedlingController extends DatabaseHelper {

    public SeedlingController(Context context) {
        super(context);
    }

    public void save(Seedling seedling) {

        ContentValues values = new ContentValues();
        values.put("id", seedling.getId()); // Inserir o ID
        values.put("individualNumber", seedling.getIndividualNumber()); // Inserir o número individual
        values.put("popularName", seedling.getPopularName()); // Inserir o nome popular
        values.put("popularScientific", seedling.getPopularscientific()); // Inserir o nome científico popular
        values.put("height", seedling.getHeight()); // Inserir a altura
        values.put("cap", seedling.getCap()); // Inserir o CAP
        values.put("statusSeedling", seedling.getStatusSeedling()); // Inserir o status booleano
        values.put("groupSeedling", seedling.getGroupSeedling()); // Inserir o grupo da muda
        values.put("observation", seedling.getObservation()); // Inserir a observação
        values.put("portions_id", seedling.getPortions_id()); // Inserir o ID da porção relacionada
        values.put("created_in", seedling.getCreated_in()); // Inserir a data de criação

        this.saveData("seedlings", values);
    }

    public ArrayList<Seedling> getAllSeedlings() {
        return this.findAllSeedlings();
    }

    public ArrayList<Seedling> getAllSeedlingsByPortionId(String portions_id) {
        return this.findAllSeedlingsByPortionId(portions_id);
    }

    public void update(Seedling seedling) {
        this.updateSeedling(seedling);
    }

    public void delete(Seedling seedling) {
        this.deleteSeedling(seedling);
    }


}
