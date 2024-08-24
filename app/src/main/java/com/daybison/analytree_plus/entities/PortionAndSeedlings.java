package com.daybison.analytree_plus.entities;

public class PortionAndSeedlings {
    private String namePortion;
    private String statusSeedling;
    private String groupSeedling;
    private String individualNumber;
    private String popularName;
    private String popularScientific;
    private String heightSeedling;
    private String capSeedling;
    private String observationSeedling;
    private String createdInSeedling;

    public PortionAndSeedlings() {
    }

    public String getNamePortion() {
        return namePortion;
    }

    public void setNamePortion(String namePortion) {
        this.namePortion = namePortion;
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

    public String getPopularScientific() {
        return popularScientific;
    }

    public void setPopularScientific(String popularScientific) {
        this.popularScientific = popularScientific;
    }

    public String getHeightSeedling() {
        return heightSeedling;
    }

    public void setHeightSeedling(String heightSeedling) {
        this.heightSeedling = heightSeedling;
    }

    public String getCapSeedling() {
        return capSeedling;
    }

    public void setCapSeedling(String capSeedling) {
        this.capSeedling = capSeedling;
    }

    public String getObservationSeedling() {
        return observationSeedling;
    }

    public void setObservationSeedling(String observationSeedling) {
        this.observationSeedling = observationSeedling;
    }

    public String getCreatedInSeedling() {
        return createdInSeedling;
    }

    public void setCreatedInSeedling(String createdInSeedling) {
        this.createdInSeedling = createdInSeedling;
    }

    @Override
    public String toString() {
        return "PortionAndSeedlings{" +
                "namePortion='" + namePortion + '\'' +
                ", statusSeedling='" + statusSeedling + '\'' +
                ", groupSeedling='" + groupSeedling + '\'' +
                ", individualNumber='" + individualNumber + '\'' +
                ", popularName='" + popularName + '\'' +
                ", popularScientific='" + popularScientific + '\'' +
                ", heightSeedling='" + heightSeedling + '\'' +
                ", capSeedling='" + capSeedling + '\'' +
                ", observationSeedling='" + observationSeedling + '\'' +
                ", createdInSeedling='" + createdInSeedling + '\'' +
                '}';
    }
}