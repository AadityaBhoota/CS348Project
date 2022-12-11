package com.example.cs348project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class buyerHomeActivity extends AppCompatActivity {

    ImageButton foodbtn, elecbtn, homebtn, fashbtn;
    String buyer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_home);
        foodbtn = (ImageButton) findViewById(R.id.simpleImageButtonFood);
        elecbtn = (ImageButton) findViewById(R.id.simpleImageButtonElectronics);
        homebtn = (ImageButton) findViewById(R.id.simpleImageButtonHome);
        fashbtn = (ImageButton) findViewById(R.id.simpleImageButtonFashion);
        buyer_id = getIntent().getStringExtra("user");


        foodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buyerHomeActivity.this, buyerActivity.class);
                intent.putExtra("category", "food");
                intent.putExtra("user", buyer_id);
                startActivity(intent);
                finish();
            }
        });

        elecbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buyerHomeActivity.this, buyerActivity.class);
                intent.putExtra("category", "electronics");
                intent.putExtra("user", buyer_id);
                startActivity(intent);
                finish();
            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buyerHomeActivity.this, buyerActivity.class);
                intent.putExtra("category", "home");
                intent.putExtra("user", buyer_id);
                startActivity(intent);
                finish();
            }
        });

        fashbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buyerHomeActivity.this, buyerActivity.class);
                intent.putExtra("category", "fashion");
                intent.putExtra("user", buyer_id);
                startActivity(intent);
                finish();
            }
        });
    }
}