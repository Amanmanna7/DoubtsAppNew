package com.example.doubtsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class imgAnsAdapter extends FirebaseRecyclerAdapter<modelImgAns,imgAnsAdapter.ansImgHolder> {

    public imgAnsAdapter(@NonNull FirebaseRecyclerOptions<modelImgAns> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ansImgHolder holder, int position, @NonNull modelImgAns model) {
        holder.nameTagAns.setText(model.getUser());
        holder.imageDescription.setText(model.getDescription());
        Glide.with(holder.retrievedViewAns.getContext()).load(model.getImage()).into(holder.retrievedViewAns);


    }

    @NonNull
    @Override
    public ansImgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowimgans,parent,false);
        return new ansImgHolder(view);

    }

    class ansImgHolder extends RecyclerView.ViewHolder{

        TextView nameTagAns,imageDescription;
        ImageView retrievedViewAns;

        public ansImgHolder(@NonNull View itemView) {
            super(itemView);
            nameTagAns=(TextView)itemView.findViewById(R.id.nameTagAns);
            imageDescription=(TextView)itemView.findViewById(R.id.imageDescription);
            retrievedViewAns=(ImageView)itemView.findViewById(R.id.retrievedViewAns);


        }
    }

}
