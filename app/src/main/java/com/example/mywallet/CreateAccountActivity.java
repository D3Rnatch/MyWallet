package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import model.Account;
import viewmodels.AccountViewModel;
import viewmodels.AppConfigViewModel;

public class CreateAccountActivity extends AppCompatActivity {

    private AccountViewModel mAccount;
    private NavController mNavController;
    private Button mNextBtn;
    private Button mPrevBtn;

    private static final String AccountResult="ThirdStepResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAccount=new ViewModelProvider(this).get(AccountViewModel.class);

        getSupportFragmentManager().setFragmentResultListener(AccountResult, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult( String requestKey,  Bundle bundle) {
                if(bundle.getBoolean(AccountResult))
                {
                    finalizeActivity(); // Means that the Save button was clicked.
                }
            }
        });
    }

    private void finalizeActivity() {
        // Transmits data
        Intent intentWithResult = new Intent();
        intentWithResult.putExtra("message_return", mAccount.getAccount().getValue());
        setResult(Activity.RESULT_OK, intentWithResult);
        finish(); // End the Activity by sending the Account result.
    }

}