package com.assignmentrecord.labgroup.finalyearprojectdemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.assignmentrecord.labgroup.finalyearprojectdemo.Adapters.AssignmentAdapter;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Dialogs.DialogAddAssignment;
import com.assignmentrecord.labgroup.finalyearprojectdemo.R;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.AssignmentTable;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.StudentTable;
import com.software.shell.fab.ActionButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

public class AssignmentActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_assignment)
    RecyclerView rv;
    @Bind(R.id.floating_action_button)
    ActionButton fab;
    DialogAddAssignment dialog;
    String passedLabID;
    String passedGroupID;
    String passedStudentID;
    StudentTable studentResult;
    RealmList<AssignmentTable> results;
    AssignmentAdapter mAdapter;
    Realm mRealm;
    private RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            mAdapter.update(results);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        ButterKnife.bind(this);
        toolbar.setTitle("Assignment Marks");
        setSupportActionBar(toolbar);

        passedLabID = getIntent().getExtras().getString("LabId");
        passedGroupID = getIntent().getExtras().getString("GroupId");
        passedStudentID = getIntent().getExtras().getString("StudentId");

        long labsId = Long.parseLong(passedLabID);
        long groupsId = Long.parseLong(passedGroupID);
        long studentId = Long.parseLong(passedStudentID);


        mRealm = Realm.getDefaultInstance();
        studentResult = mRealm.where(StudentTable.class)
                .equalTo("labID", labsId)
                .equalTo("groupID", groupsId)
                .equalTo("studentID", studentId)
                .findFirstAsync();

        studentResult.load();
        results = studentResult.getMarks();

        mAdapter = new AssignmentAdapter(this, results, mRealm);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        SimpleTouchCallback cb = new SimpleTouchCallback(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(cb);
        helper.attachToRecyclerView(rv);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floating_action_button:
                showDialogAdd();
                break;
        }
    }

    private void showDialogAdd() {
        dialog = new DialogAddAssignment();
        Bundle args = new Bundle();
        args.putString("labId", passedLabID);
        args.putString("groupId", passedGroupID);
        args.putString("studentId", passedStudentID);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "AddAssignment");
    }
}
