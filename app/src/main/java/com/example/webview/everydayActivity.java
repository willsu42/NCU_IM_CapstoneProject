package com.example.webview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class everydayActivity extends AppCompatActivity {
    int day ;
    double price;
    double count;
    double cash;
    double[] stockprice={0,0};
    double cap = 10000000;
    int date;
    int counter;
    double[] stockhave;
    DecimalFormat df = new DecimalFormat( "0.00");
    static final String TAG = "Firestore.Firebase";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    LineGraphSeries<DataPoint> series;
    public Price[] stock = new Price[31];
    public DataPoint[] point = new DataPoint[31];

    public void changePrice_tsmc(int index) {

        CollectionReference ref1 = db.collection("tsmc");


        ref1.whereEqualTo("index", index)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Price p2330 = document.toObject(Price.class);
                        price = p2330.getPrice();
                        String p = df.format(price);
                        Date date = p2330.getDate();
                        TextView textView = findViewById(R.id.textView47);
                        textView.setText(p);

                        double cap0 = Double.parseDouble(p)*1000*stockhave[0];
                        TextView pt0 =findViewById(R.id.textView49);
                        pt0.setText(Double.toString(cap0));

                        Log.d(TAG, price+"from changePrice->onComplete");
                    }
                }
                Log.d(TAG, price+"after changePrice->onComplete");
            }
        });
    }
    public void changePrice_inno(int index) {

        CollectionReference ref1 = db.collection("innolux");


        ref1.whereEqualTo("index", index)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Price p2330 = document.toObject(Price.class);
                        price = p2330.getPrice();
                        String p = df.format(price);

                        TextView textView = findViewById(R.id.textView50);
                        textView.setText(p);
                        double cap1 = Double.parseDouble(p)*1000*stockhave[1];
                        TextView pt1 =findViewById(R.id.textView52);
                        pt1.setText(Double.toString(cap1));





                        Log.d(TAG, price+"from changePrice->onComplete");
                    }
                }
                Log.d(TAG, price+"after changePrice->onComplete");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everyday);

        Bundle bundle = getIntent().getExtras();
        day = bundle.getInt("day");
        date = bundle.getInt("date");
        counter = bundle.getInt("counter");
        stockhave = bundle.getDoubleArray("stockhavelist");
        date+=1;
        changePrice_tsmc(date);
        changePrice_inno(date);

        TextView text =findViewById(R.id.textView48);
        text.setText(Double.toString(stockhave[0]));
        TextView t = findViewById(R.id.textView51);
        t.setText(Double.toString(stockhave[1]));


        Button b15 =  findViewById(R.id.button15);
        b15.setOnClickListener(cal);
        Button bnext =  findViewById(R.id.button16);
        bnext.setOnClickListener(next);
        Button bend =  findViewById(R.id.button17);
        bend.setOnClickListener(end);
    }

    //15button功能
    private View.OnClickListener cal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            TextView pt0 =findViewById(R.id.textView49);
            TextView pt1 =findViewById(R.id.textView52);
            TextView stocktot=findViewById(R.id.textView53);
            TextView cashtext=findViewById(R.id.textView54);


            stockprice[0]=Double.parseDouble(pt0.getText().toString());
            stockprice[1]=Double.parseDouble(pt1.getText().toString());


            double sp = Double.parseDouble(pt0.getText().toString())+Double.parseDouble(pt1.getText().toString());
            stocktot.setText(Double.toString(sp));
             cash = cap-sp;
            cashtext.setText(Double.toString(cash));
        }
    };
    //15button功能
    private View.OnClickListener next = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getApplicationContext(), tsmc2Activity.class);

            intent.putExtra("day", day);
            intent.putExtra("cash",cash);
            intent.putExtra("counter",counter);
            intent.putExtra("date",date);
            intent.putExtra("stockhavelist",stockhave);
            intent.putExtra("stockprice",stockprice);
            startActivity(intent);
        }
    };
    private View.OnClickListener end = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getApplicationContext(), reportActivity.class);

            intent.putExtra("day", day);
            intent.putExtra("cash",cash);
            intent.putExtra("counter",counter);
            intent.putExtra("date",date);
            intent.putExtra("stockhavelist",stockhave);
            intent.putExtra("stockprice",stockprice);
            startActivity(intent);
        }
    };

}
