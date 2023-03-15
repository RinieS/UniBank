package com.jmc.unibank.Models;

import com.jmc.unibank.Views.AccountType;
import com.jmc.unibank.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver dbDriver;


    //client data section

    private final Client client;
    private boolean clientLoginSuccessFlag;

    //admin data section

    private boolean adminLoginSuccessFlag = false;
    private final ObservableList<Client> clients;


    private Model(){

        this.viewFactory = new ViewFactory();
        this.dbDriver = new DatabaseDriver();
        //client data section
        this.clientLoginSuccessFlag = false;
        this.client = new Client("", "", "", null, null, null);
        //admin data section
        this.adminLoginSuccessFlag = false;
        this.clients = FXCollections.observableArrayList();


    }

    public static synchronized Model getInstance(){
        if (model == null){
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory(){

        return viewFactory;
    }

    public DatabaseDriver getDbDriver() {
        return dbDriver;
    }


    /*
    * client method section
    * */

    public boolean getClientLoginSuccessFlag (){
        return this.clientLoginSuccessFlag;
    }
    public void setClientLoginSuccessFlag(boolean flag){
        this.clientLoginSuccessFlag = flag;
    }

    public Client getClient() {
        return client;
    }

    public void evaluateClientCredentials (String pAddress, String password){

        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet resultSet = dbDriver.getClientData(pAddress, password);
        try{
            if(resultSet.isBeforeFirst()){
                this.client.firstNameProperty().set(resultSet.getString("FirstName"));
                this.client.lastNameProperty().set(resultSet.getString("LastName"));
                this.client.payeeAddressProperty().set(resultSet.getString("PayeeAddress"));
                String[]dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                this.client.dateProperty().set(date);
                checkingAccount = getCheckingAccount(pAddress);
                savingsAccount = getSavingsAccount(pAddress);
                this.client.chAccountProperty().set(checkingAccount);
                this.client.sAccountProperty().set(savingsAccount);
                this.clientLoginSuccessFlag = true;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /*
     * admin method section
     * */

    public boolean getAdminLoginSuccessFlag(){
        return this.adminLoginSuccessFlag;
    }

    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag) {
        this.adminLoginSuccessFlag = adminLoginSuccessFlag;
    }

    public void evaluateAdminCredentials (String username, String password){

        ResultSet resultSet = dbDriver.getAdminData(username, password);

        try{
            if(resultSet.isBeforeFirst()){
                this.adminLoginSuccessFlag = true;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public ObservableList<Client> getClients(){
        return clients;
    }
    public void setClients(){
        CheckingAccount ch;
        SavingsAccount sv;
        ResultSet rs = dbDriver.getAllClients();

        try{
            while(rs.next()){
                String fName = rs.getString("FirstName");
                String lName = rs.getString("LastName");
                String pAddress = rs.getString("PayeeAddress");
                String [] dateArray = rs.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]));
                ch = getCheckingAccount(pAddress);
                sv = getSavingsAccount(pAddress);
                clients.add(new Client(fName, lName,pAddress, ch, sv, date));
            }

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }


    }

    /*
    Utility methods section
    * */

    public CheckingAccount getCheckingAccount (String pAddress){
        CheckingAccount account = null;
        ResultSet rs = dbDriver.getCheckingAccountData(pAddress);
        try{
            String num = rs.getString("AccountNumber");
            int tLimit = (int)rs.getDouble("TransactionLimit");
            double bal = rs.getDouble("Balance");
            account = new CheckingAccount(pAddress, num, bal, tLimit );
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return account;
    }

    public SavingsAccount getSavingsAccount (String pAddress){
        SavingsAccount account = null;
        ResultSet rs = dbDriver.getSavingsAccountData(pAddress);
        try{
            String num = rs.getString("AccountNumber");
            double wLimit = rs.getDouble("WithdrawalLimit");
            double bal = rs.getDouble("Balance");
            account = new SavingsAccount(pAddress, num, bal, wLimit );
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return account;
    }
}
