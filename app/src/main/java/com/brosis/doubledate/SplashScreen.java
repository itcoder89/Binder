package com.brosis.doubledate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.brosis.doubledate.CodeClasses.Variables;
import com.brosis.doubledate.storage.LocalStorage;

import androidx.annotation.Nullable;


public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_);
        sharedPreferences=getSharedPreferences(Variables.pref_name,MODE_PRIVATE);
        //if(LocalStorage.getCheckIsLogin(SplashScreen.this).equals("true")){
          //  SPLASH_TIME_OUT = 0;
       /// }
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
             //   Intent i = new Intent(SplashScreen.this, Home.class);
              //  startActivity(i);
              //  finish();
              //  handler.removeCallbacks(this);
            }
        }, SPLASH_TIME_OUT);
        /*new Handler().postDelayed(new Runnable() {
            *//*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             *//*
            @Override
            public void run() {
                //if(LocalStorage.getCheckIsLogin(SplashScreen.this).equals("true")){
                    Intent i = new Intent(SplashScreen.this, Home.class);
                    startActivity(i);
                    finish();
               *//* }else{
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }*//*
            }
        }, SPLASH_TIME_OUT);*/
    }
}
