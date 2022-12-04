package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class buyerActivity extends AppCompatActivity {

    Connection conn;
    Statement stmt;
    ResultSet result;
//    ListView listview = findViewById(R.id.category_data);
//    String[] headers = {"ID","Name","Category"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        try {
            ConnectionHelper ch = new ConnectionHelper();
            conn = ch.connect();
            if (conn != null) {

                String sql = "select * from product where category='" + category + "'";
                stmt = conn.createStatement();
                result = stmt.executeQuery(sql);

                while (result.next()) {
                    TextView id = (TextView) findViewById(R.id.textView4);
                    TextView name = (TextView) findViewById(R.id.textView5);
                    TextView cat = (TextView) findViewById(R.id.textView6);
                    id.setText(result.getString(1));
                    name.setText(result.getString(2));
                    cat.setText(result.getString(3));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (result != null) {
                try {
                    ((ResultSet) result).close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }
}