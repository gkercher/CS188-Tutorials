package com.beautifulbulldog.grace.beautifulbulldog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BulldogAdapter extends RecyclerView.Adapter<BulldogAdapter.BulldogViewHolder> {
    private Context context;
    private ArrayList<Bulldog>bulldogs;
    private RecyclerViewClickListener mListener;

    public BulldogAdapter(Context context, ArrayList<Bulldog>dataSet, RecyclerViewClickListener
                          clickListener) {
        this.context = context;
        this.bulldogs = dataSet;
        this.mListener = clickListener;
    }

    public static class BulldogViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView nameView;
        public TextView ageView;
        private RecyclerViewClickListener mListener;
        public BulldogViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            nameView = v.findViewById(R.id.name_view);
            ageView = v.findViewById(R.id.age_view);
            mListener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return bulldogs.size();
    }

    @Override public BulldogAdapter.BulldogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bulldog_cell, parent, false);
        BulldogViewHolder vh = new BulldogViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(BulldogViewHolder holder, int position) {
        holder.nameView.setText(bulldogs.get(position).getName());
        holder.ageView.setText(bulldogs.get(position).getAge());
    }

}
