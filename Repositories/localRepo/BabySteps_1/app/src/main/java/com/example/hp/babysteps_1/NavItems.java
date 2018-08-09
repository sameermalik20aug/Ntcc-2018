package com.example.hp.babysteps_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class NavItems extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_items);
        textView = findViewById(R.id.tView);
         if(getIntent().getExtras().getInt("HELP") == 1)
         {
             textView.setText("All the Help That You Need");
         }
         else if(getIntent().getExtras().getInt("HELP") == 2)
         {
             textView.setText("About The App");
         }
         else
         {
             Toast.makeText(this, "Hello There!", Toast.LENGTH_SHORT).show();
         }
    }

}
