package com.ps.hoodiesstore.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ps.hoodiesstore.R;
import com.ps.hoodiesstore.databinding.ActivityThankuBinding;

public class ThankuActivity extends AppCompatActivity {
    private ActivityThankuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThankuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbtn.setOnClickListener(v -> {
            startActivity(new Intent(this, ProductActivity.class));
            finish();
        });
    }
}