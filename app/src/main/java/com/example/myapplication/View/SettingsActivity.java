package com.example.myapplication.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Model.Datamanagement.DatabaseManager;
import com.example.myapplication.R;

import java.util.HashMap;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private static String TAG = "SettingsActivity";
    private Spinner spinner;
    public static String languageCode = DatabaseManager.with(null).getLanguage();

    private void swapArrayItems(String[] items){
        String first = items[0];
        items[0] = items[1];
        items[1] = first;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.spinner = findViewById(R.id.languageSpinner);

        final String[] languageCodes = new String[]{
                "nl", "en"
        };

        final String[] arraySpinner = new String[] {
                getResources().getString(R.string.dutch), getResources().getString(R.string.english)
        };
        
        if (!languageCode.equals(languageCodes[0])){
            swapArrayItems(languageCodes);
            swapArrayItems(arraySpinner);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arraySpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setAppLocale(languageCodes[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setAppLocale(String localeCode){
        DatabaseManager.with(getApplicationContext()).setLanguage(localeCode);
        Toast.makeText(this, getResources().getString(R.string.languageChanged), Toast.LENGTH_SHORT).show();
    }
}