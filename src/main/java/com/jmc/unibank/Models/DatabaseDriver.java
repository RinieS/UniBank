package com.jmc.unibank.Models;

import java.sql.*;

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


    /*
    admin section
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
}
