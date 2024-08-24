package com.daybison.analytree_plus.entities;

import com.daybison.analytree_plus.utils.FormatDataUtils;

import java.text.DateFormat;
import java.util.UUID;

public class Seedling {
    private String id;
    private String individualNumber;
    private String popularName;
    private String popularscientific;
    private String height;
    private String cap;
    private String statusSeedling;
    private String groupSeedling;
    private String observation;
    private String portions_id;
    private String created_in;

    public Seedling() {
    }

    public Seedling(String individualNumber, String popularName, String popularscientific, String height, String cap, String statusSeedling, String groupSeedling, String observation, String portions_id) {
        this.id = UUID.randomUUID().toString();
        this.individualNumber = individualNumber;
        this.popularName = popularName;
        this.popularscientific = popularscientific;
        this.height = height;
        this.cap = cap;
        this.statusSeedling = statusSeedling;
        this.groupSeedling = groupSeedling;
        this.observation = observation;
        this.portions_id = portions_id;
        this.created_in = FormatDataUtils.getFormattedCreatedIn();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPortions_id() {
        return portions_id;
    }

    public void setPortions_id(String portions_id) {
        this.portions_id = portions_id;
    }

    public String getIndividualNumber() {
        return individualNumber;
    }

    public void setIndividualNumber(String individualNumber) {
        this.individualNumber = individualNumber;
    }

    public String getPopularName() {
        return popularName;
    }

    public void setPopularName(String popularName) {
        this.popularName = popularName;
    }

    public String getPopularscientific() {
        return popularscientific;
    }

    public void setPopularscientific(String popularscientific) {
        this.popularscientific = popularscientific;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getStatusSeedling() {
        return statusSeedling;
    }

    public void setStatusSeedling(String statusSeedling) {
        this.statusSeedling = statusSeedling;
    }

    public String getGroupSeedling() {
        return groupSeedling;
    }

    public void setGroupSeedling(String groupSeedling) {
        this.groupSeedling = groupSeedling;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getCreated_in() {
        return created_in;
    }

    public void setCreated_in(String created_in) {
        this.created_in = created_in;
    }

    @Override
    public String toString() {
        return "Seedling{" +
                "id='" + id + '\'' +
                ", individualNumber='" + individualNumber + '\'' +
                ", popularName='" + popularName + '\'' +
                ", popularscientific='" + popularscientific + '\'' +
                ", height='" + height + '\'' +
                ", cap='" + cap + '\'' +
                ", statusSeedling='" + statusSeedling + '\'' +
                ", groupSeedling='" + groupSeedling + '\'' +
                ", observation='" + observation + '\'' +
                ", portions_id='" + portions_id + '\'' +
                ", created_in='" + created_in + '\'' +
                '}';
    }
}
