package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.example.myapplication.R;

public class HelpActivity extends LanguageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        //SettingsActivity.setLanguage(SettingsActivity.languageCode, getResources());

    }
}
