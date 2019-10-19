package com.example.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
{
    ArrayList<Note> data;
    FileSelected activity;

    interface FileSelected
    {
        void OnFileSelected(Note o);
    }

    public ListAdapter(Context context, ArrayList<Note> data) {
        this.data = data;
        activity  = (FileSelected) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(data.get(position).getFilename());
        holder.tvDate.setText(data.get(position).getDate());
        holder.itemView.setTag(data.get(position));

    }

    @Override
    public int getItemCount() {
        return  data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  tvName , tvDate;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvDate  =  itemView.findViewById(R.id.tvDate);
            tvName = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.OnFileSelected(data.get(data.indexOf((Note)itemView.getTag())));
                }
            });


        }
    }
}