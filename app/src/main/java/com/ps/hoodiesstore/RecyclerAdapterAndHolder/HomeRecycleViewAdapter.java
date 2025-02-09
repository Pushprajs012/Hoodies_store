package com.ps.hoodiesstore.RecyclerAdapterAndHolder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps.hoodiesstore.Data.CartData;
import com.ps.hoodiesstore.Data.HoodieData;
import com.ps.hoodiesstore.UI.ProductDetailAcivity;
import com.ps.hoodiesstore.databinding.RowLayoutProdectBinding;


import java.util.List;

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<HomeHolder> {

    private List<HoodieData> hoodieList;
    private FirebaseDatabase firebaseDatabase;
    private String uid;

    // Corrected Constructor Name
    public HomeRecycleViewAdapter(List<HoodieData> hoodieList, FirebaseDatabase firebaseDatabase, String uid) {
        this.hoodieList = hoodieList;
        this.firebaseDatabase = firebaseDatabase;
        this.uid = uid;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutProdectBinding binding = RowLayoutProdectBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new HomeHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
        HoodieData hoodie = hoodieList.get(position);

        // Binding data to views
        holder.binding.productName.setText(hoodie.getName());
        holder.binding.productPrice.setText("$"+hoodie.getPrice());
        holder.binding.productSize.setText("Size: "+hoodie.getSize());
        Glide.with(holder.binding.productImage.getContext())
                .load(hoodie.getImage())
                .into(holder.binding.productImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ProductDetailAcivity.class);
            intent.putExtra("hoodie", hoodie.getProduct_id());
            holder.itemView.getContext().startActivity(intent);
        });

        holder.binding.btncart.setOnClickListener(v -> {
            //check to data is already in cart
            firebaseDatabase.getReference("Users")
                    .child(uid)
                    .child("Cart")
                    .child(hoodie.getProduct_id())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(holder.itemView.getContext(), "Product already in cart", Toast.LENGTH_SHORT).show();

                    } else {
                        CartData cartData=new CartData(hoodie.getProduct_id(),hoodie.getName(),hoodie.getPrice(),hoodie.getSize(),hoodie.getImage(),hoodie.getAbout(),1);
                        firebaseDatabase.getReference("Users").child(uid).child("Cart").child(hoodie.getProduct_id()).setValue(cartData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(holder.itemView.getContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
                            }
                        });
                         }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        });
    }

    @Override
    public int getItemCount() {

        return hoodieList.size();
    }
}
