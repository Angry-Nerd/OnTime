package com.akshit.ontime.ui.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.akshit.ontime.R;
import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.DbConstants;
import com.akshit.ontime.constants.UserPropertyConstants;
import com.akshit.ontime.databinding.ActivitySignUpBinding;
import com.akshit.ontime.models.User;
import com.akshit.ontime.util.FirebaseUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = AppConstants.APP_PREFIX + SignUpActivity.class.getSimpleName();
    private ActivitySignUpBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
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
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!confirmPassword.equals(password)) {
            Toast.makeText(getApplicationContext(), "Passwords don't Match", Toast.LENGTH_LONG).show();
            return;
        }

        if (!email.endsWith("@cgc.edu.in")) {
            Toast.makeText(this, "Please use your college official email Id.", Toast.LENGTH_LONG).show();
            return;
        }

        mBinding.signUpProgress.setVisibility(View.VISIBLE);
        //create user
        FirebaseUtil.getAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (!task.isSuccessful()) {
                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(SignUpActivity.this, "E-mail already registered.", Toast.LENGTH_LONG).show();
                } else {
                    final Exception exception = task.getException();
                    if (exception == null) {
                        //TODO crash analytics
                        Toast.makeText(this, "Unable to register the user. Please try again.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                mBinding.signUpProgress.setVisibility(View.GONE);
                return;
            }
            sendVerificationEmail(email);
        }).addOnFailureListener(e -> {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            mBinding.signUpProgress.setVisibility(View.GONE);
        });
    }

    /**
     * Send verification email to the user.
     */
    private void sendVerificationEmail(final String email) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    registerUserInDb(email);
                } else {
                    mBinding.signUpProgress.setVisibility(View.GONE);
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    Toast.makeText(SignUpActivity.this, "Unable to register the user. Please try again later.", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            mBinding.signUpProgress.setVisibility(View.GONE);
            Toast.makeText(SignUpActivity.this, "Unable to register the user. Please try again later.", Toast.LENGTH_LONG).show();
        }
    }

    private void registerUserInDb(final String email) {
        String[] id = email.split("@");
        User user = new User();
        Map<String, Object> properties = new HashMap<>();
        properties.put(UserPropertyConstants.SIGNED_UP_TIME, System.currentTimeMillis());
        user.setOtherProperties(properties);
        user.setEmail(email);
        CollectionReference reference = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY)
                .document(id[1]).collection(DbConstants.USERS);
        OnSuccessListener<DocumentReference> onSuccessListener = o -> {
            Toast.makeText(this, "User registered. Please verify the email.", Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            finish();
        };

        OnFailureListener onFailureListener = e -> {
            mBinding.signUpProgress.setVisibility(View.GONE);
            FirebaseAuth.getInstance().getCurrentUser().delete();
            Toast.makeText(SignUpActivity.this, "Unable to register the user. Please try again later.", Toast.LENGTH_LONG).show();
        };

        FirebaseUtil.addToCollection(reference, onSuccessListener, user, onFailureListener);
    }


}
