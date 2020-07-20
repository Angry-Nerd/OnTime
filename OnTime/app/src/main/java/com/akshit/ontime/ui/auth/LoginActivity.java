package com.akshit.ontime.ui.auth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.akshit.ontime.MainActivity;
import com.akshit.ontime.R;
import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.DbConstants;
import com.akshit.ontime.databinding.ActivityLoginBinding;
import com.akshit.ontime.managers.UserManager;
import com.akshit.ontime.models.User;
import com.akshit.ontime.ui.FirstSignInActivity;
import com.akshit.ontime.ui.HomeActivity;
import com.akshit.ontime.util.AppUtils;
import com.akshit.ontime.util.FirebaseUtil;
import com.akshit.ontime.util.SharedPreferenceManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = LoginActivity.class.getSimpleName();
    public ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AppUtils.setTopBar(this);
        }
    }

    private void login(final View v, final String id, final String password, final String domain) {
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(password)) {
            Snackbar snackbar = Snackbar.make(v, "Both fields are mandatory.", Snackbar.LENGTH_LONG);
            snackbar.setAction("Dismiss", o -> snackbar.dismiss());
            snackbar.show();
        } else {
            AppUtils.disableTouch(this);
            mBinding.loginProgress.setVisibility(View.VISIBLE);
            loginUser(id, password, domain);

        }
    }

    /**
     * Used to login the user.
     *
     * @param id               roll number or username
     * @param password         password
     * @param universityDomain domain
     */
    private void loginUser(final String id, final String password, final String universityDomain) {
        final String userEmail = id + "@" + universityDomain;
        FirebaseUtil.getAuth().signInWithEmailAndPassword(userEmail, password)
                .addOnSuccessListener(authResult -> {
                    if (Objects.requireNonNull(authResult.getUser()).isEmailVerified()) {
                        Log.d(TAG, "loginUser: second");
                        getUserDetails(id, universityDomain);
                    } else {
                        AppUtils.enableTouch(this);
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(this, "Your email is not verified. Please verify email first.", Toast.LENGTH_SHORT).show();
                        mBinding.loginProgress.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(e -> {
                    AppUtils.enableTouch(LoginActivity.this);
                    mBinding.loginProgress.setVisibility(View.GONE);
                    String message;
                    try {
                        if (e.getMessage() == null) {
                            message = "There's a problem. Contact your admin.";
                        } else {
                            message = e.getMessage();
                        }
                    } catch (NullPointerException n) {
                        //TODO Add a crashlytics check.
                        message = "There's a problem. Contact your admin.";
                    }
                    final Snackbar snackbar = Snackbar.make(mBinding.loginRootLayout, message, Snackbar.LENGTH_LONG);
                    snackbar.setAction("Dismiss", v -> snackbar.dismiss());
                    snackbar.show();
                });
    }

    /**
     * Get the user details who is logged in and store them locally.
     *
     * @param id               of the user
     * @param universityDomain university's domain
     */
    private void getUserDetails(final String id, final String universityDomain) {
        final String email = id + "@" + universityDomain;
        final DocumentReference documentReference = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY)
                .document(universityDomain).collection(DbConstants.USERS).document(email);

        final OnFailureListener failureListener = e -> {
            FirebaseUtil.getAuth().signOut();
            AppUtils.enableTouch(this);
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            mBinding.loginProgress.setVisibility(View.GONE);
        };
        final OnSuccessListener<DocumentSnapshot> onSuccessListener = userDoc -> {
            SharedPreferenceManager.setLoggedInEmail(email);
            SharedPreferenceManager.setUniversityName(universityDomain);
            if (userDoc.exists()) {
                Log.d(TAG, "getUserDetails: yes");
                final User user = userDoc.toObject(User.class);
                UserManager.getInstance().setUser(user);
                if (user.getApplicationStatus() == 1) {
                    startActivity(new Intent(this, FirstSignInActivity.class));
                } else {
                    startActivity(new Intent(this, HomeActivity.class));
                }
                finish();
            } else {
                Log.d(TAG, "getUserDetails: no");
                SharedPreferenceManager.setApplicationStatus(0);
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(LoginActivity.this, FirstSignInActivity.class));
                    finish();
                }, 1000);
            }
        };


        FirebaseUtil.getFromDocument(documentReference, onSuccessListener, failureListener);
    }

    /**
     * Perform user login on click of this.
     *
     * @param view of the login button
     */
    public void loginUser(final View view) {
        mBinding.loginError.setText("");
        mBinding.loginError.setVisibility(View.GONE);

        //perform some validation
        if (AppUtils.checkInternetConnectivity(this)) {
            final String id = mBinding.loginId.getText().toString();
            final String password = mBinding.loginPassword.getText().toString();

            if (!id.matches("^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$")) {
                final String notValidEmail = "Email not valid.";
                mBinding.loginError.setText(notValidEmail);
                mBinding.loginError.setVisibility(View.VISIBLE);
                return;
            }
            final String[] userId = id.split("@");
            final String domain = userId[1];

            //login in user's account
            login(view, userId[0], password, domain);
        } else {
            final Snackbar snackbar = Snackbar.make(view, "Both fields are mandatory.", Snackbar.LENGTH_LONG);
            snackbar.setAction("Dismiss", v -> snackbar.dismiss());
            snackbar.show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.GOOGLE_SIGN_IN_REQUEST_CODE) {
            final Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //                GoogleSignInAccount account = task.getResult(ApiException.class);
            Log.d(TAG, task.toString());
            handleSignInResult(task);
        }
    }


    /**
     * When sign in gets completed or failed.
     *
     * @param completedTask of login
     */
    private void handleSignInResult(final Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d(TAG, "onActivityResult: ");
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    /**
     * Send user to home activity.
     *
     * @param user logged in user
     */
    private void updateUI(final GoogleSignInAccount user) {
        if (user != null) {
            SharedPreferenceManager.setLoggedInEmail(user.getEmail());
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Unable to login.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method to start activity for sign up.
     *
     * @param view of the sing up button
     */
    public void signUp(final View view) {
        final Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpIntent);
    }

    /**
     * Start reset password activity.
     *
     * @param view of the reset password button
     */
    public void forgotPassword(View view) {
        final Intent resetPasswordIntent = new Intent(this, ResetPassword.class);
        startActivity(resetPasswordIntent);
    }
}
