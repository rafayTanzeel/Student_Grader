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
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.LabTable;

import io.realm.Realm;

public class DialogAddLabs extends DialogFragment implements View.OnClickListener {

    private EditText labName;
    private TextInputLayout labLayoutName;
    private EditText description;
    private Button Exit;
    private Button Add;


    public DialogAddLabs() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_row, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        labName = (EditText) view.findViewById(R.id.name);
        description = (EditText) view.findViewById(R.id.description);
        Exit = (Button) view.findViewById(R.id.exit);
        Add = (Button) view.findViewById(R.id.add);
        labLayoutName = (TextInputLayout) view.findViewById(R.id.input_listName_container);
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
                addLabs();
                break;
        }
    }

    private void addLabs() {
        String labNamed = labName.getText().toString().trim();
        if(labNamed.isEmpty()){
            labLayoutName.setError("Can Not Be Empty");
            Toast.makeText(getContext(), "Please Enter Lab Name", Toast.LENGTH_SHORT).show();
        }else {
            String descriptionSentence = description.getText().toString().trim();
            long now = System.currentTimeMillis();
            Realm realm = Realm.getDefaultInstance();

            LabTable labData = new LabTable(now, labNamed, descriptionSentence);
            realm.beginTransaction();
            realm.copyToRealm(labData);
            realm.commitTransaction();
            realm.close();
            dismiss();
        }
    }
}
