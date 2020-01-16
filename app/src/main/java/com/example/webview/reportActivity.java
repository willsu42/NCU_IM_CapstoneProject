package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;
import java.text.DecimalFormat;
public class reportActivity extends AppCompatActivity {
    double[] stockprice={0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Bundle bundle = getIntent().getExtras();

        DecimalFormat df = new DecimalFormat( "0.00");
        stockprice=bundle.getDoubleArray("stockprice");

        double cash = bundle.getDouble("cash");

        double tot = cash+stockprice[0]+stockprice[1];
        double profit = tot-10000000;
        double profitper = profit/10000000*100;

        String p = df.format(profitper);
        TextView textView = findViewById(R.id.textView28);
        textView.setText(Double.toString(profit));
        TextView text = findViewById(R.id.textView29);
        text.setText(p);

        if(profit>0){

            ImageView imageView1 = findViewById( R.id.imageView1);

            imageView1.setImageResource(R.drawable.goodjob);
        }
    }
}
