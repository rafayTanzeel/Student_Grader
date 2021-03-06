package com.assignmentrecord.labgroup.finalyearprojectdemo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignmentrecord.labgroup.finalyearprojectdemo.Activity.GroupsActivity;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Activity.SwipeListener;
import com.assignmentrecord.labgroup.finalyearprojectdemo.R;
import com.assignmentrecord.labgroup.finalyearprojectdemo.Tables.LabTable;

import io.realm.Realm;
import io.realm.RealmResults;


public class LabsAdapter extends RecyclerView.Adapter<LabsAdapter.Custom_Holder> implements SwipeListener {

    private RealmResults<LabTable> labsList;
    private Realm mRealm;
    Context context;

    public LabsAdapter(Context context, RealmResults<LabTable> labsList, Realm realm) {
        this.context = context;
        this.mRealm = realm;
        update(labsList);
    }

    public void update(RealmResults<LabTable> results) {
        labsList = results;
        notifyDataSetChanged();
    }

    @Override
    public Custom_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Custom_Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(Custom_Holder holder, int position) {
        holder.name.setText(labsList.get(position).getLabName());
        holder.description.setText(labsList.get(position).getDescriptionText());
        holder.time.setText(Long.toString(labsList.get(position).getTimeAdded()));
    }

    @Override
    public int getItemCount() {
        return labsList.size();
    }

    @Override
    public void onSwip(int position) {
        mRealm.beginTransaction();
        labsList.get(position).removeFromRealm();
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
            Intent i = new Intent(context, GroupsActivity.class);

            i.putExtra("LabId", time.getText().toString());
            context.startActivity(i);
        }


    }
}
