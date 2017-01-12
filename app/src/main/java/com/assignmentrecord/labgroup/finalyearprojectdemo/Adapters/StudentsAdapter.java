package com.assignmentrecord.labgroup.finalyearprojectdemo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignmentrecord.labgroup.finalyearprojectdemo.Activity.AssignmentActivity;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Activity.SwipeListener;
import com.assignmentrecord.labgroup.finalyearprojectdemo.R;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.StudentTable;

import io.realm.Realm;
import io.realm.RealmResults;


public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.Custom_Holder> implements SwipeListener {

    Context context;
    RealmResults<StudentTable> studentsList;
    long LabID;
    long GroupId;
    private Realm mRealm;
    public StudentsAdapter(Context context, RealmResults<StudentTable> studentsList, Realm realm, long labsId, long groupsId) {
        this.context = context;
        this.mRealm=realm;
        this.LabID=labsId;
        this.GroupId = groupsId;
        update(studentsList);
    }

    public void update(RealmResults<StudentTable> results) {
        studentsList = results;
        notifyDataSetChanged();
    }

    @Override
    public Custom_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Custom_Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row, parent, false));
    }

    @Override
    public void onBindViewHolder(Custom_Holder holder, int position) {
        holder.studentName.setText(studentsList.get(position).getStudentName());
        holder.studentId.setText(Long.toString(studentsList.get(position).getStudentID()));
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    @Override
    public void onSwip(int position) {
        mRealm.beginTransaction();
        studentsList.get(position).removeFromRealm();
        mRealm.commitTransaction();
        notifyItemRemoved(position);
    }

    public class Custom_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView studentName;
        public TextView studentId;

        public Custom_Holder(View itemView) {
            super(itemView);
            studentName = (TextView) itemView.findViewById(R.id.studentName);
            studentId = (TextView) itemView.findViewById(R.id.studentId);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent i= new Intent(context, AssignmentActivity.class);
            i.putExtra("LabId", Long.toString(LabID));
            i.putExtra("GroupId", Long.toString(GroupId));
            i.putExtra("StudentId", studentId.getText().toString());


            context.startActivity(i);
        }
    }
}
