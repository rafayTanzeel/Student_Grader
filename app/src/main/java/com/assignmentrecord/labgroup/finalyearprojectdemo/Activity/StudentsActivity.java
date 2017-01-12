package com.assignmentrecord.labgroup.finalyearprojectdemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.assignmentrecord.labgroup.finalyearprojectdemo.Adapters.StudentsAdapter;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Dialogs.DialogAddStudent;
import com.assignmentrecord.labgroup.finalyearprojectdemo.R;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.GroupTable;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.StudentTable;
import com.software.shell.fab.ActionButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class StudentsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_students)
    RecyclerView rv;
    Realm mRealm;
    String passedLabId;
    String passedGroupId;
    RealmResults<StudentTable> results;
    RealmList<GroupTable> groupResults;
    @Bind(R.id.floating_action_button)
    ActionButton fab;
    DialogAddStudent dialog;
    StudentsAdapter mAdapter;

    private RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            mAdapter.update(results);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        ButterKnife.bind(this);
        toolbar.setTitle(getString(R.string.StudentsToolbar));
        setSupportActionBar(toolbar);

        passedLabId = getIntent().getExtras().getString("LabId");
        passedGroupId = getIntent().getExtras().getString("GroupId");
        long labsId = Long.parseLong(passedLabId);
        long groupsId = Long.parseLong(passedGroupId);

        mRealm = Realm.getDefaultInstance();
        results = mRealm.where(StudentTable.class)
                .equalTo("labID", labsId)
                .equalTo("groupID", groupsId).findAllAsync();

        mAdapter = new StudentsAdapter(this, results, mRealm, labsId, groupsId);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        SimpleTouchCallback cb = new SimpleTouchCallback(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(cb);
        helper.attachToRecyclerView(rv);

        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_action_button:
                showDialogAdd();
                break;
        }
    }

    private void showDialogAdd() {
        dialog = new DialogAddStudent();
        Bundle args = new Bundle();
        args.putString("labId", passedLabId);
        args.putString("groupId", passedGroupId);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "Add_Student");

    }
}
