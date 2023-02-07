package com.example.mywallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import viewmodels.AccountViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateAccountThirdStep#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAccountThirdStep extends Fragment {

    public final String ResultValue="ThirdStepResult";

    private Button mNextButton;
    private Button mPrevButton;
    private TextView mTitle;
    private NavController mController;
    private ArrayAdapter<String> mListAdapter;
    private ListView mBudgets;
    private Button mAddBtn;
    private TextInputEditText mInput;
    private AccountViewModel mAccount;

    public CreateAccountThirdStep() {
        // Required empty public constructor
    }

    public static CreateAccountThirdStep newInstance(String param1, String param2) {
        CreateAccountThirdStep fragment = new CreateAccountThirdStep();
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
        return inflater.inflate(R.layout.fragment_create_account_third_step, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle b){
        mAccount = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        mListAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_activated_1,
                mAccount.getAccount().getValue().getBudgets());

        mBudgets = (ListView) getActivity().findViewById(R.id.budgets_lv);
        mBudgets.setAdapter(mListAdapter);

        mNextButton = getActivity().findViewById(R.id.next_btn);
        mPrevButton = getActivity().findViewById(R.id.back_btn);
        mPrevButton.setEnabled(true);
        mNextButton.setText("Save");
        mController = NavHostFragment.findNavController(this);
        mTitle = getActivity().findViewById(R.id.header_tv);
        mTitle.setText("Final Step");
        mAddBtn = getActivity().findViewById(R.id.add_budget_btn);
        mInput = getActivity().findViewById(R.id.add_budget_ed);


        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizeFragment(true);
                // Activity will end after catching the result.
            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizeFragment(false);
                mController.navigate(R.id.action_create_account_third_step_to_create_account_second_step);
            }
        });
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputLayout textInputLayout = getActivity().findViewById(R.id.add_budget_ed_layout);
                String text = textInputLayout.getEditText().getText().toString();
                mAccount.addBudget(text);
                mListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void finalizeFragment(boolean res){
        mNextButton.setText("Next");

        Bundle result = new Bundle();
        result.putBoolean(ResultValue, res);
        getActivity().getSupportFragmentManager().setFragmentResult(ResultValue, result);

    }
}