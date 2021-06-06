package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserFav extends AppCompatActivity {

    String userId ;
    ArrayList<String> favSongs = new ArrayList<>() ;

    FirebaseFirestore fStore ;

    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_fav);

        getSupportActionBar().setTitle("User's Favourites");

        Intent intent = getIntent() ;
        userId = intent.getStringExtra("id") ;

        recyclerView = findViewById(R.id.recyclerViewUfav) ;

        fStore = FirebaseFirestore.getInstance() ;
        DocumentReference documentReference = fStore.collection("fav").document(userId) ;
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult() ;
                    if (doc.exists()) {
                        Map<String, Object> map = doc.getData();

                        if (map != null) {
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                favSongs.add(entry.getValue().toString());
                            }
                            favSongs.remove("Demo") ;
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            userFavAd ad = new userFavAd(favSongs) ;
                            recyclerView.setAdapter(ad);
                        }
                    }
                }
            }
        }) ;

    }
}