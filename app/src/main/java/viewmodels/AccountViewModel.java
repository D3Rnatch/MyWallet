package viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import model.Account;

public class AccountViewModel extends ViewModel {
    MutableLiveData<Account> mAccount = new MutableLiveData<>(new Account());

    public LiveData<Account> getAccount()
    {
        return mAccount;
    }

    public void addParticipants(String usr)
    {
        Account data = mAccount.getValue();
        data.addParticipant(usr);
        mAccount.setValue(data);
    }

    public void addBudget(String bud)
    {
        Account data = mAccount.getValue();
        data.addBudget(bud, "#cccccc");
        mAccount.setValue(data);
    }

}
