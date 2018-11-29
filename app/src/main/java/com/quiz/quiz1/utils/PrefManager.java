package com.quiz.quiz1.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContext;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // shared preferences file name
    private static final String PREF_NAME = "quiz-welcome";

    private static final String FIRST_TIME_LAUNCH = "FirstTimeLaunch";

    public PrefManager(Context context){
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(FIRST_TIME_LAUNCH,true);
    }
}
