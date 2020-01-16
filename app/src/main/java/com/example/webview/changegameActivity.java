package com.example.webview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class changegameActivity extends AppCompatActivity {
    double num;
    int pos;
    int day ;
    double price;
    double cap =10000000;
    double count;
    //double pr;
    int date = 30;
    int counter;
    String stocknam="tsmc";
    String[] stocknamelist = {"tsmc","innolux"};
    double [] stockhave;
    double [] stockcap={0,0};
    DecimalFormat df = new DecimalFormat( "0.00");
    static final String TAG = "Firestore.Firebase";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LineGraphSeries<DataPoint> series;
    public Price[] stock = new Price[31];
    public DataPoint[] point = new DataPoint[31];
    Price p2330 = new Price();

    public void changePrice(double index,final String pickstock,final double stock[]) {

        CollectionReference ref = db.collection(pickstock);
        ref.whereEqualTo("index", index)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        p2330 = document.toObject(Price.class);
                        price = p2330.getPrice();
                        String p = df.format(price);
                        Date date = p2330.getDate();

                        TextView textView = findViewById(R.id.textView11);
                        textView.setText(p);
                        int i=pos;
                        stockcap[i] = stock[i]*1000*price;
                        TextView text = findViewById(R.id.textView32);
                        text.setText(Double.toString(stockcap[i]));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        TextView d = findViewById(R.id.textView23);
                        d.setText(dateFormat.format(date));

                    }
                }
                Log.d(TAG, price+"from changePrice->onComplete");
            }
        });
        Log.d(TAG, price+"after changePrice->onComplete");
    }
    public void changeCapital(double index,final String pickstock, final double stock[]) {

        CollectionReference ref = db.collection(pickstock);
        ref.whereEqualTo("index", index)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Price tmp = document.toObject(Price.class);

                        if(pickstock == "tsmc") {
                            stockcap[0] = stock[0] * 1000 * tmp.getPrice();
                            cap -= stockcap[0];
                        }
                        else if(pickstock == "innolux"){
                            stockcap[1] = stock[1] * 1000 * tmp.getPrice();
                            cap -= stockcap[1];
                        }
                        TextView c = findViewById(R.id.textView14);
                        c.setText(Double.toString(cap));
                    }
                }
            }
        });
    }
    public void initGraph(final GraphView graph, final int counter, final String pickstock) {
        final CollectionReference ref = db.collection(pickstock);
        ref.whereGreaterThan("index", counter).orderBy("index", Query.Direction.ASCENDING).limit(31).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int i = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        stock[i] = document.toObject(Price.class);
                        point[i] = new DataPoint(stock[i].getIndex()-counter-1, stock[i].getPrice());
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
        setContentView(R.layout.activity_changegame);

        Bundle bundle = getIntent().getExtras();

        String textnum = bundle.getString("textnum");
        day = bundle.getInt("day");
        double all = bundle.getDouble("textcap");
        num = Double.parseDouble(textnum);
        //double sp =bundle.getDouble("sp");
         counter = bundle.getInt("counter");
         stockhave = bundle.getDoubleArray("stockhavelist");
        //TextView text = findViewById(R.id.textView32);
        //double stockcp = Double.parseDouble(text.getText().toString());
        //cap -=stockcp;

        day +=1;
        date = date+day;

        changePrice(date,stocknamelist[0],stockhave);
        changeCapital(date,stocknamelist[0],stockhave);
        Log.d(TAG, price+"onCreate");

        //計算
        GraphView graph = findViewById(R.id.graph);
        graph.removeAllSeries();
        initGraph(graph,counter,stocknamelist[0]);

        TextView t22 = findViewById(R.id.textView22);
        t22.setText("Day"+day+":");

        TextView t = findViewById(R.id.textView20);
        t.setText(Double.toString(stockhave[0]));

        Button button =  findViewById(R.id.button3);
        button.setOnClickListener(buynum);
        Button reset = findViewById(R.id.button4);
        reset.setOnClickListener(next);
        Button submit = findViewById(R.id.button2);
        submit.setOnClickListener(subm);
        Button sale = findViewById(R.id.button5);
        sale.setOnClickListener(sold);

        Spinner spinner = findViewById(R.id.spinner);
        String[] stockname = {"2330:台積電", "3481:群創"};
        ArrayAdapter<String> stockList = new ArrayAdapter<>(changegameActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                stockname);spinner.setAdapter(stockList);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos =position;
                stocknam =stocknamelist[position];

                GraphView graph = findViewById(R.id.graph);
                graph.removeAllSeries();
                initGraph(graph,counter,stocknamelist[position]);
                changePrice(date,stocknamelist[position],stockhave);

                TextView t = findViewById(R.id.textView20);
                t.setText(Double.toString(stockhave[position]));

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private View.OnClickListener buynum = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText n = findViewById(R.id.editText3);
            double n1 = Double.parseDouble(n.getText().toString());
            TextView t = findViewById(R.id.textView20);
            if(stocknam=="tsmc") {
                count = stockhave[0];
                count += n1;
                t.setText(Double.toString(count));

            }
            else {
                count = stockhave[1];
                count += n1;
                t.setText(Double.toString(count));

            }
        }
    };

  /*  private View.OnClickListener cal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            date+=1;
            day+=1;
            counter+=1;
            //TextView pyes = findViewById(R.id.textView11);
            GraphView graph = findViewById(R.id.graph);
            graph.removeAllSeries();
            initGraph(graph,counter,stocknam);
            changePrice(date,"tsmc",stockhave);
            EditText n = findViewById(R.id.editText3);
            TextView t = findViewById(R.id.textView11);
            TextView c = findViewById(R.id.textView14);
            //轉換資料型態
            double p1 = Double.parseDouble(t.getText().toString());
            double cap1 = Double.parseDouble(c.getText().toString());
            double n1 = Double.parseDouble(n.getText().toString());
            double res = (cap1 - (p1 * 1000 * n1));



             num = num + n1;
            //double py = Double.parseDouble(pyes.getText().toString());
            double all = res + num*p1*1000;
            double stockprice = num*p1*1000;
            TextView text = findViewById(R.id.textView24);
            text.setText(Double.toString(all));

            TextView sp = findViewById(R.id.textView32);
            sp.setText(Double.toString(stockprice));

            //結果
            TextView result =  findViewById(R.id.textView14);
            result.setText(Double.toString(res));
            TextView have = findViewById(R.id.textView20);
            have.setText(Double.toString(num));
            TextView t22 = findViewById(R.id.textView22);
            t22.setText("Day" + day + ':');

        }
    }; */
    private View.OnClickListener next = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            count=0;
            date+=1;
            day+=1;
            counter+=1;

            TextView setzero = findViewById(R.id.textView20);
            if(stocknam=="tsmc"){
                setzero.setText(Double.toString(stockhave[0]));
            }
            else{
                setzero.setText(Double.toString(stockhave[1]));
            }

            TextView t22 = findViewById(R.id.textView22);
            t22.setText("Day" + day + ':');

            changePrice(date,stocknam,stockhave);

            GraphView graph = findViewById(R.id.graph);
            graph.removeAllSeries();
            initGraph(graph,counter,stocknam);
        }
    };
    private View.OnClickListener sold = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText n = findViewById(R.id.editText3);
            double n1 = Double.parseDouble(n.getText().toString());
            TextView t = findViewById(R.id.textView20);
            if(stocknam=="tsmc") {
                count = stockhave[0];
                count -= n1;
                t.setText(Double.toString(count));

            }
            else {
                count = stockhave[1];
                count -= n1;
                t.setText(Double.toString(count));

            }
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