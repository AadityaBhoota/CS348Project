package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteActivity extends AppCompatActivity {

    Connection conn;
    EditText product_id;
    String seller_id;
    Statement deleteStatement;
    ConnectionHelper ch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        product_id = (EditText) findViewById(R.id.productID);

        System.out.println("Inside create");
        ch = new ConnectionHelper();

        Intent intent = getIntent();
        seller_id = intent.getStringExtra("id");

        System.out.println("inside conn");
        Button deleteProduct2 = (Button) findViewById(R.id.delete);
        Button exit = (Button) findViewById(R.id.another);
    }

    public void deleteClick(View v) {
        try {
            conn = ch.connect();
            deleteStatement = conn.createStatement();
            int product_id_given = Integer.parseInt(product_id.getText().toString());
            String stmt1 = "Delete from warehouse where product_id =" + (product_id_given) + ";";
            deleteStatement.executeUpdate(stmt1);
            product_id.setText("");
            Intent intentToRefresh = new Intent(DeleteActivity.this, DeleteActivity.class);
            intentToRefresh.putExtra("id", seller_id);
            startActivity(intentToRefresh);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Toast.makeText(DeleteActivity.this, "SQL Error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(DeleteActivity.this, "Please input valid Product ID", Toast.LENGTH_SHORT).show();
        }  finally {
            if (deleteStatement != null) {
                try {
                    deleteStatement.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public void exitClick(View v) {
        Intent intentToSend = new Intent(DeleteActivity.this, SellerHomeActivity.class);
        intentToSend.putExtra("id", seller_id);
        startActivity(intentToSend);
    }

}