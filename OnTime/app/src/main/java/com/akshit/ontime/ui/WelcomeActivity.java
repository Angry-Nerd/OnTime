package com.akshit.ontime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.akshit.ontime.R;
import com.akshit.ontime.adapters.IntroViewPagerAdapter;
import com.akshit.ontime.constants.IntroConstants;
import com.akshit.ontime.databinding.ActivityWelcomeBinding;
import com.akshit.ontime.models.ScreenItem;
import com.akshit.ontime.ui.auth.LoginActivity;
import com.akshit.ontime.util.SharedPreferenceManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class WelcomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private List<ScreenItem> itemList;
    private ActivityWelcomeBinding mBinding;
    private IntroViewPagerAdapter mIntroViewPagerAdapter;
    private Animation mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);


        mAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.getting_started_animation);
        itemList = new ArrayList<>();

        itemList.add(new ScreenItem(IntroConstants.SLIDE_ONE_TITLE, IntroConstants.SLIDE_ONE_DESCRIPTION, R.drawable.ic_launcher_foreground));
        itemList.add(new ScreenItem(IntroConstants.SLIDE_TWO_TITLE, IntroConstants.SLIDE_TWO_DESCRIPTION, R.drawable.ic_launcher_foreground));
        itemList.add(new ScreenItem(IntroConstants.SLIDE_THREE_TITLE, IntroConstants.SLIDE_THREE_DESCRIPTION, R.drawable.ic_launcher_foreground));
        mIntroViewPagerAdapter = new IntroViewPagerAdapter(this, itemList);
        mBinding.screenViewpager.setAdapter(mIntroViewPagerAdapter);


        mBinding.tabIndicator.setupWithViewPager(mBinding.screenViewpager);


        mBinding.tabIndicator.addOnTabSelectedListener(this);

    }

    /**
     * When moved to other screen from last screen.
     */
    private void unloadLastScreen() {
        mBinding.tabIndicator.setVisibility(View.VISIBLE);
        mBinding.btnGetStarted.setVisibility(View.INVISIBLE);
        mAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.getting_started_animation);
    }

    /**
     * When moved to last screen.
     */
    private void loadLastScreen() {
        mBinding.tabIndicator.setVisibility(GONE);
        mBinding.btnGetStarted.setVisibility(View.VISIBLE);
        mBinding.btnGetStarted.setAnimation(mAnimation);
    }

    /**
     * When get started button is clicked.
     *
     * @param view of the get started button
     */
    public void getStarted(final View view) {
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        SharedPreferenceManager.setWelcomeScreenShown(true);
        finish();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == itemList.size() - 1) {
            loadLastScreen();
        } else {
            unloadLastScreen();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
