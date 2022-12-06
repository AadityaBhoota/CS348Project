package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class cartActivity extends AppCompatActivity {
    Connection conn;
    Statement stmt;
    ResultSet result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");


        try {
            ConnectionHelper ch = new ConnectionHelper();
            conn = ch.connect();
            if (conn != null) {

                String sql = "select p.product_name from cart c join product p on ";
                stmt = conn.createStatement();
                result = stmt.executeQuery(sql);

                ArrayList<String> arr = new ArrayList<String>();


                while (result.next()) {
                    arr.add(result.getString(1));
                }


                ArrayAdapter<String> listy = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_multiple_choice, arr);


                ListView viewList = findViewById(R.id.sellerView);
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

    public void back(View v){
        Intent intent = new Intent(this, buyerActivity.class);
        startActivity(intent);
        finish();
//        intent.putExtra("id", uname);
    }

    public void checkOut(View v) {
        Intent intent = new Intent(this, checkoutActivity.class);
        startActivity(intent);
        finish();
    }
}