package com.yusufqedan.programmerhelper.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.yusufqedan.programmerhelper.R;


public abstract class BaseActivityNoActionBar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("pref_dark_theme",false)) {
            setTheme(R.style.AppHackerNoActionBarTheme);
        }
        super.onCreate(savedInstanceState);
    }
}
