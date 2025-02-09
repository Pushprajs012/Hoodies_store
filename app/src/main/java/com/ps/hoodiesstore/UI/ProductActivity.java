package com.ps.hoodiesstore.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps.hoodiesstore.Application;
import com.ps.hoodiesstore.Data.HoodieData;
import com.ps.hoodiesstore.R;
import com.ps.hoodiesstore.RecyclerAdapterAndHolder.HomeRecycleViewAdapter;
import com.ps.hoodiesstore.databinding.ActivityProductBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityProductBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;
    private HomeRecycleViewAdapter adapter;
    private List<HoodieData> hoodieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchDataFromFirebase();
       // binding.toolbar.setTitle(auth.getCurrentUser().getDisplayName());
        setname();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hoodieList=new ArrayList<>();
        adapter=new HomeRecycleViewAdapter(hoodieList,firebaseDatabase,auth.getCurrentUser().getUid());
        binding.recyclerView.setAdapter(adapter);
    }


    private void init(){
        firebaseDatabase= ((Application) getApplicationContext()).getFirebaseDatabase();
        auth=((Application) getApplicationContext()).getFirebaseAuth();
        binding.menucart.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.menucart){
            startActivity(new Intent(ProductActivity.this, CartActivity.class));
        }

    }

    private void fetchDataFromFirebase(){
        firebaseDatabase.getReference("hoodies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hoodieList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HoodieData hoodieData = dataSnapshot.getValue(HoodieData.class);
                    hoodieList.add(hoodieData);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here.
      if (item.getItemId()==R.id.cart){
        startActivity(new Intent(ProductActivity.this, CartActivity.class));
      }
      return super.onOptionsItemSelected(item);
    }


    private void setname(){
        firebaseDatabase.getReference("Users").child(auth.getCurrentUser().getUid()).child("User").child("fullName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                binding.name.setText(snapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}