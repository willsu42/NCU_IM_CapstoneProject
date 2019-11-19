package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class changegameActivity extends AppCompatActivity {
    double num;
    int day =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changegame);

        day = day + 1;

        TextView t22 = findViewById(R.id.textView22);
        t22.setText("Day" + day + ':');

        TextView t11 = findViewById(R.id.textView11);
        t11.setText("28");
        Bundle bundle = getIntent().getExtras();

        String textcap = bundle.getString("textcap");
        String textnum = bundle.getString("textnum");
        num = Double.parseDouble(textnum);

        TextView textView = findViewById(R.id.textView14);
        textView.setText(textcap);
        TextView t = findViewById(R.id.textView20);
        t.setText(textnum);

        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(cal);
        Button reset = (Button) findViewById(R.id.button4);
        reset.setOnClickListener(re);
        Button submit = (Button)findViewById(R.id.button2);
        submit.setOnClickListener(subm);
        Button sale = (Button) findViewById(R.id.button5);
        sale.setOnClickListener(sold);


    }

    private View.OnClickListener cal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            EditText n = (EditText) findViewById(R.id.editText3);
            TextView t = findViewById(R.id.textView11);
            TextView c = findViewById(R.id.textView14);
            //轉換資料型態
            double p1 = Double.parseDouble(t.getText().toString());
            double cap1 = Double.parseDouble(c.getText().toString());
            double n1 = Double.parseDouble(n.getText().toString());
            double res = (cap1 - (p1 * 1000 * n1));

             num = num + n1;


            //結果
            TextView result = (TextView) findViewById(R.id.textView14);
            result.setText(Double.toString(res));
            TextView have = (TextView) findViewById(R.id.textView20);
            have.setText(Double.toString(num));


        }
    };
    private View.OnClickListener re = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = getIntent().getExtras();

            String textcap = bundle.getString("textcap");
            String textnum = bundle.getString("textnum");


            //結果
            TextView result = (TextView) findViewById(R.id.textView14);
            result.setText(textcap);
            TextView have = (TextView) findViewById(R.id.textView20);
            have.setText(textnum);
            num = Double.parseDouble(textnum);


        }
    };
    private View.OnClickListener sold = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            EditText n = (EditText) findViewById(R.id.editText3);
            TextView t = findViewById(R.id.textView11);
            TextView c = findViewById(R.id.textView14);
            //轉換資料型態
            double p1 = Double.parseDouble(t.getText().toString());
            double cap1 = Double.parseDouble(c.getText().toString());
            double n1 = Double.parseDouble(n.getText().toString());
            double res = cap1 + (p1 * 1000 * n1);
            num = num - n1;

            //結果
            TextView result = (TextView) findViewById(R.id.textView14);
            result.setText(Double.toString(res));
            TextView have = (TextView) findViewById(R.id.textView20);
            have.setText(Double.toString(num));

        }
    };
    private View.OnClickListener subm = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            day+=1;
            int p=30;
            Random ran = new Random();
            int r = ran.nextInt(7)-3;
            p= p+r;
            TextView t22 = findViewById(R.id.textView22);
            t22.setText("Day" + day + ':');

            TextView t11 = findViewById(R.id.textView11);
            t11.setText(Integer.toString(p));


        }
    };
}