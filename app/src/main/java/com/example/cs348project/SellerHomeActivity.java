package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SellerHomeActivity extends AppCompatActivity {

    Connection conn;
    Statement stmt;
    ResultSet result;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        try {
            ConnectionHelper ch = new ConnectionHelper();
            conn = ch.connect();

            Intent intent = getIntent();
            String seller_id = intent.getStringExtra("id");

            if (conn != null) {

                String sql = "Select DISTINCT p.name from warehouse w join product p on w.product_id = p.product_id where w.seller_id = '" + seller_id + "';";
                stmt = conn.createStatement();
                result = stmt.executeQuery(sql);
                TextView product_name = (TextView) findViewById(R.id.products);
                if (!result.next()) {
                    product_name.setText("You are not selling any products currently!");
                }

                while (result.next()) {
//                    TextView name = (TextView) findViewById(R.id.textView5);
//                    TextView cat = (TextView) findViewById(R.id.textView6);
                    product_name.append(result.getString(1) + "\n");
//                    name.setText(result.getString(2));
//                    cat.setText(result.getString(3));
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