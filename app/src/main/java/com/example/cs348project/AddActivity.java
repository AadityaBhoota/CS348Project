package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddActivity extends AppCompatActivity {

    EditText product_name;
    EditText quantity;
    EditText price;
    RadioGroup categoryGroup;
    RadioButton checked;
    String category;
    String seller_id;

    ConnectionHelper ch;
    Connection conn;
    Statement checkStatement;
    ResultSet checks;

    Statement addStatement;
    Statement addStatementW;

    Statement maxStatement;
    ResultSet maxID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        seller_id = intent.getStringExtra("id");

        ch = new ConnectionHelper();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        product_name = (EditText) findViewById(R.id.productNameTF);
        categoryGroup = (RadioGroup) findViewById(R.id.categoryGroup);
        quantity = (EditText) findViewById(R.id.quantityTF);
        price = (EditText) findViewById(R.id.ppiTF);

    }

    public void addProd(View v) {
        String prodName = product_name.getText().toString();
        int quantitySelected = Integer.parseInt(quantity.getText().toString());
        double priceSelected = Double.parseDouble(price.getText().toString());
        int radioId = categoryGroup.getCheckedRadioButtonId();
        checked = (RadioButton) findViewById(radioId);
        category = checked.getText().toString();

        // Checking if product name already exists in the product table.
        try {
            conn = ch.connect();
            String check = "call sp1('" + prodName + "'," + quantitySelected + ",'" + category + "','" + seller_id + "'," + priceSelected + ");";
            checkStatement = conn.createStatement();
            checks = checkStatement.executeQuery(check);
//
//            if (!checks.isBeforeFirst()) {
//                String max = "Select MAX(product_id) from product;";
//                maxStatement = conn.createStatement();
//                maxID = maxStatement.executeQuery(max);
//                int prod_ID = 1;
//
//                if (maxID.isBeforeFirst()) {
//                    maxID.next();
//                    prod_ID = maxID.getInt(1) + 1;
//                }
//
//                String add = "Insert into product values (" + prod_ID + ",'" + prodName + "','" + category + "','" + category + "');";
//                addStatement = conn.createStatement();
//                addStatement.executeUpdate(add);
//
//                String addW = "Insert into warehouse values ('" + seller_id + "'," + prod_ID + "," + quantitySelected + ",0," + priceSelected + ");";
//                addStatementW = conn.createStatement();
//                addStatementW.executeUpdate(addW);
//            } else {
//                String max = "Select product_id from product where name = '" + prodName + "';";
//                maxStatement = conn.createStatement();
//                maxID = maxStatement.executeQuery(max);
//                maxID.next();
//                int prod_ID = maxID.getInt(1);
//
//                String addW = "Insert into warehouse values ('" + seller_id + "'," + prod_ID + "," + quantitySelected + ",0," + priceSelected + ");";
//                addStatementW = conn.createStatement();
//                addStatementW.executeUpdate(addW);
//            }


            Toast.makeText(AddActivity.this, "Product Added!", Toast.LENGTH_SHORT).show();
            Intent intentToRefresh = new Intent(AddActivity.this, AddActivity.class);
            intentToRefresh.putExtra("id", seller_id);
            startActivity(intentToRefresh);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AddActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (checks != null) {
                try {
                    checks.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (maxID != null) {
                try {
                    maxID.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (addStatementW != null) {
                try {
                    addStatementW.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (addStatement != null) {
                try {
                    addStatement.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (checkStatement != null) {
                try {
                    checkStatement.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (maxStatement != null) {
                try {
                    maxStatement.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public void plusClick(View v) {
        try {
            int current_int = Integer.parseInt(quantity.getText().toString());
            int new_int = current_int + 1;
            quantity.setText(Integer.toString(new_int));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AddActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void minusClick(View v) {
        try {
            int current_int = Integer.parseInt(quantity.getText().toString());
            int new_int = current_int - 1;
            quantity.setText(Integer.toString(new_int));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AddActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View v) {
        Intent intentToSend = new Intent(AddActivity.this, SellerHomeActivity.class);
        intentToSend.putExtra("id", seller_id);
        startActivity(intentToSend);
    }
}