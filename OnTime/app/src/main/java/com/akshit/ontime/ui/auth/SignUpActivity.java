package com.akshit.ontime.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.akshit.ontime.R;
import com.akshit.ontime.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ActivitySignUpBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Send verification email to the user.
     */
    private void sendVerificationEmail() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "User registered. Please verify the email.", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    finish();
                } else {
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    Toast.makeText(SignUpActivity.this, "Unable to register the user. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(SignUpActivity.this, "Unable to register the user. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to sign up the user.
     *
     * @param view of the sign up button.
     */
    public void signUp(View view) {
        final String email = mBinding.signUpId.getText().toString().trim();
        final String password = mBinding.signUpPassword.getText().toString().trim();
        final String confirmPassword = mBinding.confirmSignUpPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!confirmPassword.equals(password)) {
            Toast.makeText(getApplicationContext(), "Passwords don't Match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.endsWith("cgc.edu.in")) {
            Toast.makeText(this, "Please use your college official email Id.", Toast.LENGTH_SHORT).show();
            return;
        }

        mBinding.signUpProgress.setVisibility(View.VISIBLE);
        //create user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (!task.isSuccessful()) {
                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(SignUpActivity.this, "E-mail already registered.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    final Exception exception = task.getException();
                    if (exception == null) {
                        //TODO crash analytics
                        Toast.makeText(this, "Unable to register the user. Please try again.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(SignUpActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                }
                mBinding.signUpProgress.setVisibility(View.GONE);
                return;
            }
            mBinding.signUpProgress.setVisibility(View.GONE);
            sendVerificationEmail();
        });
    }
}
