package com.example.mywallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import model.Account;
import viewmodels.AccountViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateAccountSecondStep#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAccountSecondStep extends Fragment {

    private AccountViewModel mAccount;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mTitle;
    private NavController mController;
    private ArrayAdapter<String> mListAdapter;
    private ListView mParticipants;
    private Button mAddBtn;
    private TextInputEditText mInput;

    public CreateAccountSecondStep() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CreateAccountSecondStep newInstance(String param1, String param2) {
        CreateAccountSecondStep fragment = new CreateAccountSecondStep();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View v, Bundle b){

        mAccount = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        mListAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_activated_1,
                mAccount.getAccount().getValue().getParticipants());

        mParticipants = (ListView) getActivity().findViewById(R.id.participants_list);
        mParticipants.setAdapter(mListAdapter);

        mNextButton = getActivity().findViewById(R.id.next_btn);
        mPrevButton = getActivity().findViewById(R.id.back_btn);
        mPrevButton.setEnabled(true);
        mController = NavHostFragment.findNavController(this);
        mTitle = getActivity().findViewById(R.id.header_tv);
        mTitle.setText("Second Step");
        mAddBtn = getActivity().findViewById(R.id.add_participants_btn);
        mInput = getActivity().findViewById(R.id.add_participant_ed);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.navigate(R.id.action_create_account_second_step_to_create_account_third_step);
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.navigate(R.id.action_create_account_second_step_to_createAccountFirstStep);
            }
        });

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputLayout textInputLayout = getActivity().findViewById(R.id.add_participant_ed_layout);
                String text = textInputLayout.getEditText().getText().toString();
                mAccount.addParticipants(text);
                mListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account_second_step, container, false);
    }

}