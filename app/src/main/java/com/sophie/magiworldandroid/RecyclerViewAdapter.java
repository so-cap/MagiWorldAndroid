package com.sophie.magiworldandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOPHIE on 22/12/2019.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {
    private List<String> mNumberOfTurn;
    private List<String> mAttackDamage;

    public RecyclerViewAdapter(List<String> numberOfTurn, List<String> attackDamage) {
        this.mNumberOfTurn = numberOfTurn;
        this.mAttackDamage = attackDamage;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_recyclerview, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.CustomViewHolder holder, int position) {
       holder.bind(mNumberOfTurn.get(position),mAttackDamage.get(position));
    }

    @Override
    public int getItemCount() {
        return mNumberOfTurn.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView numberOfTurn;
        TextView attackDamages;

        public CustomViewHolder (View itemView){
            super(itemView);
            numberOfTurn = itemView.findViewById(R.id.number_of_turn);
            attackDamages = itemView.findViewById(R.id.attack_history);
        }
        public void bind(String numberOfTurn, String attackDamages){
            this.numberOfTurn.setText(numberOfTurn);
            this.attackDamages.setText(attackDamages);
        }
    }
}
