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
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mNumberOfTurn;
    private List<String> mAttackDamage;

    public RecyclerViewAdapter(List<String> numberOfTurn, List<String> attackDamage) {
        this.mNumberOfTurn = numberOfTurn;
        this.mAttackDamage = attackDamage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).numberOfTurn.setText(mNumberOfTurn.get(position));
        ((ViewHolder)holder).attackDamages.setText(mAttackDamage.get(position));
    }

    @Override
    public int getItemCount() {
        return mNumberOfTurn.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView numberOfTurn;
        TextView attackDamages;

        public ViewHolder (View itemView){
            super(itemView);
            numberOfTurn = itemView.findViewById(R.id.number_of_turn);
            attackDamages = itemView.findViewById(R.id.attack_history);
        }
    }
}
