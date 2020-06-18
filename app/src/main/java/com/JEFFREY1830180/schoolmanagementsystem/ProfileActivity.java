package com.JEFFREY1830180.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {


    private TextView textViewName,textViewShowEmail,textViewShowAccount,textViewChangePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textViewName = findViewById(R.id.textViewName);
        textViewShowEmail = findViewById(R.id.textViewShowEmail);
        textViewShowAccount = findViewById(R.id.textViewShowAccount);
        textViewChangePass = findViewById(R.id.textViewChangePass);

        textViewChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,ChangePasswordActivity.class);
                startActivity(intent);



            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String RegisteredUserID = currentUser.getUid();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue().toString();
                String email = dataSnapshot.child("Email").getValue().toString();
                String account = dataSnapshot.child("TypeOfAccount").getValue().toString();
                textViewName.setText(name);
                textViewShowEmail.setText("Email: "+email);
                textViewShowAccount.setText("Account Type: "+account);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_nav, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.logout:
                        Intent intentToLogout = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(intentToLogout);
                        intentToLogout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

}