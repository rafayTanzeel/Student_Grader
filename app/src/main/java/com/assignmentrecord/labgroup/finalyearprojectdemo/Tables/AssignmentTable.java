package com.assignmentrecord.labgroup.finalyearprojectdemo.Tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class AssignmentTable extends RealmObject {
    private String assignmentName;
    private String assignmentDescription;
    private String marks;

    @PrimaryKey
    private long assignmentMarkCode;

    public AssignmentTable() {
    }

    public AssignmentTable(String assignmentName, String assignmentDescription, String marks, long assignmentMarkCode) {
        this.assignmentName = assignmentName;
        this.assignmentDescription = assignmentDescription;
        this.marks = marks;
        this.assignmentMarkCode= assignmentMarkCode;
    }

    public long getAssignmentMarkCode() {
        return assignmentMarkCode;
    }

    public void setAssignmentMarkCode(long assignmentMarkCode) {
        this.assignmentMarkCode = assignmentMarkCode;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
