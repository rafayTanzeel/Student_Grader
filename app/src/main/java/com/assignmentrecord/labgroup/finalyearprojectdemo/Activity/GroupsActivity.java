package com.assignmentrecord.labgroup.finalyearprojectdemo.Activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.assignmentrecord.labgroup.finalyearprojectdemo.Adapters.GroupsAdapter;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Dialogs.DialogAddGroups;
import com.assignmentrecord.labgroup.finalyearprojectdemo.R;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.GroupTable;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.LabTable;
import com.software.shell.fab.ActionButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

public class GroupsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapseToolbarGroups)
    CollapsingToolbarLayout collapseToolbar;
    @Bind(R.id.floating_action_button)
    ActionButton fab;
    @Bind(R.id.rv_groups)
    RecyclerView rv;
    String passedLabId;
    Realm mRealm;
    LabTable results;
    RealmList<GroupTable> groupResults;
    DialogAddGroups dialog;
    GroupsAdapter mAdapter;
    private RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            mAdapter.update(groupResults);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        ButterKnife.bind(this);

        passedLabId = getIntent().getExtras().getString("LabId");
        long labsId = Long.parseLong(passedLabId);
        collapseToolbar.setTitle(getString(R.string.group));
        setSupportActionBar(toolbar);

        mRealm = Realm.getDefaultInstance();
        results = mRealm.where(LabTable.class).equalTo("timeAdded", labsId).findFirstAsync();
        results.load();
        groupResults = results.getGroups();

        mAdapter = new GroupsAdapter(this, groupResults, mRealm, passedLabId);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(this);

        SimpleTouchCallback cb = new SimpleTouchCallback(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(cb);
        helper.attachToRecyclerView(rv);
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
        dialog = new DialogAddGroups();
        Bundle args = new Bundle();
        args.putString("labId", passedLabId);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "AddGroups");
        // Log.e("Answers", dialog.Answers);
    }
}
