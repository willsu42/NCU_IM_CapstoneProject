package com.example.webview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class changegameActivity extends AppCompatActivity {
    double num;
    int day =1;
    double price;
    int date = 0;

    static final String TAG = "Firestore.Firebase";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void changePrice(int index) {

        CollectionReference ref = db.collection("price");


        ref.whereEqualTo("index", index+"")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Price p2330 = document.toObject(Price.class);
                        String index = p2330.getIndex();
                        price = p2330.getPrice();
                        Date date = p2330.getDate();
                        Log.d(TAG, index + price + date);
                        TextView textView = findViewById(R.id.textView11);
                        textView.setText(Double.toString(price));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        TextView d = findViewById(R.id.textView23);
                        d.setText(dateFormat.format(date));
                    }
                }
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changegame);

        day = day + 1;
        date = date +1;

        TextView t22 = findViewById(R.id.textView22);
        t22.setText("Day" + day + ':');

        changePrice(date);

        Bundle bundle = getIntent().getExtras();

        String textcap = bundle.getString("textcap");
        String textnum = bundle.getString("textnum");
        double all = bundle.getDouble("all");
        num = Double.parseDouble(textnum);

        TextView textView = findViewById(R.id.textView14);
        textView.setText(textcap);
        TextView t = findViewById(R.id.textView20);
        t.setText(textnum);

        TextView text = findViewById(R.id.textView24);
        text.setText(Double.toString(all));

        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(cal);
        Button reset = (Button) findViewById(R.id.button4);
        reset.setOnClickListener(next);
        Button submit = (Button)findViewById(R.id.button2);
        submit.setOnClickListener(subm);
        Button sale = (Button) findViewById(R.id.button5);
        sale.setOnClickListener(sold);


    }

    private View.OnClickListener cal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            date+=1;
            day+=1;
            TextView pyes = findViewById(R.id.textView11);
            changePrice(date);
            EditText n = (EditText) findViewById(R.id.editText3);
            TextView t = findViewById(R.id.textView11);
            TextView c = findViewById(R.id.textView14);
            //轉換資料型態
            double p1 = Double.parseDouble(t.getText().toString());
            double cap1 = Double.parseDouble(c.getText().toString());
            double n1 = Double.parseDouble(n.getText().toString());
            double res = (cap1 - (p1 * 1000 * n1));

             num = num + n1;
            double py = Double.parseDouble(pyes.getText().toString());
            double all = res + num*p1*1000;
            TextView text = findViewById(R.id.textView24);
            text.setText(Double.toString(all));

            //結果
            TextView result = (TextView) findViewById(R.id.textView14);
            result.setText(Double.toString(res));
            TextView have = (TextView) findViewById(R.id.textView20);
            have.setText(Double.toString(num));
            TextView t22 = findViewById(R.id.textView22);
            t22.setText("Day" + day + ':');

        }
    };
    private View.OnClickListener next = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            date+=1;
            day+=1;
            TextView t22 = findViewById(R.id.textView22);
            t22.setText("Day" + day + ':');

            changePrice(date);


        }
    };
    private View.OnClickListener sold = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            date+=1;
            day+=1;
            TextView pyes = findViewById(R.id.textView11);
            changePrice(date);
            EditText n = (EditText) findViewById(R.id.editText3);
            TextView t = findViewById(R.id.textView11);
            TextView c = findViewById(R.id.textView14);
            //轉換資料型態
            double p1 = Double.parseDouble(t.getText().toString());
            double cap1 = Double.parseDouble(c.getText().toString());
            double n1 = Double.parseDouble(n.getText().toString());
            double res = cap1 + (p1 * 1000 * n1);
            num = num - n1;
            double py = Double.parseDouble(pyes.getText().toString());
            double all = res+p1*num*1000;

            //結果
            TextView result = (TextView) findViewById(R.id.textView14);
            result.setText(Double.toString(res));
            TextView have = (TextView) findViewById(R.id.textView20);
            have.setText(Double.toString(num));
            TextView t22 = findViewById(R.id.textView22);
            t22.setText("Day" + day + ':');
            TextView text = findViewById(R.id.textView24);
            text.setText(Double.toString(all));

        }
    };
    private View.OnClickListener subm = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            TextView total = findViewById(R.id.textView24);


            Intent intent = new Intent(getApplicationContext(), reportActivity.class);
            // int stkNum = Integer.parseInt(editText.getText().toString());
            intent.putExtra("total", total.getText().toString());

            startActivity(intent);


        }
    };
}