package com.ps.hoodiesstore.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.ps.hoodiesstore.Application;
import com.ps.hoodiesstore.R;
import com.ps.hoodiesstore.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
       private ActivityMainBinding binding;
       private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addListener();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View v) {
       if (v.getId() == R.id.ivnext){
           if(firebaseAuth.getCurrentUser() == null){
           startActivity(new Intent(this, LoginActivity.class));
               finish();
           }
           else {
               startActivity(new Intent(this, ProductActivity.class));
               finish();
           }

       }
    }

    private void addListener(){
        binding.ivnext.setOnClickListener(this);

        firebaseAuth = ((Application) getApplicationContext()).getFirebaseAuth();
    }
}