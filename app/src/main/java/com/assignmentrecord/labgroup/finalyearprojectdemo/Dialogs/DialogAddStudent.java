package com.assignmentrecord.labgroup.finalyearprojectdemo.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.assignmentrecord.labgroup.finalyearprojectdemo.R;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.StudentTable;

import io.realm.Realm;
import io.realm.RealmResults;

public class DialogAddStudent extends DialogFragment implements View.OnClickListener {
    private EditText studentName;
    private TextInputLayout studentLayoutName;
    private TextInputLayout studentLayoutEmail;
    private EditText studentEmail;
    private Button Exit;
    private Button Add;
    private String labIDExtracted;
    private String groupIDExtracted;

    public DialogAddStudent() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_student, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        labIDExtracted = "000000000000";
        groupIDExtracted = "000000000000";
        if (bundle != null) {
            labIDExtracted = bundle.getString("labId");
            groupIDExtracted = bundle.getString("groupId");
        }

        studentName = (EditText) view.findViewById(R.id.student_name);
        studentEmail = (EditText) view.findViewById(R.id.email);
        Exit = (Button) view.findViewById(R.id.exit_student_register);
        Add = (Button) view.findViewById(R.id.add_student);
        studentLayoutName = (TextInputLayout) view.findViewById(R.id.input_listStudentName_container);
        studentLayoutEmail = (TextInputLayout) view.findViewById(R.id.input_listEmail_container);
        Exit.setOnClickListener(this);
        Add.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_student_register:
                dismiss();
                break;
            case R.id.add_student:
                addGroups();
                break;
        }
    }

    private void addGroups() {
        String studentNamedStr = studentName.getText().toString().trim();
        String studentEmailStr = studentEmail.getText().toString().trim();
        if (studentNamedStr.isEmpty() || studentEmailStr.isEmpty()) {
            studentLayoutName.setError("Can Not Be Empty");
            studentLayoutEmail.setError("Can Not Be Empty");
            Toast.makeText(getContext(), "Please Enter Student Name", Toast.LENGTH_SHORT).show();
        } else {

            long now = System.currentTimeMillis();

            long labIdNo = Long.parseLong(labIDExtracted);
            long groupIdNo = Long.parseLong(groupIDExtracted);

            Realm realm = Realm.getDefaultInstance();
            RealmResults<StudentTable> studentExist = realm.where(StudentTable.class).equalTo("emailAddress", studentEmailStr).findAllAsync();
            studentExist.load();
            if (studentExist.size() == 0) {
                StudentTable studentData = new StudentTable(studentNamedStr, now, labIdNo, groupIdNo, studentEmailStr, now);
                realm.beginTransaction();
                realm.copyToRealm(studentData);
                realm.commitTransaction();
            } else if (studentExist.size() >= 1) {
                RealmResults<StudentTable> alreadyEntered = realm.where(StudentTable.class)
                        .equalTo("emailAddress", studentEmailStr)
                        .equalTo("labID", labIdNo)
                        .equalTo("groupID", groupIdNo).findAllAsync();
                alreadyEntered.load();
                if (alreadyEntered.size() == 1) {
                    Toast.makeText(getContext(), "Already Entered This Student", Toast.LENGTH_LONG).show();
                } else {
                    StudentTable stdID = realm.where(StudentTable.class)
                            .equalTo("emailAddress", studentEmailStr)
                            .findFirstAsync();
                    stdID.load();

                    StudentTable studentData = new StudentTable(studentNamedStr, stdID.getStudentID(), labIdNo, groupIdNo, studentEmailStr, now);
                    realm.beginTransaction();
                    realm.copyToRealm(studentData);
                    realm.commitTransaction();
                }
            }
            realm.close();
            dismiss();
        }
    }
}
