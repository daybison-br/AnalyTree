package com.daybison.analytree_plus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daybison.analytree_plus.databinding.SeedlingItemBinding;
import com.daybison.analytree_plus.entities.Seedling;

import java.util.ArrayList;

public class SeedlingAdapter extends RecyclerView.Adapter<SeedlingAdapter.SeedlingViewHolder> {

    private final ArrayList<Seedling> seedlingList;
    private final Context context;
    private final OnItemClickListener onItemClickListener;

    public SeedlingAdapter(ArrayList<Seedling> seedlingList, Context context, OnItemClickListener onItemClickListener) {
        this.seedlingList = seedlingList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SeedlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SeedlingItemBinding listItem;
        listItem = SeedlingItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new SeedlingViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SeedlingViewHolder holder, int position) {
        Seedling seedling = seedlingList.get(position);

        holder.binding.SeedlingNameView.setText(seedlingList.get(position).getPopularName().toUpperCase());
        holder.binding.individualNumberNum.setText(seedlingList.get(position).getIndividualNumber());
        holder.binding.statusNameValue.setText(seedlingList.get(position).getStatusSeedling());


        // Adiciona o listener de clique
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(seedling);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seedlingList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Seedling seedling);
    }

    public static class SeedlingViewHolder extends RecyclerView.ViewHolder{

        SeedlingItemBinding binding;

        public SeedlingViewHolder(SeedlingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
