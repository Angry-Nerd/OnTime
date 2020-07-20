package com.akshit.ontime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.akshit.ontime.R;
import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.databinding.ActivitySplashBinding;
import com.akshit.ontime.ui.auth.LoginActivity;
import com.akshit.ontime.util.SharedPreferenceManager;

import static com.akshit.ontime.util.FirebaseUtil.isLoggedIn;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            //TODO check for if update required
            if (SharedPreferenceManager.isWelcomeScreenShown()) {
                //check for login
                if (isLoggedIn()) {
                    Log.d("akshiban", "onCreate: " + shouldFillApplication());
                    if (shouldFillApplication() != 2) {
                        startActivity(new Intent(SplashActivity.this, FirstSignInActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    }
                    finish();

                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            } else {
                //open the viewpager activity
                startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                finish();
            }
        }, AppConstants.SPLASH_TIME);
    }

    private int shouldFillApplication() {
        return SharedPreferenceManager.getApplicationStatus();
    }
}
