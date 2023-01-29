package viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import model.Account;

public class AppConfigViewModel extends ViewModel
{
    MutableLiveData<Account> mAccount;

    public AppConfigViewModel() {
        mAccount = new MutableLiveData<>();
        mAccount.setValue(new Account());
    }

    public LiveData<Account> getAccount() {
        return mAccount;
    }

    public boolean hasAccount() {
        return false;
    }

}
