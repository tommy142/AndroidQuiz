package com.quiz.quiz1.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.quiz.quiz1.R;
import com.quiz.quiz1.data.User;
import com.quiz.quiz1.utils.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private EditText editUsername, editEmail, editPassword, editRtPassword, editBirthday;
    private RadioButton selectedRadioButton;
    private CheckBox cbTerm;
    private Button btnSignUp;
    private RadioGroup mGender;
    private DatabaseHelper dbHelper;
    private User user;

    private Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initObjects();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editBirthday.setText(sdf.format(mCalendar.getTime()));
            }
        };

        editBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterActivity.this, dateSetListener, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void initViews() {
        editUsername = findViewById(R.id.username);
        editEmail = findViewById(R.id.emailReg);
        editPassword = findViewById(R.id.password);
        editRtPassword = findViewById(R.id.rt_password);
        editBirthday = findViewById(R.id.birthday);

        mGender = findViewById(R.id.rgGender);

        cbTerm = findViewById(R.id.cb_term);

        btnSignUp = findViewById(R.id.btn_signUp);
    }

    private void initObjects(){
        dbHelper = new DatabaseHelper(RegisterActivity.this);
        user = new User();
    }
    private void register() {
        String username = editUsername.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String rtPassword = editRtPassword.getText().toString();
        String birthday = editBirthday.getText().toString();
        int selectedId = mGender.getCheckedRadioButtonId();
        selectedRadioButton = findViewById(selectedId);
        //Validate
        if (!isValidateEmail(email)) {
            Toast.makeText(getApplicationContext(), getString(R.string.email_warning), Toast.LENGTH_SHORT).show();
        } else if (isEmptyField(username)) {
            editUsername.setError(getString(R.string.username_required));
        } else if (isEmptyField(password)) {
            editPassword.setError(getString(R.string.pw_required));
        } else if (isEmptyField(rtPassword)) {
            editRtPassword.setError(getString(R.string.pw_required));
        } else if (!isMatch(password, rtPassword)) {
            editRtPassword.setError(getString(R.string.dont_match));
        } else if (isEmptyField(birthday)) {
            editBirthday.setError(getString(R.string.bd_required));
        } else if (!cbTerm.isChecked()) {
            Toast.makeText(getApplicationContext(), getString(R.string.terms), Toast.LENGTH_SHORT).show();
        } else {
            if (!dbHelper.checkUser(email)) {
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                user.setGender(selectedRadioButton.getText().toString());
                user.setBirthday(birthday);
                dbHelper.addUser(user);
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.error_email), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isValidateEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isEmptyField(String field) {
        return TextUtils.isEmpty(field);
    }

    private boolean isMatch(String password, String rtPassword) {
        return password.equals(rtPassword);
    }
}
