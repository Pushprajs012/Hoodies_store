package com.ps.hoodiesstore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Application extends android.app.Application {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }



    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
    }
}
