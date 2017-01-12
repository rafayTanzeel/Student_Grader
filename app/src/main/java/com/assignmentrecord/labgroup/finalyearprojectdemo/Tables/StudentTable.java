package com.assignmentrecord.labgroup.finalyearprojectdemo.Tables;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class StudentTable extends RealmObject {
    private String studentName;

    private long labID;
    private long groupID;
    private RealmList<AssignmentTable> marks;
    private String emailAddress;
    private long studentID;

    @PrimaryKey
    private long studentSessionID;

    public StudentTable() {
    }

    public StudentTable(String studentName, long studentID, long labID, long groupID, String emailAddress, long studentSessionID) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.labID = labID;
        this.groupID = groupID;
        this.emailAddress = emailAddress;
        this.studentSessionID=studentSessionID;
    }

    public long getStudentSessionID() {
        return studentSessionID;
    }

    public void setStudentSessionID(long studentSessionID) {
        this.studentSessionID = studentSessionID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public long getLabID() {
        return labID;
    }

    public void setLabID(long labID) {
        this.labID = labID;
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }

    public RealmList<AssignmentTable> getMarks() {
        return marks;
    }

    public void setMarks(RealmList<AssignmentTable> marks) {
        this.marks = marks;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
