package com.stevandoh.journalapp.journalapp.actvities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.stevandoh.journalapp.journalapp.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
        View easySplashScreenView = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(GoogleSignInActivity.class)
                .withSplashTimeOut(3000)
                .withLogo(R.mipmap.ic_launcher)
                .withAfterLogoText("Journal App")
                .withFooterText("Copyright \u00A9 2018. All Rights Reserved.")

//                .withLogo(R.drawable.logo)
                .create();
        setContentView(easySplashScreenView);
    }
}
