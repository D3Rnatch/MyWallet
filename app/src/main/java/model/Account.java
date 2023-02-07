package model;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Account implements Serializable {
    private String mAccountId = new String();
    private List<String> mBudgets = new ArrayList<String>(); // Envelops to which expenses are affected (name, color)
    private List<String> mParticipants = new ArrayList<String>();

    public void addParticipant(String usr){
        mParticipants.add(usr);
    }

    public void setParticipants(List<String> participants){
        mParticipants = participants;
    }

    public List<String> getParticipants() {
        return mParticipants;
    }

    public void addBudget(String name, String color){
        mBudgets.add(name);
    }

    public List<String> getBudgets() {
        return mBudgets;
    }

    public void setBudgets(List<String> budgets){
        mBudgets = budgets;
    }

    public void setAccountId(String id){
        mAccountId = id;
    }
    public String getAccountId() {
        return mAccountId;
    }

    private  void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {

        mAccountId = ois.readUTF();
        mParticipants = (List<String>) ois.readObject();
        mBudgets= (List<String>) ois.readObject();
    }

    private  void writeObject(ObjectOutputStream oos)
            throws IOException {
        oos.writeUTF(mAccountId);
        oos.writeObject(mParticipants);
        oos.writeObject(mBudgets);
    }

}
