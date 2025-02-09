package com.ps.hoodiesstore.RecyclerAdapterAndHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ps.hoodiesstore.databinding.RowLayoutProdectBinding;

public class HomeHolder extends RecyclerView.ViewHolder {

    RowLayoutProdectBinding binding;

    public HomeHolder(@NonNull RowLayoutProdectBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
