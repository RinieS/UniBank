package com.jmc.unibank.Models;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CheckingAccount extends Account{


    //# of transactions per day
    private final IntegerProperty transactionLimit;


    public CheckingAccount(String owner, String accNumber, double bal, int limit) {
        super(owner, accNumber, bal);
        this.transactionLimit = new SimpleIntegerProperty(this, "Transaction Limit", limit);
    }

    public IntegerProperty transactionLimitProperty (){return transactionLimit;}


    @Override
    public String toString(){
        return accountNumberProperty().get();
    }
}
