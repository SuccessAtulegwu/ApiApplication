package com.example.apiapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.OurViewHolder> {

    Context context;
    List<model> models;
    public Adapter(Context context, List<model> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public OurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new OurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OurViewHolder holder, int position) {

           holder.doorId.setText(models.get(position).getDoorID());
           holder.lockId.setText(models.get(position).get_LockID());
           holder.doorName.setText(models.get(position).get_LockNumber());
           holder.lockType.setText(models.get(position).get_LockType());


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class OurViewHolder extends RecyclerView.ViewHolder {
     TextView doorId, lockId, doorName, lockType;
        public OurViewHolder(@NonNull View itemView) {
            super(itemView);
            doorId = itemView.findViewById(R.id.doorId);
            lockId = itemView.findViewById(R.id.lockId);
            doorName = itemView.findViewById(R.id.doorName);
            lockType = itemView.findViewById(R.id.lockType);
        }
    }


}
