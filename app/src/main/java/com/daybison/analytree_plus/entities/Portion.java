package com.daybison.analytree_plus.entities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daybison.analytree_plus.configs.DatabaseHelper;
import com.daybison.analytree_plus.controller.SeedlingController;
import com.daybison.analytree_plus.utils.FormatDataUtils;
import com.daybison.analytree_plus.view.MainActivity;

import java.util.UUID;

@Entity(tableName = "portions")
public class Portion {

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String formFactor;
    private Boolean deleted;
    private String created_in;
    private int qtySeedling;


    public Portion() {
        this.id = UUID.randomUUID().toString();

    }

    public Portion(String name, String formFactor, Boolean deleted) {
        this.id = UUID.randomUUID().toString();
        this.formFactor = formFactor;
        this.deleted = deleted;
        this.created_in = FormatDataUtils.getFormattedCreatedIn();
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_in() {
        return created_in;
    }

    public void setCreated_in(String created_in) {
        this.created_in = created_in;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public int getQtySeedling() {
        return qtySeedling;
    }

    public void setQtySeedling(int qtySeedling) {
        this.qtySeedling = qtySeedling;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public Integer qtySeedlingsCount(Context context, String portion_id) {
        SeedlingController seedlingController = new SeedlingController(context);

        return seedlingController.findAllSeedlingsByPortionId(portion_id).size();
    }

    @Override
    public String toString() {
        return "Portion{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", formFactor='" + formFactor + '\'' +
                ", deleted=" + deleted +
                ", created_in='" + created_in + '\'' +
                ", qtySeedling=" + qtySeedling +
                '}';
    }
}
