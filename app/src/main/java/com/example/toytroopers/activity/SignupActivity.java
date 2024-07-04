package com.example.toytroopers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toytroopers.databinding.ActivitySignupBinding;
import com.example.toytroopers.model.User;
import com.example.toytroopers.utils.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding signupBinding;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signupBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = signupBinding.getRoot();
        setContentView(view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference(Constants.USER_TABLE);

        signupBinding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    private void signUpUser() {
        String email = signupBinding.edtUserName.getText().toString().trim();
        String contact = signupBinding.edtContact.getText().toString().trim();
        String password = signupBinding.edtPassword.getText().toString().trim();

        User newUser = new User(contact, email , password);
        String userId = usersRef.push().getKey();
        usersRef.child(userId).setValue(newUser);

        Toast.makeText(this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }
}