package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Account implements Serializable {
    private String mAccountId = new String();
    private String mBudgets = new String(); // Envelops to which expenses are affected (name, color)
    private String mParticipants = new String(); // List of active participants to the account.

    public void addParticipant(String usr){
        mParticipants = (usr);
    }

    public String getParticipants() {
        return mParticipants;
    }

    public void addBudget(String name, String color){
        mBudgets = name;
    }

    public String getBudgets() {
        return mBudgets;
    }

    public void setAccountId(String id){
        mAccountId = id;
    }
    public String getAccountId() {
        return mAccountId;
    }

    private  void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {

        mAccountId = ois.readUTF() ;
        mParticipants= ois.readUTF();
        mBudgets= ois.readUTF();
    }

    private  void writeObject(ObjectOutputStream oos)
            throws IOException {
        oos.writeUTF(mAccountId);
        oos.writeUTF(mParticipants);
        oos.writeUTF(mBudgets);
    }

}
