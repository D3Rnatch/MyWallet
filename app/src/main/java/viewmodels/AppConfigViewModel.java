package viewmodels;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import model.Account;

public class AppConfigViewModel extends ViewModel
{
    private static final String AccountNameKey = "storage_key_account_name";
    private static final String AccountParticipantsKey = "storage_key_account_participants";
    private static final String AccountBudgetsKey = "storage_key_account_budgets";

    // Public members
    public static final String PreferencesName = "AppConfig";


    // Private members
    private MutableLiveData<Account> mAccount=new MutableLiveData<>(new Account());
    private SharedPreferences mInternalPreferences; /// Provided at first load.


    public void save()
    {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = mInternalPreferences.edit();
        editor.putString(AccountNameKey, mAccount.getValue().getAccountId());
        editor.putString(AccountParticipantsKey, gson.toJson(mAccount.getValue().getParticipants()));
        editor.putString(AccountBudgetsKey, gson.toJson(mAccount.getValue().getBudgets()));
        editor.apply();
    }

    public void load(SharedPreferences sharedPref)
    {
        mInternalPreferences = sharedPref;
        mAccount.getValue().setAccountId(sharedPref.getString(AccountNameKey, ""));

        Gson gson = new Gson();
        String json = sharedPref.getString(AccountParticipantsKey, "");
        if(!json.isEmpty()){
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            List<String> content = gson.fromJson(json, type);
            mAccount.getValue().setParticipants(content);
        }

        String json2 = sharedPref.getString(AccountBudgetsKey, "");
        if(!json2.isEmpty()){
            Type type2 = new TypeToken<ArrayList<String>>() {}.getType();
            List<String> content = gson.fromJson(json2, type2);
            mAccount.getValue().setBudgets(content);
        }

    }

    public void addAccount(Account acc)
    {
        if(mAccount == null)
        {
            load();
        }
        mAccount.setValue(acc);
    }

    public LiveData<Account> getAccount()
    {
        if(mAccount == null)
        {
            load();
        }

        return mAccount;
    }

    public boolean hasAccount()
    {
        if(mAccount == null)
        {
            load();
        }

        return !mAccount.getValue().getAccountId().isEmpty();
    }

    private void load()
    {
        mAccount = new MutableLiveData<>();
        mAccount.setValue(new Account());
    }

}
