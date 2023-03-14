package com.jmc.unibank.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Client {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAddress;
    private final ObjectProperty<Account> checkingAccount;
    private final ObjectProperty<Account> savingsAccount;
    private final ObjectProperty<LocalDate>dateCreated;

    public Client (String fName, String lName, String pAddress, Account ch, Account s, LocalDate date){

        this.firstName = new SimpleStringProperty (this, "firstName", fName );
        this.lastName = new SimpleStringProperty(this, "lastName", lName);
        this.payeeAddress = new SimpleStringProperty(this, "payeeAddress", pAddress);
        this.checkingAccount = new SimpleObjectProperty<>(this, "checkingAccount", ch);
        this.savingsAccount = new SimpleObjectProperty<>(this, "savingsAccount", s);
        this.dateCreated = new SimpleObjectProperty<>(this, "dateCreated", date);
    }


    public StringProperty firstNameProperty(){return firstName;}

    public StringProperty lastNameProperty(){return lastName;}

    public StringProperty payeeAddressProperty(){return payeeAddress;}

    public ObjectProperty<Account> chAccountProperty(){return checkingAccount;}

    public ObjectProperty<Account> sAccountProperty (){return savingsAccount;}

    public ObjectProperty<LocalDate> dateProperty(){return dateCreated;}
}
