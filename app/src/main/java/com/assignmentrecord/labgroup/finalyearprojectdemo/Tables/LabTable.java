package com.assignmentrecord.labgroup.finalyearprojectdemo.Tables;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class LabTable extends RealmObject {
    private  String labName;
    private String descriptionText;
    private RealmList<GroupTable> groups;

    @PrimaryKey
    private long timeAdded;

    public LabTable() {
    }

    public LabTable(long timeAdded, String labName, String descriptionText) {
        this.timeAdded = timeAdded;
        this.descriptionText = descriptionText;
        this.labName = labName;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public RealmList<GroupTable> getGroups() {
        return groups;
    }

    public void setGroups(RealmList<GroupTable> groups) {
        this.groups = groups;
    }

    public long getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(long timeAdded) {
        this.timeAdded = timeAdded;
    }
}
