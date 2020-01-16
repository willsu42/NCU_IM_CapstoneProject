package com.example.webview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class tsmc2Activity extends AppCompatActivity {
    int day ;
    double price;
    double count;
    int date;
    double cash;
    int counter;
    double[] stockprice;
    double[] stockhave={0,0} ;

    static final String TAG = "Firestore.Firebase";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    LineGraphSeries<DataPoint> series;
    public Price[] stock = new Price[31];
    public DataPoint[] point = new DataPoint[31];

    public void changePrice(int index) {

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
                        TextView textView = findViewById(R.id.textView11);
                        textView.setText(p);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        TextView d = findViewById(R.id.textView23);
                        d.setText(dateFormat.format(date));
                        Log.d(TAG, price+"from changePrice->onComplete");
                    }
                }
                Log.d(TAG, price+"after changePrice->onComplete");
            }
        });
    }

    DecimalFormat df = new DecimalFormat( "0.00");
    public void initGraph(final GraphView graph, final int counter) {
        final CollectionReference ref = db.collection("tsmc");
        ref.whereGreaterThan("index", counter).orderBy("index", Query.Direction.ASCENDING).limit(31).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int i = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        stock[i] = document.toObject(Price.class);
                        point[i] = new DataPoint(stock[i].getIndex() - counter - 1, stock[i].getPrice());
                        i++;
                    }
                    series = new LineGraphSeries<>(point);
                    series.setDrawDataPoints(true);
                    series.setDataPointsRadius(8);
                    series.setThickness(5);
                    series.setTitle(ref.getId());
                }
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(30);
                graph.getViewport().setScalable(true);

                graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().setTextSize(45);
                graph.getLegendRenderer().setTextColor(Color.WHITE);
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                graph.addSeries(series);

            }

        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsmc2);
        Bundle bundle = getIntent().getExtras();
        day = bundle.getInt("day");
        date = bundle.getInt("date");
        cash = bundle.getDouble("cash");
        stockprice =bundle.getDoubleArray("stockprice");
        counter = bundle.getInt("counter");
        stockhave = bundle.getDoubleArray("stockhavelist");
        day+=1;
        counter+=1;
        TextView t14 = findViewById(R.id.textView14);
        t14.setText(Double.toString(cash));
        TextView t32 = findViewById(R.id.textView32);
        t32.setText(Double.toString(stockprice[0]));
        TextView t20 = findViewById(R.id.textView20);
        t20.setText(Double.toString(stockhave[0]));
        TextView t22 = findViewById(R.id.textView22);
        t22.setText("Day"+day+":");

        changePrice(date);
        GraphView graph = findViewById(R.id.graph);
        graph.removeAllSeries();
        initGraph(graph,counter);

        Button submit = findViewById(R.id.button12);
        submit.setOnClickListener(subm);
        Button reset = findViewById(R.id.button14);
        reset.setOnClickListener(rese);
        Button buy = findViewById(R.id.button3);
        buy.setOnClickListener(buynum);
        Button b5 = findViewById(R.id.button5);
        b5.setOnClickListener(sell);
    }

    //買入button功能
    private View.OnClickListener buynum = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText n = findViewById(R.id.editText3);
            double n1 = Double.parseDouble(n.getText().toString());

            count = stockhave[0];
            count = count + n1;
            TextView num = findViewById(R.id.textView20);
            num.setText(Double.toString(count));
        }
    };

    //重置button功能
    private View.OnClickListener rese = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count=0;
            TextView setzero = findViewById(R.id.textView20);
            setzero.setText(Double.toString(stockhave[0]));
        }
    };

    //送出button功能
    private View.OnClickListener subm = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView have = findViewById(R.id.textView20);
            stockhave[0] = Double.parseDouble(have.getText().toString());
            Intent intent = new Intent(getApplicationContext(), inno2Activity.class);

            intent.putExtra("day", day);
            intent.putExtra("counter",counter);
            intent.putExtra("date",date);
            intent.putExtra("stockhavelist",stockhave);
            intent.putExtra("cash",cash);
            intent.putExtra("stockprice",stockprice);
            startActivity(intent);
        }
    };
    //賣出button功能
    private View.OnClickListener sell = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText n = findViewById(R.id.editText3);
            double n1 = Double.parseDouble(n.getText().toString());

            count = stockhave[0];
            count = count - n1;
            TextView num = findViewById(R.id.textView20);
            num.setText(Double.toString(count));
            TextView t11 = findViewById(R.id.textView11);


            double cap2= cash+n1*1000*Double.parseDouble(t11.getText().toString());
            TextView t14 = findViewById(R.id.textView14);
            t14.setText(Double.toString(cap2));
            TextView t32 = findViewById(R.id.textView32);

            double sp2 = stockprice[0] -n1*1000*Double.parseDouble(t11.getText().toString());
            t32.setText(Double.toString(sp2));




        }
    };
}
