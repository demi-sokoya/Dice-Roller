package com.example.finalexam;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.preference.PreferenceFragment;


@SuppressWarnings("deprecation")
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefrences);
    }
}

