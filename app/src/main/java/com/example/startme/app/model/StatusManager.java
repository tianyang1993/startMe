package com.example.startme.app.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.startme.app.R;

/**
 * Created by Tian on 4/6/2014.
 */
public class StatusManager {

    private final String PREF_NAME = "com.example.startme.app";

    public static final int NO_LOGIN = 0;
    public static final int LOGIN_WITH_FB = 1;
    public static final int LOGIN_WITH_WB = 2;

    private int nLogin = NO_LOGIN;

    private static StatusManager instance = null;

    public static StatusManager getInstance(){

        if (instance == null){
            instance = new StatusManager();
        }

        return instance;
    }

    public void setLogin(int nLogin){
        this.nLogin = nLogin;
    }

    public int getLogin(){
        return this.nLogin;
    }

    public void read(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        this.nLogin = pref.getInt("login_key", NO_LOGIN);

    }

    public void write(Context context){

        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("login_key", nLogin);
        editor.commit();
    }
}
