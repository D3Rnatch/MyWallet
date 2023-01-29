package com.example.mywallet;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import viewmodels.AppConfigViewModel;

public class MainActivity extends AppCompatActivity {

    AppConfigViewModel mConfig;
    Button mJoinAccountActivity;
    Button mCreateAccountActivity;
    Button mDisplayAccountActivity;
    TextView mAccountStatus;
    ActivityResultLauncher<Intent> mJoinResult;
    ActivityResultLauncher<Intent> mCreateResult;
    ActivityResultLauncher<Intent> mDisplayResult;

    private enum ActivityRequestCode {
        JOIN_ACTIVITY,
        CREATE_ACTIVITY,
        DISPLAY_ACTIVITY
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConfig = new ViewModelProvider(this).get(AppConfigViewModel.class);
        mJoinAccountActivity = findViewById(R.id.join_account_btn);
        mCreateAccountActivity = findViewById(R.id.create_account_btn);
        mDisplayAccountActivity = findViewById(R.id.continue_btn);
        mAccountStatus = findViewById(R.id.account_default_tv);

        mJoinAccountActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToJoinActivity();
            }
        });
        mCreateAccountActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToCreateActivity();
            }
        });
        mDisplayAccountActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToDisplayActivity();
            }
        });


        mJoinResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Here, no request code
                            Intent data = result.getData();
                            refreshUI();
                        }
                    }
                });

        mCreateResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Here, no request code
                            Intent data = result.getData();
                            refreshUI();
                        }
                    }
                });

        mDisplayResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Here, no request code
                            Intent data = result.getData();
                            refreshUI();
                        }
                    }
                });

        refreshUI();
    }

    private void switchToJoinActivity()
    {
        Intent switchActivityIntent = new Intent(this, JoinAccountActivity.class);
        mJoinResult.launch(switchActivityIntent);
    }

    private void switchToCreateActivity()
    {
        Intent switchActivityIntent = new Intent(this, CreateAccountActivity.class);
        mJoinResult.launch(switchActivityIntent);
    }

    private void switchToDisplayActivity()
    {
        Intent switchActivityIntent = new Intent(this, DisplayAccountActivity.class);
        mJoinResult.launch(switchActivityIntent);
    }

    private void refreshUI()
    {
        mDisplayAccountActivity.setEnabled(mConfig.hasAccount());
        if(mConfig.hasAccount())
        {
            mAccountStatus.setText("Continue on " + mConfig.getAccount().getValue().getAccountId());
        }
        else
        {
            mAccountStatus.setText("No account configured");
        }
    }

}