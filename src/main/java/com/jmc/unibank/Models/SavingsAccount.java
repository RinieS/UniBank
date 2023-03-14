package com.jmc.unibank.Models;

import javafx.beans.property.DoubleProperty;

import javafx.beans.property.SimpleDoubleProperty;


public class SavingsAccount extends Account{

// withdrawal limit from savings per day
    private final DoubleProperty withdrawalLimit;


    public SavingsAccount(String owner, String accNumber, double bal, double wLimit) {
        super(owner, accNumber, bal);
        this.withdrawalLimit = new SimpleDoubleProperty(this, "Withdrawal limit", wLimit);
    }


    public DoubleProperty withdrawalLimitProperty (){return withdrawalLimit;}
}
