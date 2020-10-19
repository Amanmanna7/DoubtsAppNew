package com.example.doubtsapp;


import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

import java.net.InterfaceAddress;

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

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position=getAdapterPosition();
                        mClickListener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                });



            }catch (Exception e){

            }
        }
    }


    private ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(DataSnapshot snapshot,int position);

    }

    public void setOnClickListener(ClickListener clickListener){
        mClickListener = clickListener;
    }


}
