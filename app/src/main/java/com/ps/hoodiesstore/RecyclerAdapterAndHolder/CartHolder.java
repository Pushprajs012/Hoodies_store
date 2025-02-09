package com.ps.hoodiesstore.RecyclerAdapterAndHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ps.hoodiesstore.databinding.RowLayoutCartBinding;
import com.ps.hoodiesstore.databinding.RowLayoutProdectBinding;

public class CartHolder extends RecyclerView.ViewHolder {

    RowLayoutCartBinding binding;

    public CartHolder(@NonNull RowLayoutCartBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
