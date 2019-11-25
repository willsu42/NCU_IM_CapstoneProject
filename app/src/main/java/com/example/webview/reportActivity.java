package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class reportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Bundle bundle = getIntent().getExtras();

        String total = bundle.getString("total");
        double tot = Double.parseDouble(total);
        double profit = tot-10000000;
        double profitper = profit/10000000*100;
        TextView textView = findViewById(R.id.textView28);
        textView.setText(Double.toString(profit));
        TextView text = findViewById(R.id.textView29);
        text.setText(Double.toString(profitper));
    }
}
