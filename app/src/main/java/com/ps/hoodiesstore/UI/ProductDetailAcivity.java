package com.ps.hoodiesstore.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps.hoodiesstore.Application;
import com.ps.hoodiesstore.Data.CartData;
import com.ps.hoodiesstore.R;
import com.ps.hoodiesstore.databinding.ActivityProductDetailAcivityBinding;

public class ProductDetailAcivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityProductDetailAcivityBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;
    private CartData cartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailAcivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        getandsetDatatoView();
        checkdataincart();

        binding.m.setSelected(true);
        for (int i = 0; i < binding.linearlayout2.getChildCount(); i++) {
            View child = binding.linearlayout2.getChildAt(i);
            child.setOnClickListener(v -> {

                for (int j = 0; j < binding.linearlayout2.getChildCount(); j++) {
                    binding.linearlayout2.getChildAt(j).setSelected(false);
                }

                v.setSelected(true);
            });
        }
    }

    private void getandsetDatatoView() {

        firebaseDatabase.getReference("hoodies").child(getIntent().getStringExtra("hoodie")).get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                cartData=new CartData(task.getResult().child("product_id").getValue(String.class),
                        task.getResult().child("name").getValue(String.class),
                        task.getResult().child("price").getValue(Integer.class),
                        task.getResult().child("size").getValue(String.class),
                        task.getResult().child("image").getValue(String.class),
                       task.getResult().child("about").getValue(String.class),
                       1);

                binding.productTitle.setText(task.getResult().child("name").getValue(String.class));
                binding.productPrice.setText("$"+task.getResult().child("price").getValue(Integer.class).toString());
                binding.abouttext.setText(task.getResult().child("about").getValue(String.class));
                binding.productSize.setText("Size: ");

                Glide.with(binding.productImage.getContext()).load(task.getResult().child("image").getValue(String.class)).into(binding.productImage);

            }
        });
    }


    private void init() {
        firebaseDatabase = ((Application) getApplicationContext()).getFirebaseDatabase();
        auth = ((Application) getApplicationContext()).getFirebaseAuth();
        binding.addToCartButton.setOnClickListener(this);
        binding.backButton.setOnClickListener(v -> finish());

    }

    @Override
    public void onClick(View v) {
if (v.getId()== R.id.addToCartButton){

    firebaseDatabase.getReference("Users").child(auth.getCurrentUser().getUid()).child("Cart").child(getIntent().getStringExtra("hoodie")).setValue(cartData).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
           startActivity(new Intent(ProductDetailAcivity.this, CartActivity.class));
        }
    });


}
    }

    private void checkdataincart(){
        firebaseDatabase.getReference("Users")
                .child(auth.getCurrentUser().getUid())
                .child("Cart")
                .child(getIntent().getStringExtra("hoodie"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            binding.addToCartButton.setText("Already In Cart");
                            binding.addToCartButton.setEnabled(false);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}