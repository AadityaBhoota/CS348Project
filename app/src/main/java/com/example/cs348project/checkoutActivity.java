package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class checkoutActivity extends AppCompatActivity {
    Connection conn;
    Statement stmt;
    ResultSet result;

    ArrayAdapter<String> priceAdapter;
    ArrayAdapter<String> productAdapter;
    ListView productView;
    ListView priceView;
    ArrayList<String> products;
    ArrayList<String> productIDs;
    ArrayList<String> prices;
    ArrayList<String> sellers;
    String buyer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Intent intent = getIntent();
        ConnectionHelper ch;
        buyer_id = intent.getStringExtra("user");
        productIDs = new ArrayList<>();
        products = new ArrayList<>();
        prices = new ArrayList<>();
        sellers = intent.getStringArrayListExtra("sellers");

        try {
            ch = new ConnectionHelper();
            conn = ch.connect();
            if (conn != null) {
                String sql = "select sum(c.price) from cart c where buyer_id=\"" + buyer_id+"\"";
                stmt = conn.createStatement();
                result = stmt.executeQuery(sql);
                TextView price = (TextView) findViewById(R.id.totalText);
                while (result.next()) {
                    price.setText("$"+result.getString(1));
                }

                sql = "select p.name, c.price, p.product_id from cart c join product p on p.product_id=c.product_id where buyer_id=\"" + buyer_id+"\"";
                stmt = conn.createStatement();
                result = stmt.executeQuery(sql);

                while (result.next()) {
                    products.add(result.getString(1));
                    prices.add(result.getString(2));
                    productIDs.add(result.getString(3));
                }

                productAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, products);
                priceAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, prices);

                productView = findViewById(R.id.productView4);
                priceView = findViewById(R.id.priceView4);
                productView.setAdapter(productAdapter);
                priceView.setAdapter(priceAdapter);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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

    public void order(View view) {
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        ConnectionHelper ch;
        String query;
        try {
            ch = new ConnectionHelper();
            conn = ch.connect();
            if (conn != null) {
                for (int i = 0; i < productIDs.size(); i++) {
//                    query = "Update warehouse where ";
                    System.out.println("Query here");
//                    st = conn.createStatement();
//                    st.executeUpdate(query);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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

