package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import viewmodels.AppConfigViewModel;

public class DisplayAccountActivity extends AppCompatActivity {

    AppConfigViewModel mConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_account);

        mConfig = new ViewModelProvider(this).get(AppConfigViewModel.class);
        mConfig.load(getSharedPreferences(mConfig.PreferencesName, Context.MODE_PRIVATE));

        Button nextBtn = findViewById(R.id.next_btn);
        nextBtn.setVisibility(View.INVISIBLE);

        Button backBtn = findViewById(R.id.back_btn);
        backBtn.setVisibility(View.INVISIBLE);

        TextView tv = findViewById(R.id.header_tv);
        tv.setText(mConfig.getAccount().getValue().getAccountId());
    }
}