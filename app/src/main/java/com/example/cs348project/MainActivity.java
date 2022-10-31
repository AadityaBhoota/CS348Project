package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.telecom.ConnectionRequest;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.*;


public class MainActivity extends AppCompatActivity {
    String ConnectionResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            ConnectionHelper ch = new ConnectionHelper();
            conn = ch.connect();
            if (conn != null) {
                String query = "select * from users";
                st = conn.createStatement();
                rs = st.executeQuery(query);
                System.out.println("worked");
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                    System.out.println(rs.getString(2));
                }
            }
        } catch (
                Exception E) {
            System.out.println(E.getMessage());
        } finally {
            if (rs != null) {
                try {
                    ((ResultSet) rs).close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public void helloWorld(View v) {
    }
}