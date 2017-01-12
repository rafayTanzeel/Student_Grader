package com.assignmentrecord.labgroup.finalyearprojectdemo.Activity;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.assignmentrecord.labgroup.finalyearprojectdemo.Adapters.LabsAdapter;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Dialogs.DialogAddLabs;
import com.assignmentrecord.labgroup.finalyearprojectdemo.R;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.LabTable;
import com.software.shell.fab.ActionButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class LabsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.collapseToolbar) CollapsingToolbarLayout collapseToolbar;
    @Bind(R.id.rv_labs) RecyclerView rv;
    @Bind(R.id.floating_action_button)
    ActionButton fab;

    DialogAddLabs dialog;
    Realm mRealm;
    RealmResults<LabTable> result;
    LabsAdapter mAdapter;

    private RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            mAdapter.update(result);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labs);
        ButterKnife.bind(this);
        fab.setOnClickListener(this);
        collapseToolbar.setTitle(getString(R.string.lab));
        setSupportActionBar(toolbar);
        mRealm = Realm.getDefaultInstance();
        result= mRealm.where(LabTable.class).findAllAsync();


        mAdapter = new LabsAdapter(this, result, mRealm);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        SimpleTouchCallback cb = new SimpleTouchCallback(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(cb);
        helper.attachToRecyclerView(rv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floating_action_button:
                //Snackbar.make(v.getRootView(), "Item Successfully Deleted", Snackbar.LENGTH_SHORT).show();
                showDialogAdd();
                break;
        }
    }

    private void showDialogAdd() {
        dialog = new DialogAddLabs();
        dialog.show(getSupportFragmentManager(), "Add");
        // Log.e("Answers", dialog.Answers);
    }
}
