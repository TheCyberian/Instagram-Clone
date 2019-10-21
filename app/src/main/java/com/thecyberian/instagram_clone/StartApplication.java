package com.thecyberian.instagram_clone;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class StartApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("7db6f986197dbc47bed0fe3e48b7d9dd7adc4727")
                .clientKey("d8a7e8c255a2c585337484365bfe4dfe9c09c0ab")
                .server("http://13.233.250.178:80/parse/")
                .build());


//        ParseObject object = new ParseObject("ExampleObject");
//        object.put("myNumber", "145");
//        object.put("myString", "raghav");
//
//        object.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException ex) {
//                if (ex == null) {
//                    Log.i("Parse Result", "Successful!");
//                } else {
//                    Log.i("Parse Result", "Failed - " + ex.toString());
//                    ex.printStackTrace();
//                }
//            }
//        });

        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
