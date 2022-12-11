package com.example.cs348project;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.*;

public class ConnectionHelper {
    Connection con;
    String uname, pass, ip, port, database;

    @SuppressLint("NewApi")

    public Connection connect() {

        ip = "34.123.191.70";
        database = "cs348";
        uname = "root";
        pass = "12345678";
        String url = "jdbc:mysql://34.123.191.70/cs348";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, uname, pass);

        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }
        return con;
    }

}