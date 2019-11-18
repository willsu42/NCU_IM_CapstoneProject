package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;


public class GameActivity extends AppCompatActivity {

    double count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Button button = (Button)findViewById(R.id.button3);
        button.setOnClickListener(cal);
        Button reset = (Button)findViewById(R.id.button4);
        reset.setOnClickListener(re);




        TextView textView = findViewById(R.id.textView11);
        textView.setText("30");
    }
    private View.OnClickListener cal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //DecimalFormat nf = new DecimalFormat("0.00");
            EditText n = (EditText)findViewById(R.id.editText3);
            TextView t = findViewById(R.id.textView11);
            TextView c = findViewById(R.id.textView14);
            //轉換資料型態
            double p1 = Double.parseDouble(t.getText().toString());
            double cap1 = Double.parseDouble(c.getText().toString());
            double n1 = Double.parseDouble(n.getText().toString());
            double res = cap1-(p1*1000*n1);
            count = count +n1;

            //結果
            TextView result = (TextView)findViewById(R.id.textView14);
            result.setText(Double.toString(res));
            TextView have = (TextView)findViewById(R.id.textView20);
            have.setText(Double.toString(count));



        }
    };
    private View.OnClickListener re = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //DecimalFormat nf = new DecimalFormat("0.00");
            //EditText n = (EditText)findViewById(R.id.editText3);
            //TextView t = findViewById(R.id.textView11);
            //TextView c = findViewById(R.id.textView14);
            //轉換資料型態
            //double p1 = Double.parseDouble(t.getText().toString());
            //double cap1 = Double.parseDouble(c.getText().toString());
            //double n1 = Double.parseDouble(n.getText().toString());
            int i = 10000000;
            count = 0;

            //結果
            TextView result = (TextView)findViewById(R.id.textView14);
            result.setText(Integer.toString(i));
            TextView have = (TextView)findViewById(R.id.textView20);
            have.setText(Double.toString(count));



        }
    };
}








