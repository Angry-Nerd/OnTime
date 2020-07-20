package com.akshit.ontime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.akshit.ontime.R;
import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.DbConstants;
import com.akshit.ontime.databinding.ActivityFirstSignInBinding;
import com.akshit.ontime.managers.UserManager;
import com.akshit.ontime.models.User;
import com.akshit.ontime.ui.auth.LoginActivity;
import com.akshit.ontime.util.AppUtils;
import com.akshit.ontime.util.FirebaseUtil;
import com.akshit.ontime.util.SharedPreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class FirstSignInActivity extends AppCompatActivity {

    private static final String TAG = AppConstants.APP_PREFIX + "SignInActivity";
    private ActivityFirstSignInBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_first_sign_in);
        mBinding.emailId.setText(SharedPreferenceManager.getLoggedEmail());
        final User user = UserManager.getInstance().getUser();
        Log.d(TAG, "onCreate: " + user);
        if (user != null) {
            mBinding.submitApplication.setEnabled(false);
            mBinding.firstName.setEnabled(false);
            mBinding.lastName.setEnabled(false);
            mBinding.college.setEnabled(false);
            mBinding.stream.setEnabled(false);
            String []displayName = user.getDisplayName().split(" ");
            mBinding.firstName.setText(displayName[0]);
            mBinding.lastName.setText(displayName[1]);
            mBinding.college.setText(user.getCollege());
            mBinding.stream.setText(user.getStream());

            Toast.makeText(this, "Your application is still under review.", Toast.LENGTH_SHORT).show();
        }
    }

    public void signOut(View view) {
        FirebaseUtil.signOut(this);
    }

    public void submitApplication(View view) {
        User user = new User();
        final String firstName = mBinding.firstName.getText().toString();
        final String lastName = mBinding.lastName.getText().toString();
        final String emailId = mBinding.emailId.getText().toString();
        final String college = mBinding.college.getText().toString();
        final String stream = mBinding.stream.getText().toString();


        final String check = AppUtils.checkEmpty(firstName, lastName, emailId, college, stream);
        if (check != null) {
            Toast.makeText(this, check, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!firstName.matches("^[A-Za-z]+$")) {
            Toast.makeText(this, "Not a valid first name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!lastName.matches("^[A-Za-z]+$")) {
            Toast.makeText(this, "Not a valid last name.", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (!emailId.matches("$[A-Za-z]+^")) {
//            Toast.makeText(this, "Not a valid name.", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (!college.matches("^[A-Za-z]+$")) {
            Toast.makeText(this, "Not a college name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!stream.matches("^[A-Z]+$")) {
            Toast.makeText(this, "Not a stream name.", Toast.LENGTH_SHORT).show();
            return;
        }

        user.setEmail(emailId);
        user.setDisplayName(firstName + " " + lastName);
        user.setCollege(college);
        user.setStream(stream);
        user.setUserName(emailId.split("@")[0]);
        user.setApplicationStatus(1);
        final String university = SharedPreferenceManager.getUniversityName();

        final DocumentReference documentReference = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY)
                .document(university)
                .collection(DbConstants.USERS)
                .document(emailId);
        OnSuccessListener onSuccessListener = o -> {
            Toast.makeText(this, "Your application is submitted and will be reviewed shortly. You'll be notified by email.", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }, 2000);
        };

        OnFailureListener onFailureListener = e -> {
            //TODO crashlytics
            Toast.makeText(this, "An error occurred. " + e.getMessage(), Toast.LENGTH_SHORT).show();
        };

        FirebaseUtil.setDocument(documentReference, onSuccessListener, onFailureListener, user);
        //TODO send notification to authority
    }
}