package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SellerHomeActivity extends AppCompatActivity {

    Connection conn;
    Statement stmt1;
    Statement stmt2;
    ResultSet products;
    ResultSet totalSales;
    Button inventory;
    Button addProduct;
    Button updateProduct;
    Button deleteProduct;

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

                String sql = "Select DISTINCT p.name, w.items_sold from warehouse w join product p on w.product_id = p.product_id where w.seller_id = '" + seller_id + "' ORDER BY w.items_sold DESC LIMIT 5;";
                stmt1 = conn.createStatement();
                products = stmt1.executeQuery(sql);
                TextView product_name = (TextView) findViewById(R.id.products);
                TextView items_sold = (TextView) findViewById(R.id.items_sold);
                if (!products.next()) {
                    product_name.setText("You are not selling any products currently!");
                }

                do {
                    String product = products.getString(1);
                    product_name.append(product + "\n");

                    String itemsSold = products.getString(2);
                    items_sold.append(itemsSold + "\n");

                } while (products.next());

                String sql2 = "select SUM(items_sold * price) from warehouse group by seller_id having seller_id = '" + seller_id + "';";
                stmt2 = conn.createStatement();
                totalSales = stmt2.executeQuery(sql2);
                TextView total_sales = (TextView) findViewById(R.id.salesAmt);

                if (!totalSales.next()) {
                    total_sales.setText("$0.00");
                } else {
                   total_sales.setText(totalSales.getString(1));
                }

                inventory = (Button) findViewById(R.id.inventory);
                inventory.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        openActivity("Inventory", seller_id);
                    }
                });

                addProduct = (Button) findViewById(R.id.addProduct);
                addProduct.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        openActivity("Add", seller_id);
                    }
                });

                updateProduct = (Button) findViewById(R.id.updateProduct);
                updateProduct.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        openActivity("Update", seller_id);
                    }
                });

                deleteProduct = (Button) findViewById(R.id.deleteProduct);
                deleteProduct.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        openActivity("Delete", seller_id);
                    }
                });

                products.close();


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (products != null) {
                try {
                    ((ResultSet) products).close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (totalSales != null) {
                try {
                    ((ResultSet) totalSales).close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (stmt2 != null) {
                try {
                    stmt2.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public void openActivity(String button, String seller_id) {
        Intent intentToSend = null;

        if (button.equals("Inventory")) {
            intentToSend = new Intent(this, InventoryActivity.class);
            intentToSend.putExtra("id", seller_id);
        } else if (button.equals("Add")) {
            intentToSend = new Intent(this, AddActivity.class);
            intentToSend.putExtra("id", seller_id);
        } else if (button.equals("Update")) {
            intentToSend = new Intent(this, UpdateActivity.class);
            intentToSend.putExtra("id", seller_id);
        } else {
            intentToSend = new Intent(this, DeleteActivity.class);
            intentToSend.putExtra("id", seller_id);
        }
        startActivity(intentToSend);
//        finish();
    }
}