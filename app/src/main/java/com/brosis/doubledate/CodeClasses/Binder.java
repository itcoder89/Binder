package com.brosis.doubledate.CodeClasses;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.brosis.doubledate.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by AQEEL on 10/19/2018.
 */

public class Binder extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        Fresco.initialize(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        /*if (BuildConfig.DEBUG) {
            //initialise reporter with external path
            CrashReporter.initialize(this);
        }*/
        /*AutoErrorReporter.get(this)
                .setEmailAddresses("sanjay.chourasia4@gmail.com")
                .setEmailSubject("Auto Crash Report")
                .start();*/

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        // The following line triggers the initialization of ACRA
       // ACRA.init(this);
    }

}
