package com.assignmentrecord.labgroup.finalyearprojectdemo.Tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class GroupTable extends RealmObject {
    private String labName;
    private String descriptionText;

    @PrimaryKey
    private long timeAdded;

    public GroupTable() {
    }

    public GroupTable(String labName, String descriptionText, long timeAdded) {
        this.labName = labName;
        this.descriptionText = descriptionText;
        this.timeAdded = timeAdded;
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

    public long getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(long timeAdded) {
        this.timeAdded = timeAdded;
    }
}
