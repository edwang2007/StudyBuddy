package hu.ait.studybuddy;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Jana on 6/28/2017.
 */

public class MainApplication extends Application{
    private Realm realmClasses;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

    public void openRealm(){
        RealmConfiguration config = new RealmConfiguration.Builder().
                deleteRealmIfMigrationNeeded().
                build();

        realmClasses = Realm.getInstance(config);
    }

    public void closeRealm(){
        realmClasses.close();
    }

    public Realm getRealm(){
        return realmClasses;
    }
}
