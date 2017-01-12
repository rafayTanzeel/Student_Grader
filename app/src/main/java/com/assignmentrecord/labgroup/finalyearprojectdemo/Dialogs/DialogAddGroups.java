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
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.GroupTable;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.LabTable;

import io.realm.Realm;


public class DialogAddGroups extends DialogFragment implements View.OnClickListener{

    private EditText groupName;
    private TextInputLayout groupLayoutName;
    private EditText description;
    private Button Exit;
    private Button Add;
    private String labIDExtracted;

    public DialogAddGroups() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_row, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        labIDExtracted = "000000000000";
        if (bundle != null) {
            labIDExtracted = bundle.getString("labId");
        }

        groupName = (EditText) view.findViewById(R.id.name);
        description = (EditText) view.findViewById(R.id.description);
        Exit = (Button) view.findViewById(R.id.exit);
        Add = (Button) view.findViewById(R.id.add);
        groupLayoutName= (TextInputLayout) view.findViewById(R.id.input_listName_container);
        Exit.setOnClickListener(this);
        Add.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit:
                dismiss();
                break;
            case R.id.add:
                addGroups();
                break;
        }
    }

    private void addGroups() {
        String groupNamed = groupName.getText().toString().trim();
        if(groupNamed.isEmpty()){
            groupLayoutName.setError("Can Not Be Empty");
            Toast.makeText(getContext(), "Please Enter Group Name", Toast.LENGTH_SHORT).show();
        }
        else {
            String descriptionSentence = description.getText().toString().trim();
            long now = System.currentTimeMillis();
            Realm realm = Realm.getDefaultInstance();
            long labIdNo = Long.parseLong(labIDExtracted);
            GroupTable groupData = new GroupTable(groupNamed, descriptionSentence, now);
            LabTable labSelected = realm.where(LabTable.class).equalTo("timeAdded", labIdNo).findFirstAsync();
            labSelected.load();
            realm.beginTransaction();
            labSelected.getGroups().add(groupData);
            realm.copyToRealmOrUpdate(labSelected);
            realm.commitTransaction();
            realm.close();
            dismiss();
        }
    }
}
