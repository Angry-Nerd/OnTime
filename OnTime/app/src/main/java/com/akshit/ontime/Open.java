package com.akshit.ontime;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Open extends AppCompatActivity {

    Animation image,text;
    ImageView view;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        view=findViewById(R.id.open);
        textView=findViewById(R.id.onTime);
        textView.setVisibility(View.GONE);
        text=AnimationUtils.loadAnimation(this,R.anim.visible);
        image= AnimationUtils.loadAnimation(this,R.anim.ballon);
        image.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setVisibility(View.VISIBLE);
                textView.setAnimation(text);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setAnimation(image);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent run = new Intent(Open.this,WelcomeActivity.class);
                startActivity(run);
                finish();
            }
        },2000);
    }
}
