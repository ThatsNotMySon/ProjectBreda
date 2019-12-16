package com.example.myapplication.View;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public abstract class LanguageActivity extends AppCompatActivity {
    public static String languageCode = "en";

    @Override
    protected void onResume() {
        super.onResume();
        if(!languageCode.equals(SettingsActivity.languageCode)){
            languageCode = SettingsActivity.languageCode;
            recreate();
        }
        Log.i("onResume", "PING");
    }
}
