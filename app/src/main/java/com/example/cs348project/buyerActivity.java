package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class buyerActivity extends AppCompatActivity {

    Connection conn;
    Statement stmt;
    ResultSet result;
    ArrayAdapter<String> priceAdapter;
    ArrayAdapter<String> productAdapter;
    ArrayAdapter<String> sellerAdapter;
    ListView sellerView;
    ListView productView;
    ListView priceView;
    ArrayList<String> sellers;
    ArrayList<String> products;
    ArrayList<String> prices;
    ArrayList<String> productIDs;
    String buyer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        sellerView = findViewById(R.id.sellerView);
        productView = findViewById(R.id.productView);
        priceView = findViewById(R.id.priceView);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        buyer_id = intent.getStringExtra("user");

        try {
            ConnectionHelper ch = new ConnectionHelper();
            conn = ch.connect();
            if (conn != null) {

                String sql = "select w.seller_id, p.name, w.price, p.product_id from warehouse w join product p on w.product_id = p.product_id where p.category='" + category + "'";
                stmt = conn.createStatement();
                result = stmt.executeQuery(sql);

                sellers = new ArrayList<>();
                products = new ArrayList<>();
                prices = new ArrayList<>();
                productIDs = new ArrayList<>();


                while (result.next()) {
                    sellers.add(result.getString(1));
                    products.add(result.getString(2));
                    prices.add(result.getString(3));
                    productIDs.add(result.getString(4));
                }
                sellerAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, sellers);
                productAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, products);
                priceAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_multiple_choice, prices);

                priceView.setAdapter(priceAdapter);
                priceView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                productView.setAdapter(productAdapter);
                sellerView.setAdapter(sellerAdapter);
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

    public void addToCart(View v)  {
        SparseBooleanArray checked = priceView.getCheckedItemPositions();
        String query = "";
        ConnectionHelper ch = new ConnectionHelper();
        Connection conn = ch.connect();
        try {
            if (conn != null) {
                for (int i = 0; i < priceView.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {
                        query = "Insert into cart values (\"" + buyer_id + "\"," + prices.get(i) + "," + productIDs.get(i) + ", 1)";
                        stmt = conn.createStatement();
                        stmt.executeUpdate(query);
                    }
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

    public void addToWishlist(View v)  {
        SparseBooleanArray checked = priceView.getCheckedItemPositions();
        String query = "";
        ConnectionHelper ch = new ConnectionHelper();
        Connection conn = ch.connect();
        try {
            if (conn != null) {
                for (int i = 0; i < priceView.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {
                        query = "Insert into wishlist values (\"" + buyer_id + "\"," + prices.get(i) + "," + productIDs.get(i) + ")";
                        stmt = conn.createStatement();
                        stmt.executeUpdate(query);
                    }
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
