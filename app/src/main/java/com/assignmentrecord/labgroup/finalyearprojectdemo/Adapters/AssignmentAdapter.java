package com.assignmentrecord.labgroup.finalyearprojectdemo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignmentrecord.labgroup.finalyearprojectdemo.Activity.SwipeListener;
import com.assignmentrecord.labgroup.finalyearprojectdemo.R;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.AssignmentTable;

import io.realm.Realm;
import io.realm.RealmList;


public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.Custom_Holder>  implements SwipeListener {
    Context context;
    RealmList<AssignmentTable> assignmentList;
Realm mRealm;
    public AssignmentAdapter(Context context, RealmList<AssignmentTable> assignmentList, Realm realm) {
        this.context = context;
        this.mRealm = realm;
        update(assignmentList);
    }

    public void update(RealmList<AssignmentTable> results){
        assignmentList = results;
        notifyDataSetChanged();
    }

    @Override
    public Custom_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Custom_Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_row, parent, false));
    }

    @Override
    public void onBindViewHolder(Custom_Holder holder, int position) {
        holder.assignmentName.setText(assignmentList.get(position).getAssignmentName());
        holder.assignmentDescription.setText(assignmentList.get(position).getAssignmentDescription());
        holder.assignmentMarks.setText(assignmentList.get(position).getMarks());
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    @Override
    public void onSwip(int position) {
        mRealm.beginTransaction();
        assignmentList.get(position).removeFromRealm();
        mRealm.commitTransaction();
        notifyItemRemoved(position);
    }

    public class Custom_Holder extends RecyclerView.ViewHolder {
        private TextView assignmentName;
        private TextView assignmentDescription;
        private TextView assignmentMarks;
        public Custom_Holder(View itemView) {
            super(itemView);
            assignmentName = (TextView) itemView.findViewById(R.id.assignmentName);
            assignmentDescription = (TextView) itemView.findViewById(R.id.assignmentDescriptionRow);
            assignmentMarks = (TextView) itemView.findViewById(R.id.assignmentMarks);
        }

    }
}
