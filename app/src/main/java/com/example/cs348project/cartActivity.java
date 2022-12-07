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

    ArrayAdapter<String> priceAdapter;
    ArrayAdapter<String> productAdapter;
    ListView productView;
    ListView priceView;
    ArrayList<String> products;
    ArrayList<String> prices;
    String buyer_id;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        buyer_id = intent.getStringExtra("user");
        products = new ArrayList<>();
        prices = new ArrayList<>();


        try {
            ConnectionHelper ch = new ConnectionHelper();
            conn = ch.connect();
            if (conn != null) {

                String sql = "select p.name, c.price from cart c join product p on p.product_id=c.product_id where buyer_id=\"" + buyer_id+"\"";
                stmt = conn.createStatement();
                result = stmt.executeQuery(sql);


                while (result.next()) {
                    products.add(result.getString(1));
                    prices.add(result.getString(2));
                }

                productAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, products);
                priceAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, prices);


                productView = findViewById(R.id.productView2);
                priceView = findViewById(R.id.priceView2);
                productView.setAdapter(productAdapter);
                priceView.setAdapter(priceAdapter);
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

    public void back(View v) {
        Intent intent = new Intent(this, buyerActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("user", buyer_id);
        startActivity(intent);
        finish();

    }

    public void checkOut(View v) {
        Intent intent = new Intent(this, checkoutActivity.class);
//        intent.putExtra("category", category);
        startActivity(intent);
        finish();
    }
}