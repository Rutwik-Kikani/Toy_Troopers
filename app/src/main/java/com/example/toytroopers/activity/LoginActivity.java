package com.example.toytroopers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.toytroopers.databinding.ActivityLoginBinding;
import com.example.toytroopers.utils.Constants;
import com.example.toytroopers.utils.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;
    private DatabaseReference usersRef;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = loginBinding.getRoot();
        setContentView(view);

        prefManager = new PrefManager(this);

        if (prefManager.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        usersRef = FirebaseDatabase.getInstance().getReference().child(Constants.USER_TABLE);

        loginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        loginBinding.txtSignup.setOnClickListener(v -> {
            Intent intent = new Intent(this,SignupActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = loginBinding.edtUserName.getText().toString().trim();
        String password = loginBinding.edtPassword.getText().toString().trim();

        if (email.isEmpty()) {
            loginBinding.edtUserName.setError("Email is required");
            loginBinding.edtUserName.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            loginBinding.edtPassword.setError("Password is required");
            loginBinding.edtPassword.requestFocus();
            return;
        }

        usersRef.orderByChild(Constants.USER_TABLE_EMAIL).equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String dbPassword = userSnapshot.child(Constants.USER_TABLE_PASSWORD).getValue(String.class);

                        if (dbPassword != null && dbPassword.equals(password)) {
                            String userId = userSnapshot.getKey();
                            prefManager.setUserId(userId);
                            prefManager.setEmail(email);
                            prefManager.setLoggedIn(true);

                            Toast.makeText(LoginActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                            return;
                        }
                    }
                    Toast.makeText(LoginActivity.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}