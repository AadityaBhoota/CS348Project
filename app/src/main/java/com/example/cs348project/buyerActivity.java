package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class buyerActivity extends AppCompatActivity {

    Connection conn;
    Statement stmt;
    ResultSet result;

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

                String sql = "select CONCAT(p.name, \"    $\", w.price) from warehouse w join product p on w.product_id = p.product_id where p.category='" + category + "'";
                stmt = conn.createStatement();
                result = stmt.executeQuery(sql);

                ArrayList<String> arr = new ArrayList<String>();


                while (result.next()) {
                    arr.add(result.getString(1));
                }


                ArrayAdapter<String> listy = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_multiple_choice, arr);


                ListView viewList = findViewById(R.id.listview);
                viewList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                viewList.setAdapter(listy);

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
