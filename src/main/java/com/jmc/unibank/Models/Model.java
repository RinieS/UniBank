package com.jmc.unibank.Models;

import com.jmc.unibank.Views.AccountType;
import com.jmc.unibank.Views.ViewFactory;

import java.sql.ResultSet;
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


    private Model(){

        this.viewFactory = new ViewFactory();
        this.dbDriver = new DatabaseDriver();
        //client data section
        this.clientLoginSuccessFlag = false;
        this.client = new Client("", "", "", null, null, null);
        //admin data section


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
}
