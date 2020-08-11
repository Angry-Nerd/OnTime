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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean shouldShowLoginScreen = false, shouldShowWelcomeScreen = false;
        if (SharedPreferenceManager.isWelcomeScreenShown()) {
            if (!isLoggedIn()) {
                shouldShowLoginScreen = true;
            }
        } else {
            shouldShowWelcomeScreen = true;
        }
        boolean finalShouldShowWelcomeScreen = shouldShowWelcomeScreen;
        boolean finalShouldShowLoginScreen = shouldShowLoginScreen;
        new Handler().postDelayed(() -> {
            //TODO check for if update required
            if (finalShouldShowWelcomeScreen) {
                startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                finish();
            } else {
                if (finalShouldShowLoginScreen) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
//                    if (shouldFillApplication() != 2) {
//                        startActivity(new Intent(SplashActivity.this, FirstSignInActivity.class));
//                    } else {
//
//                    }
                }
                finish();
            }
        }, AppConstants.SPLASH_TIME);
    }

//    private int shouldFillApplication() {
//        return SharedPreferenceManager.getApplicationStatus();
//    }
}
