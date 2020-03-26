package com.example.rent_a_cycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.signup_Name_edt)
    TextInputEditText name_edt;
    @BindView(R.id.signup_webmail_edt)
    TextInputEditText mail_edt;
    @BindView(R.id.signup_mobnum_edt)
    TextInputEditText mobnum_edt;
    @BindView(R.id.signup_password_edt)
    TextInputEditText pass_edt;
    @BindView(R.id.signup_weight_edt)
    TextInputEditText weight_edt;
    @BindView(R.id.signup_height_edt)
    TextInputEditText height_edt;

    @BindView(R.id.signup_button)
    Button signup_butt;

    @BindView(R.id.goto_login_textview)
    TextView goToLogin_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.goto_login_textview)
    void gotologin(){
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }
}
