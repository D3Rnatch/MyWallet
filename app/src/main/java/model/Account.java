package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Account {
    private String mAccountId;
    private Map<String, String> mBudgets; // Envelops to which expenses are affected (name, color)
    private Set mParticipants; // List of active participants to the account.

    public Account() {
        mBudgets = new HashMap<>();
        mParticipants = new HashSet();
    }

    public Set getParticipants() {
        return mParticipants;
    }

    public Map<String,String> getBudgets() {
        return mBudgets;
    }

    public String getAccountId() {
        return mAccountId;
    }

}
