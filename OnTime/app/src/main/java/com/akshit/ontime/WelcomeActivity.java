package com.akshit.ontime;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.preference.Preference;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.prefs.Preferences;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager mPager;
    private int[] layouts={R.layout.welcome_slide_1,R.layout.welcome_slide_2,R.layout.welcome_slide_3};

    private LinearLayout Dots_layout;
    private Button skip,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(new PreferencesManager(this).checkPreference())
            loadHome();
        if(Build.VERSION.SDK_INT>=19)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_welcome);
        mPager=findViewById(R.id.viewPager);
        MpagerAdapter mpagerAdapter = new MpagerAdapter(layouts, this);
        mPager.setAdapter(mpagerAdapter);
        skip=findViewById(R.id.skip);
        next=findViewById(R.id.next);
        skip.setOnClickListener(this);
        next.setOnClickListener(this);
        Dots_layout=findViewById(R.id.dotsLayout);
        createDots(0);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if(position==layouts.length-1)
                {
                    next.setText("Start");
                    skip.setVisibility(View.INVISIBLE);
                }
                else
                {
                    next.setText("Next");
                    skip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createDots(int currentPosition)
    {
        if(Dots_layout!=null)
            Dots_layout.removeAllViews();
        ImageView[] dots = new ImageView[layouts.length];
        for(int i=0;i<layouts.length;i++)
        {
            dots[i]=new ImageView(this);
            if(i==currentPosition)
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dots));
            else
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dots));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            Dots_layout.addView(dots[i],params);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.skip:
                loadHome();
                new PreferencesManager(this).writePreference();
                break;
            case R.id.next:
                loadNextSlide();
                if(next.getText().equals("Start"))
                    new PreferencesManager(this).writePreference();
                break;
        }
    }
    private void loadHome()
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    private void loadNextSlide()
    {
        int next_slide=mPager.getCurrentItem()+1;
        if(next_slide<layouts.length)
        {
            mPager.setCurrentItem(next_slide);
        }
        else
        {
            loadHome();
        }
    }
}
