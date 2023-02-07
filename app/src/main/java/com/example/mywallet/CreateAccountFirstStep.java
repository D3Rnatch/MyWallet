package com.example.mywallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import viewmodels.AccountViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateAccountFirstStep#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAccountFirstStep extends Fragment {

    private AccountViewModel mAccount;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mTitle;
    private TextInputEditText mAccountName;
    private NavController mController;

    public CreateAccountFirstStep() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateAccountFirstStep.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateAccountFirstStep newInstance(String param1, String param2) {
        CreateAccountFirstStep fragment = new CreateAccountFirstStep();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account_first_step, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle b) {

        mAccount = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        mNextButton = getActivity().findViewById(R.id.next_btn);
        mPrevButton = getActivity().findViewById(R.id.back_btn);
        mAccountName = getActivity().findViewById(R.id.account_name_ed);
        mController = NavHostFragment.findNavController(this);
        mTitle = getActivity().findViewById(R.id.header_tv);
        mTitle.setText("First Step");

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputLayout textInputLayout = getActivity().findViewById(R.id.account_name_ed_layout);
                String text = textInputLayout.getEditText().getText().toString();
                mAccount.getAccount().getValue().setAccountId(text);
                mController.navigate(R.id.action_createAccountFirstStep_to_create_account_second_step);
            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
}