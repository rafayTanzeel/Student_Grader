package com.assignmentrecord.labgroup.finalyearprojectdemo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignmentrecord.labgroup.finalyearprojectdemo.Activity.StudentsActivity;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Activity.SwipeListener;
import com.assignmentrecord.labgroup.finalyearprojectdemo.R;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.GroupTable;

import io.realm.Realm;
import io.realm.RealmList;


public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.Custom_Holder> implements SwipeListener {
    RealmList<GroupTable> groupsList;
    Context context;
    private Realm mRealm;
    private String LabId;
    public GroupsAdapter(Context context, RealmList<GroupTable> groupsList, Realm realm, String passedLabId) {
        this.context=context;
        this.mRealm = realm;
        this.LabId=passedLabId;
        update(groupsList);
    }

    public void update(RealmList<GroupTable> results){
        groupsList = results;
        notifyDataSetChanged();
    }

    @Override
    public Custom_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Custom_Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(Custom_Holder holder, int position) {
        holder.name.setText(groupsList.get(position).getLabName());
        holder.description.setText(groupsList.get(position).getDescriptionText());
        holder.time.setText(Long.toString(groupsList.get(position).getTimeAdded()));
    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    @Override
    public void onSwip(int position) {
        mRealm.beginTransaction();
        groupsList.get(position).removeFromRealm();
        mRealm.commitTransaction();
        notifyItemRemoved(position);
    }

    public class Custom_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView description;
        public TextView time;
        public Custom_Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameRow);
            description = (TextView) itemView.findViewById(R.id.descriptionRow);
            time = (TextView) itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i =new Intent(context, StudentsActivity.class);
            i.putExtra("LabId", LabId);
            i.putExtra("GroupId", time.getText().toString());
            context.startActivity(i);
        }
    }
}
