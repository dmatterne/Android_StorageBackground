package be.david.chucknorrisjokes.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by David on 17/10/2016.
 */

public class AppPreferences {

//    public final static String PREF_NAME = "pref";

    private SharedPreferences sharedPreferences;
    private final static String IS_FIRST_TIME = "isFirstTime";
    private final static String USER_NAME = "username";

    private SharedPreferences.Editor editor;

    private Context context;

    public AppPreferences(Context context) {

        this.context = context;
//        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences = context.getSharedPreferences(AppPreferences.class.getName(), Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();
    }

    public boolean isFirstTime() {

        return sharedPreferences.getBoolean(IS_FIRST_TIME,true);



    }


    public void setFirstTime(boolean b) {

        if (b) {

            editor.putBoolean(IS_FIRST_TIME, false);
            editor.commit();
        }

    }

    public String getUsername() {

        return sharedPreferences.getString(USER_NAME,"");

    }

    public void setUsername(String username) {

        editor.putString(USER_NAME, username);
        editor.commit();
    }
}
