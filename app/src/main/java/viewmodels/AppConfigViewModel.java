package viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import model.Account;

public class AppConfigViewModel extends ViewModel
{
    MutableLiveData<Account> mAccount;


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
