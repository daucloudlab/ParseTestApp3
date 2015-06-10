package kz.abcsoft.parse.android.app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "t8CwzNxISqA0ilkYgzSBuiN9EOSxzpngvfjL9re0", "D4guPV46qrcElP9hnsUr7XgP29LwKcY7uHS8f1PB");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACl = new ParseACL() ;
        //defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACl, true);
    }
}
