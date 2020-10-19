package com.example.doubtsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class ansAdapter extends FirebaseRecyclerAdapter<modelAns,ansAdapter.ansviewHolder> {

    public ansAdapter(@NonNull FirebaseRecyclerOptions<modelAns> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ansviewHolder holder, int position, @NonNull modelAns model) {
        holder.ansUsername.setText(model.getUser());
        holder.ansTextView.setText(model.getAnswer());
    }

    @NonNull
    @Override
    public ansviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowans,parent,false);
        return new ansviewHolder(view);
    }

    class ansviewHolder extends RecyclerView.ViewHolder{

        TextView ansTextView,ansUsername;

        public ansviewHolder(@NonNull View itemView) {
            super(itemView);
            ansTextView=(TextView)itemView.findViewById(R.id.viewTextAnswer);
            ansUsername=(TextView)itemView.findViewById(R.id.nameTagAns);


        }
    }
}
