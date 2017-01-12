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
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.AssignmentTable;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.StudentTable;

import io.realm.Realm;

public class DialogAddAssignment extends DialogFragment implements View.OnClickListener {

    private EditText assignmentName;
    private TextInputLayout assignmentLayoutName;
    private TextInputLayout assignmentAchieveLayoutMarks;
    private TextInputLayout assignmentTotalLayoutMarks;
    private EditText description;
    private EditText achievedMarks;
    private EditText totalMarks;
    private Button Exit;
    private Button Add;
    private String labIDExtracted;
    private String groupIDExtracted;
    private String studentIDExtracted;

    public DialogAddAssignment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_assignment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        labIDExtracted = "000000000000";
        groupIDExtracted = "000000000000";
        studentIDExtracted = "000000000000";
        if (bundle != null) {
            labIDExtracted = bundle.getString("labId");
            groupIDExtracted = bundle.getString("groupId");
            studentIDExtracted = bundle.getString("studentId");
        }



        assignmentName = (EditText) view.findViewById(R.id.assignment_name);
        description = (EditText) view.findViewById(R.id.comments);
        achievedMarks = (EditText) view.findViewById(R.id.achieved);
        totalMarks = (EditText) view.findViewById(R.id.total);
        Exit = (Button) view.findViewById(R.id.exit_assignment_register);
        assignmentLayoutName = (TextInputLayout) view.findViewById(R.id.input_listAssignmentName_container);
        assignmentAchieveLayoutMarks = (TextInputLayout) view.findViewById(R.id.input_listAchieveMarks_container);
        assignmentTotalLayoutMarks = (TextInputLayout) view.findViewById(R.id.input_listTotalMarks_container);
        Add = (Button) view.findViewById(R.id.add_assignment);
        Exit.setOnClickListener(this);
        Add.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_assignment_register:
                dismiss();
                break;
            case R.id.add_assignment:
                addAssignment();
                break;
        }
    }

    private void addAssignment() {
        String assignmentNamed = assignmentName.getText().toString().trim();
        String achMarks = achievedMarks.getText().toString().trim();
        String sumMarks = totalMarks.getText().toString().trim();
        if (assignmentNamed.isEmpty() || achMarks.isEmpty() || sumMarks.isEmpty()) {
            assignmentLayoutName.setError("Can Not Be Empty");
            assignmentAchieveLayoutMarks.setError("Can Not Be Empty");
            assignmentTotalLayoutMarks.setError("Can Not Be Empty");
            Toast.makeText(getContext(), "Please Enter Required Assignment Information", Toast.LENGTH_SHORT).show();
        } else if(Long.parseLong(achMarks)>Long.parseLong(sumMarks)){
            assignmentAchieveLayoutMarks.setError("Can Not Be Greater Than Total Marks");
            assignmentTotalLayoutMarks.setError("Can Not Be Lesser Than Achieved Marks");
        }
        else {
            String descriptionSentence = description.getText().toString().trim();
            long now = System.currentTimeMillis();
            Realm realm = Realm.getDefaultInstance();
            long labIdNo = Long.parseLong(labIDExtracted);
            long groupIdNo = Long.parseLong(groupIDExtracted);
            long studentIdNo = Long.parseLong(studentIDExtracted);
            AssignmentTable assignmentData = new AssignmentTable(assignmentNamed, descriptionSentence, achMarks +"/"+ sumMarks, now);
            StudentTable studentSelected = realm.where(StudentTable.class)
                    .equalTo("labID", labIdNo)
                    .equalTo("groupID", groupIdNo)
                    .equalTo("studentID", studentIdNo)
                    .findFirstAsync();
            studentSelected.load();
            realm.beginTransaction();
            studentSelected.getMarks().add(assignmentData);
            realm.copyToRealmOrUpdate(studentSelected);
            realm.commitTransaction();
            realm.close();
            dismiss();
        }
    }
}
