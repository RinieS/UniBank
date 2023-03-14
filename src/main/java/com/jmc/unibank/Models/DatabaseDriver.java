package com.jmc.unibank.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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



}
