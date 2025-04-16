package com.example.redalert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText emailLogin, passwordLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnGoToSignup = findViewById(R.id.btnGoToSignup);

        btnLogin.setOnClickListener(v -> {
            String email = emailLogin.getText().toString();
            String password = passwordLogin.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "⚠️ Enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            Toast.makeText(this, "✅ Logged in!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(this, "❌ " + e.getMessage(), Toast.LENGTH_LONG).show()
                        );
            }
        });

        btnGoToSignup.setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
            finish();
        });
    }
}
