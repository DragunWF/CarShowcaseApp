package com.example.carshowcaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carshowcaseapp.helpers.DatabaseHelper;
import com.example.carshowcaseapp.helpers.Utils;
import com.example.carshowcaseapp.services.SessionService;
import com.google.android.material.textfield.TextInputEditText;

public class LoginView extends AppCompatActivity {
    private View view;
    private TextInputEditText usernameText, passwordText;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            DatabaseHelper.initialize(this);

            bindElements();
            setButtons();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        view = findViewById(R.id.main);
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        loginBtn = findViewById(R.id.loginBtn);
    }

    private void setButtons() {
        loginBtn.setOnClickListener(v -> {
            String username = Utils.getText(usernameText);
            String password = Utils.getText(passwordText);

            if (username.isEmpty() || password.isEmpty()) {
                Utils.snackbar("All fields are required!", view);
                return;
            }

            boolean isLoginSuccessful = SessionService.login(username, password);
            if (!isLoginSuccessful) {
                Utils.snackbar("Incorrect credentials!", view);
                return;
            }

            startActivity(new Intent(LoginView.this, MainActivity.class));
            finish();
        });
    }
}