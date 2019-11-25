package com.example.webview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class GameActivity extends AppCompatActivity {
    int day =1;
    int date=0;
    double count =0;
    public double price;

    static final String TAG = "Firestore.Firebase";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void changePrice(int index) {

        CollectionReference ref = db.collection("price");
        /*for (int index = 0; index < 90; index++) {
            ref.whereEqualTo("index", index + "")
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

                        }

                    }
                }
            });
        }*/

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
        setContentView(R.layout.activity_game);
        changePrice(0);
        Button reset = (Button) findViewById(R.id.button4);
        reset.setOnClickListener(re);
        Button submit = (Button) findViewById(R.id.button2);
        submit.setOnClickListener(subm);



    }
                /* private View.OnClickListener cal = new View.OnClickListener() {
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
                         count = count + n1;

                         //結果
                         TextView result = (TextView) findViewById(R.id.textView14);
                         result.setText(Double.toString(res));
                         TextView have = (TextView) findViewById(R.id.textView20);
                         have.setText(Double.toString(count));


                     }
                 };*/
                 private View.OnClickListener re = new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         date+=1;
                         day+=1;
                         TextView t22 = findViewById(R.id.textView22);
                         t22.setText("Day" + day + ':');

                         changePrice(date);

                     }
                 };
                 private View.OnClickListener subm = new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         TextView pyes = findViewById(R.id.textView11);
                         changePrice(1);
                         EditText n = (EditText) findViewById(R.id.editText3);
                         TextView t = findViewById(R.id.textView11);
                         TextView c = findViewById(R.id.textView14);
                         //轉換資料型態
                         double p1 = Double.parseDouble(t.getText().toString());
                         double cap1 = Double.parseDouble(c.getText().toString());
                         double n1 = Double.parseDouble(n.getText().toString());
                         double res = (cap1 - (p1 * 1000 * n1));
                         count = count + n1;
                         double py = Double.parseDouble(pyes.getText().toString());
                         double all = cap1+(p1-py)*n1*1000;
                         //結果
                         TextView result = (TextView) findViewById(R.id.textView14);
                         result.setText(Double.toString(res));
                         TextView have = (TextView) findViewById(R.id.textView20);
                         have.setText(Double.toString(count));


                         TextView textcap = (TextView) findViewById(R.id.textView14);
                         TextView textnum = (TextView) findViewById(R.id.textView20);

                         Intent intent = new Intent(getApplicationContext(), changegameActivity.class);
                         // int stkNum = Integer.parseInt(editText.getText().toString());
                         intent.putExtra("textcap", textcap.getText().toString());
                         intent.putExtra("textnum", textnum.getText().toString());
                         intent.putExtra("all",all);
                         startActivity(intent);


                     }
                 };
             }



