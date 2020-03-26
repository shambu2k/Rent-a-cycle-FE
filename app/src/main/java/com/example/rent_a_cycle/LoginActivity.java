package com.example.rent_a_cycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class LoginActivity extends AppCompatActivity {

    private PermissionsChecker checker;

    @BindView(R.id.login_webmail_edt)
    TextInputEditText mail_edt;
    @BindView(R.id.login_password_edt)
    TextInputEditText pass_edt;

    @BindView(R.id.login_button)
    Button login_butt;

    @BindView(R.id.forgot_pass_textview)
    TextView forgotpass_tv;
    @BindView(R.id.goto_signup_textview)
    TextView goTosignup_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        checker = new PermissionsChecker(LoginActivity.this);
        checker.getPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        checker.setAllGranted(true);
        switch (requestCode) {
            case 1234: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            checker.setAllGranted(false);
                            return;
                        }
                    }
                }
            }
        }
    }

    @OnClick(R.id.login_button)
    void getIn() {
        if (checker.isAllGranted()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "You need to give permissions", Toast.LENGTH_SHORT).show();
            checker.getPermissions();
        }
    }

    @OnClick(R.id.goto_signup_textview)
    void gotoSignUp() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        finish();
    }

}
