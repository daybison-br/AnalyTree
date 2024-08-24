package com.daybison.analytree_plus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daybison.analytree_plus.databinding.PortionItemBinding;
import com.daybison.analytree_plus.entities.Portion;

import java.util.ArrayList;

public class PortionAdapter extends RecyclerView.Adapter<PortionAdapter.PortionViewHolder> {

    private final ArrayList<Portion> portionList;
    private final Context context;
    private final OnItemClickListener onItemClickListener;

    public PortionAdapter(ArrayList<Portion> portionList, Context context, OnItemClickListener onItemClickListener) {
        this.portionList = portionList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PortionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PortionItemBinding listItem;
        listItem = PortionItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new PortionViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull PortionViewHolder holder, int position) {
        Portion portion = portionList.get(position);

       // holder.binding.iconItemList.setBackgroundResource(portionList.get(position).getImgPortion); Caso queira que o icone seja dinamico
        holder.binding.portionNameView.setText(portionList.get(position).getName().toUpperCase());
        holder.binding.qtyNumberItens.setText(String.valueOf(portionList.get(position).qtySeedlingsCount(this.context, portionList.get(position).getId())+ " Mudas"));


        // Adiciona o listener de clique
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(portion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return portionList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Portion portion);
    }

    public static class PortionViewHolder extends RecyclerView.ViewHolder{

        PortionItemBinding binding;

        public PortionViewHolder(PortionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
