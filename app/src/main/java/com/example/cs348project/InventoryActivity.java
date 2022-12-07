package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InventoryActivity extends AppCompatActivity {

    Connection conn;
    ResultSet products;
    Statement stmt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        try {
            ConnectionHelper ch = new ConnectionHelper();
            conn = ch.connect();

            Intent intent = getIntent();
            String seller_id = intent.getStringExtra("id");

            if (conn != null) {

                String sql = "select w.product_id, p.name, w.quantity, w.price, w.items_sold from warehouse w join product p on w.product_id = p.product_id where seller_id = '" + seller_id + "' ORDER BY product_id;";
                stmt1 = conn.createStatement();
                products = stmt1.executeQuery(sql);
                TextView product_id = (TextView) findViewById(R.id.productID);
                product_id.setGravity(Gravity.CENTER);
                TextView product_name = (TextView) findViewById(R.id.productName);
                product_name.setGravity(Gravity.CENTER);
                TextView quantity = (TextView) findViewById(R.id.quantity);
                quantity.setGravity(Gravity.CENTER);
                TextView price = (TextView) findViewById(R.id.price);
                price.setGravity(Gravity.CENTER);
                TextView items_sold = (TextView) findViewById(R.id.sold);
                items_sold.setGravity(Gravity.CENTER);

                products.next();
                do {
                    String id = products.getString(1);
                    String name = products.getString(2);
                    String quantityItem = products.getString(3);
                    String priceItem = products.getString(4);
                    String itemsSold = products.getString(5);

                    product_id.append(id + "\n");
                    product_name.append(name + "\n");
                    quantity.append(quantityItem + "\n");
                    price.append(priceItem + "\n");
                    items_sold.append(itemsSold + "\n");
                } while (products.next());

                stmt1.close();
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
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }


}