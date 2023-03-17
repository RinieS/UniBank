package com.jmc.unibank.Models;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {

    private Connection connection;

    public DatabaseDriver (){

        try{
            this.connection = DriverManager.getConnection("jdbc:sqlite:unibank.db");
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    /*
    Client section
    **/

    public ResultSet getClientData(String pAddress, String password){
        Statement statement;
        ResultSet rs = null;
         try{
             statement = this.connection.createStatement();
             rs = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress= '"+pAddress+"' AND Password= '"+password+"';");
         }
         catch (SQLException ex){
             ex.printStackTrace();
         }

         return rs;
    }

    public ResultSet getTransactions(String pAddress, int limit){
        Statement statement;
        ResultSet rs = null;
        try{
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Transactions WHERE Sender = '"+pAddress+"' OR Receiver = '"+pAddress+"' LIMIT "+limit+";");
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return rs;
    }

    //method returns savings account balance
    public double getSavingsAccountsBalance (String pAddress){
        Statement statement;
        ResultSet rs;
        double balance=0;

        try{
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner='"+pAddress+"';");
            balance = rs.getDouble("Balance");

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return balance;
    }

//method to add/subtract from acct balance
    public void updateBalance(String pAddress, double amount, String operation){
        Statement statement;
        ResultSet rs;

        try{
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner='"+pAddress+"';");
            double newBal;
           if(operation.equals("ADD")){
                newBal = rs.getDouble("Balance")+ amount;
               statement.executeUpdate("UPDATE SavingsAccounts SET Balance= "+newBal+" WHERE Owner='"+pAddress+"';");
           } else {
               if(rs.getDouble("Balance") >= amount){
                   newBal = rs.getDouble("Balance")-amount;
                   statement.executeUpdate("UPDATE SavingsAccounts SET Balance= "+newBal+" WHERE Owner='"+pAddress+"';");

               }
           }


        }catch(SQLException ex){
            ex.printStackTrace();
        }

    }

    //creates and records new Transaction

    public void newTransaction(String sender, String receiver, double amount, String message){
        Statement statement;

        try{
            statement = this.connection.createStatement();
            LocalDate date = LocalDate.now();
            statement.executeUpdate("INSERT INTO "+
                    "Transactions(Sender, Receiver, Amount, Date, Message)" +
                    "VALUES ('"+sender+"', '"+receiver+"', "+amount+", '"+message+"');");

        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }



    /*
    *admin section
    **/

    public ResultSet getAdminData (String username, String password){
        Statement statement;
        ResultSet rs = null;

        try{
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Admins WHERE Username= '"+username+"'AND Password= '"+password+"'; ");
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return rs;
    }

    public void createClient(String fName, String lName, String pAddress, String password, LocalDate date){

        Statement statement;

        try{
            statement = this.connection.createStatement();
            statement.executeUpdate("INSERT INTO "+
                    "Clients(FirstName, LastName, PayeeAddress, Password, Date)"+
                    "VALUES ('"+fName+"', '"+lName+"', '"+pAddress+"', '"+password+"', '"+date.toString()+"');");
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }


    }
    public void createCheckingAccount(String owner, String number, double limit, double balance ){
        Statement statement;

        try{
            statement = this.connection.createStatement();
            statement.executeUpdate("INSERT INTO "+
                    "CheckingAccounts(Owner, AccountNumber, TransactionLimit, Balance)"+
                    "VALUES ('"+owner+"', '"+number+"', "+limit+", "+balance+");");
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public void createSavingsAccount(String owner, String number, double limit, double balance ){
        Statement statement;

        try{
            statement = this.connection.createStatement();
            statement.executeUpdate("INSERT INTO "+
                    "SavingsAccounts(Owner, AccountNumber, WithdrawalLimit, Balance)"+
                    "VALUES ('"+owner+"', '"+number+"', "+limit+", "+balance+");");
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public ResultSet getAllClients(){
        Statement statement;
        ResultSet rs=null;
        try{
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Clients;");

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return rs;
    }

    public ResultSet searchClient(String pAddress){
        Statement statement;
        ResultSet rs = null;
        try {
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress='"+pAddress+"';");

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return rs;
    }

    public void depositSavings(String pAddress, Double amnt){
        Statement statement;
        try{
            statement = this.connection.createStatement();
            statement.executeUpdate("UPDATE SavingsAccounts SET Balance = "+amnt+" WHERE Owner = '"+pAddress+"';");

        }catch(SQLException ex){
            ex.printStackTrace();
        }

    }


    /*
     *utility section
     **/

    public int getLastClientsId(){
        Statement statement;
        ResultSet rs;
        int id = 0;
        try {
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name = 'Clients'; ");
            id = rs.getInt("seq");
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return id;
    }

    public ResultSet getCheckingAccountData(String pAddress){
        Statement statement;
        ResultSet rs = null;

        try{
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE Owner = '"+pAddress+"'; ");

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return rs;
    }
    public ResultSet getSavingsAccountData(String pAddress){
        Statement statement;
        ResultSet rs = null;

        try{
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner = '"+pAddress+"'; ");

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return rs;
    }
}
