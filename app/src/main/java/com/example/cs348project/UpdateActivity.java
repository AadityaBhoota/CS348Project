package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateActivity extends AppCompatActivity {

    String seller_id;
    EditText updateQuantity;
    ConnectionHelper ch;
    TextView newQuantity;
    TextView currQuantity;
    Button plusQuan;
    Button minusQuan;
    Button applyQuan;
    EditText product_id;
    TextView nameLabel;
    TextView currPrice;
    TextView newPrice;
    EditText updatePrice;

    int product_id_given;

    Connection conn;

    Statement getProduct;
    ResultSet selectedProduct;

    Statement newQuanSQL;
    Statement newPriceSQL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        product_id = (EditText) findViewById(R.id.productID);
        updateQuantity = (EditText) findViewById(R.id.updateQuan);
        newQuantity = (TextView) findViewById(R.id.newQuan);
        currQuantity = (TextView) findViewById(R.id.currQuan);
        updatePrice = (EditText) findViewById(R.id.updatePrice);
        currPrice = (TextView) findViewById(R.id.currPrice);
        newPrice = (TextView) findViewById(R.id.newPrice);
        nameLabel = (TextView) findViewById(R.id.productNameSelected);

        ch = new ConnectionHelper();

//        minusQuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int current_quan = Integer.parseInt(currQuantity.getText().toString());
//                int current_int = Integer.parseInt(updateQuantity.getText().toString());
//                int new_int = current_int - 1;
//                int new_quan = current_quan + new_int;
//                newQuantity.setText(new_quan);
//                updateQuantity.setText(new_int);
//            }
//        });
//
//        plusQuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int current_quan = Integer.parseInt(currQuantity.getText().toString());
//                int current_int = Integer.parseInt(updateQuantity.getText().toString());
//                int new_int = current_int + 1;
//                int new_quan = current_quan + new_int;
//                newQuantity.setText(new_quan);
//                updateQuantity.setText(new_int);
//            }
//        });

        Intent intent = getIntent();
        seller_id = intent.getStringExtra("id");
    }

    public void selectClick(View v) {
        try {
            conn = ch.connect();
            getProduct = conn.createStatement();
            product_id_given = Integer.parseInt(product_id.getText().toString());
            String stmt1 = "Select p.name, w.quantity, w.price from warehouse w join product p on w.product_id = p.product_id where w.product_id = " + (product_id_given) + " AND w.seller_id = '" + seller_id +"';";
            selectedProduct = getProduct.executeQuery(stmt1);

            if (selectedProduct.isBeforeFirst()) {
                selectedProduct.next();
                nameLabel.setText(selectedProduct.getString(1));
                currQuantity.setText(selectedProduct.getString(2));
                currPrice.setText("$" + selectedProduct.getString(3));
            } else {
                nameLabel.setText("Invalid Product ID!");
                currQuantity.setText("Invalid Product ID!");
                currPrice.setText("Invalid Product ID!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Toast.makeText(UpdateActivity.this, "Please input valid Product ID (ER: 10101)", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(UpdateActivity.this, "Please input valid Product ID (ER: 10102)", Toast.LENGTH_SHORT).show();
        }  finally {
            if (getProduct != null) {
                try {
                    getProduct.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (selectedProduct != null) {
                try {
                    selectedProduct.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public void plusClickQuan(View v) {
        try {
            int current_quan = Integer.parseInt(currQuantity.getText().toString());
            int current_int = Integer.parseInt(updateQuantity.getText().toString());
            int new_int = current_int + 1;
            int new_quan = current_quan + new_int;
            newQuantity.setText(Integer.toString(new_quan));
            updateQuantity.setText(Integer.toString(new_int));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(UpdateActivity.this, "Please input valid product id", Toast.LENGTH_SHORT).show();
        }
    }

    public void minusClickQuan(View v) {
        try {
            int current_quan = Integer.parseInt(currQuantity.getText().toString());
            int current_int = Integer.parseInt(updateQuantity.getText().toString());
            int new_int = current_int - 1;
            int new_quan = current_quan + new_int;
            newQuantity.setText(Integer.toString(new_quan));
            updateQuantity.setText(Integer.toString(new_int));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(UpdateActivity.this, "Please input valid product id", Toast.LENGTH_SHORT).show();
        }
    }

    public void applyQuan(View v) {
        try {
            conn = ch.connect();
            int newQuantSQL = Integer.parseInt(newQuantity.getText().toString());
            String newQuanString = "update warehouse set quantity = " + newQuantSQL + " where product_id = " + product_id_given + " and seller_id = '" + seller_id + "';";
            newQuanSQL = conn.createStatement();
            newQuanSQL.executeUpdate(newQuanString);
            updateQuantity.setText("0");
            currQuantity.setText(newQuantity.getText().toString());
            newQuantity.setText("");
            Toast.makeText(UpdateActivity.this, "Changes Applied", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Toast.makeText(UpdateActivity.this, "Please input valid Product ID (ER: 10101)", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(UpdateActivity.this, "Please input valid Product ID (ER: 10102)", Toast.LENGTH_SHORT).show();
        }  finally {
            if (newQuanSQL != null) {
                try {
                    newQuanSQL.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public void plusClickPrice(View v) {
        try {
            double current_price = Double.parseDouble(currPrice.getText().toString().substring(1));
            double current_float = Double.parseDouble(updatePrice.getText().toString());
            double new_float = current_float + 1;
            double new_price = current_price + new_float;
            newPrice.setText("$" + Double.toString(new_price));
            updatePrice.setText(Double.toString(new_float));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(UpdateActivity.this, "Please input valid product id", Toast.LENGTH_SHORT).show();
        }
    }

    public void minusClickPrice(View v) {
        try {
            double current_price = Double.parseDouble(currPrice.getText().toString().substring(1));
            double current_float = Double.parseDouble(updatePrice.getText().toString());
            double new_float = current_float - 1;
            double new_price = current_price + new_float;
            newPrice.setText("$" + Double.toString(new_price));
            updatePrice.setText(Double.toString(new_float));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(UpdateActivity.this, "Please input valid product id", Toast.LENGTH_SHORT).show();
        }
    }

    public void applyPrice(View v) {
        try {
            conn = ch.connect();
            Double newPricint = Double.parseDouble(newPrice.getText().toString().substring(1));
            String newPriceString = "update warehouse set price = " + newPricint + " where product_id = " + product_id_given + " and seller_id = '" + seller_id + "';";
            newPriceSQL = conn.createStatement();
            newPriceSQL.executeUpdate(newPriceString);
            updatePrice.setText("0");
            currPrice.setText(newPrice.getText().toString());
            newPrice.setText("");
            Toast.makeText(UpdateActivity.this, "Changes Applied", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Toast.makeText(UpdateActivity.this, "Please input valid Product ID (ER: 10101)", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(UpdateActivity.this, "Please input valid Product ID (ER: 10102)", Toast.LENGTH_SHORT).show();
        }  finally {
            if (newPriceSQL != null) {
                try {
                    newPriceSQL.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public void updateAnother(View v) {
        Intent intentToRefresh = new Intent(UpdateActivity.this, UpdateActivity.class);
        intentToRefresh.putExtra("id", seller_id);
        startActivity(intentToRefresh);
    }

    public void exit(View v) {
        Intent intentToSend = new Intent(UpdateActivity.this, SellerHomeActivity.class);
        intentToSend.putExtra("id", seller_id);
        startActivity(intentToSend);
    }
}