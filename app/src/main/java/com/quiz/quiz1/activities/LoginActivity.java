package com.quiz.quiz1.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quiz.quiz1.R;
import com.quiz.quiz1.utils.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private Button btnSignUp, btnLogin;
    private EditText editEmail, editPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initObjects();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(signUp);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                if (!isValidateEmail(email)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.email_warning), Toast.LENGTH_SHORT).show();
                } else if (isEmptyField(password)) {
                    editPassword.setError(getString(R.string.pw_required));
                } else {
                    if (dbHelper.checkUser(email, password)) {
                        Intent accountsIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(accountsIntent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),getString(R.string.fail_login),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initViews() {
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);

        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);

    }
    private void initObjects(){
        dbHelper = new DatabaseHelper(LoginActivity.this);
    }

    private boolean isValidateEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isEmptyField(String field) {
        return TextUtils.isEmpty(field);
    }
}
