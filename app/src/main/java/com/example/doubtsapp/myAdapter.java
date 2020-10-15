package com.example.doubtsapp;


import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myAdapter extends FirebaseRecyclerAdapter<model,myAdapter.myViewHolder> {


    public myAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull model model) {

        try{
            holder.nameTag.setText(model.getUser());
            holder.textDescription.setText(model.getDescription());
            Glide.with(holder.retrievedView.getContext()).load(model.getImage()).into(holder.retrievedView);
        }catch (Exception e){

        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try{
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
            return new myViewHolder(view);
        }catch (Exception e){

        }
        return new myViewHolder(parent);

    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView retrievedView;
        TextView nameTag,textDescription;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            try{
                retrievedView=(ImageView)itemView.findViewById(R.id.retrievedView);
                nameTag=(TextView)itemView.findViewById(R.id.nameTag);
                textDescription=(TextView)itemView.findViewById(R.id.textDescription);

            }catch (Exception e){

            }
        }
    }
}
