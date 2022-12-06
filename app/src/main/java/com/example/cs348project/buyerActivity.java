package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
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
    ArrayAdapter<String> listy;
    ListView viewList;
    ArrayList<String> arr;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        viewList = findViewById(R.id.listview);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        user_id = intent.getStringExtra("user");

        try {
            ConnectionHelper ch = new ConnectionHelper();
            conn = ch.connect();
            if (conn != null) {

                String sql = "select CONCAT(p.name, \"    $\", w.price) from warehouse w join product p on w.product_id = p.product_id where p.category='" + category + "'";
                stmt = conn.createStatement();
                result = stmt.executeQuery(sql);

                arr = new ArrayList<String>();


                while (result.next()) {
                    arr.add(result.getString(1));
                }

                listy = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_multiple_choice, arr);
                viewList.setAdapter(listy);
                viewList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
//        } finally {
//            if (result != null) {
//                try {
//                    ((ResultSet) result).close();
//                } catch (SQLException e) { /* Ignored */}
//            }
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException e) { /* Ignored */}
//            }
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) { /* Ignored */}
//            }
//        }
        }
    }

    public void addToCart(View v) throws SQLException {
        SparseBooleanArray checked = viewList.getCheckedItemPositions();
        ArrayList<String> checkedItems = new ArrayList<String>();

        for (int i = 0; i < viewList.getAdapter().getCount(); i++) {
            if(checked.get(i)) {
                checkedItems.add(arr.get(i));

            }
        }

        String sql = "Insert";
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
