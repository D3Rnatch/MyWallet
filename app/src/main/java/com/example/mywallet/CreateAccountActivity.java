package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import model.Account;
import viewmodels.AppConfigViewModel;

public class CreateAccountActivity extends AppCompatActivity {


    AppConfigViewModel mConfigMdl;

    Button mSave;
    EditText mAccountId;
    EditText mParticipant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mSave = findViewById(R.id.save_btn);
        mAccountId = findViewById(R.id.account_id_name_ev);
        mParticipant = findViewById(R.id.edit_participant_name_ev);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account acc = new Account();
                acc.setAccountId(mAccountId.getText().toString());
                acc.addParticipant(mParticipant.getText().toString());
                acc.addBudget("Unknown", "#cccccc");

                Log.d("Some Tag", "################ Account created");

                // Transmits data
                Intent intentWithResult = new Intent();
                intentWithResult.putExtra("message_return", acc);
                setResult(Activity.RESULT_OK, intentWithResult);
                finish();            }
        });

        mConfigMdl = new ViewModelProvider(this).get(AppConfigViewModel.class);
    }
}