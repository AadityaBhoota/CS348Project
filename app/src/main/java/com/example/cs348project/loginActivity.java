package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.*;


public class loginActivity extends AppCompatActivity {
    String ConnectionResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View v) {
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            ConnectionHelper ch = new ConnectionHelper();
            conn = ch.connect();
            if (conn != null) {
                TextView user = (TextView) findViewById(R.id.editTextTextEmailAddress);
                TextView pass = (TextView) findViewById((R.id.editTextTextPassword));
                RadioGroup g = (RadioGroup) findViewById(R.id.radioGroup);
                int radioId = g.getCheckedRadioButtonId();
                RadioButton b = (RadioButton) findViewById(radioId);
                String type = b.getText().toString();
                String uname = user.getText().toString();
                String pwd = pass.getText().toString();
                String query = "Select * from " + type + "s where username = '" + uname + "'";
                st = conn.createStatement();
                rs = st.executeQuery(query);
                String actUname = "";
                String actPass = "";

                while (rs.next()) {
                    actUname = rs.getString(1);
                    actPass = rs.getString(2);
                }
                if (!actUname.equals("")) {
                    if (pwd.equals(actPass)) {
                        Intent intent = null;
                        if (type.equals("Buyer"))
                             intent = new Intent(this, buyerActivity.class);
                        else
                            intent = new Intent(this, sellerActivity.class);
                        startActivity(intent);
                        Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Wrong password", Toast.LENGTH_LONG).show();
                    }
                } else {
                    query = "Insert into " + type + "s values('" + uname + "', '" + pwd + "')";
                    System.out.println(query);
                    st = conn.createStatement();
                    st.executeUpdate(query);
                    Toast.makeText(this, "Created new user: " + uname, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (
                Exception E) {
            System.out.println(E.getMessage());
        } finally {
            if (rs != null) {
                try {
                    ((ResultSet) rs).close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (st != null) {
                try {
                    st.close();
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