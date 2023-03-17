package com.example.demo.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    static Connection connection;

    static Statement statement;

    final String jdbcUrl = "jdbc:h2:./data_encrypt";

    public boolean dbConnect(){
        try{
            connection = DriverManager.getConnection(jdbcUrl,"encrypt","encrypt");
            return true;
        }catch (SQLException se){
            se.printStackTrace();
            return false;
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
