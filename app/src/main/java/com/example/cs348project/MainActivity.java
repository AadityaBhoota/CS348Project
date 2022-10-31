package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    boolean x = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void helloWorld(View v){
        TextView t = (TextView) findViewById(R.id.textview);
        if (x)
            t.setText("Hi");
        else
            t.setText("Bye");
        x = !x;
    }
}