package com.example.webview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Date;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import java.text.SimpleDateFormat;


import com.google.firebase.firestore.Query;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;

//如何把changePrice function中股價拿到其他地方使用？ 例如到oncreate或onclick內做計算


public class GameActivity extends AppCompatActivity {
    //private WebView webView;
    int day =1;
    int date=31;
    double count =0;
    double[] stockhave = {0,0};
    public double price;
    public Price p2330 = new Price();
    int counter=0;
    String stocknam;
    String[] stocknamelist = {"tsmc","innolux"};

    static final String TAG = "Firestore.Firebase";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    LineGraphSeries<DataPoint> series;
    public Price[] stock = new Price[31];
    public DataPoint[] point = new DataPoint[31];

    DecimalFormat df = new DecimalFormat( "0.00");
//問題區
    public void changePrice(int index,String pickstock) {

        CollectionReference ref1 = db.collection(pickstock);


        ref1.whereEqualTo("index", index)
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



    public void initGraph(final GraphView graph,final int counter,String pickstock) {

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
        setContentView(R.layout.activity_game);
        GraphView graph = findViewById(R.id.graph);
        graph.removeAllSeries();

        Button next =  findViewById(R.id.button4);
        next.setOnClickListener(re);
        Button submit = findViewById(R.id.button2);
        submit.setOnClickListener(subm);
        Button reset = findViewById(R.id.button11);
        reset.setOnClickListener(rese);
        Button buy = findViewById(R.id.button10);
        buy.setOnClickListener(buynum);

        Spinner spinner = findViewById(R.id.spinner);
        final String[] stockname = {"2330:台積電", "3481:群創"};
        ArrayAdapter<String> stockList = new ArrayAdapter<>(GameActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                stockname);spinner.setAdapter(stockList);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stocknam =stocknamelist[position];

                GraphView graph = findViewById(R.id.graph);
                graph.removeAllSeries();
                initGraph(graph,counter,stocknam);
                changePrice(date,stocknam);
                Log.d(TAG, price+"after onCreate->onItemSelected");

                TextView setzero = findViewById(R.id.textView20);
                setzero.setText("0");

                TextView num = findViewById(R.id.textView20);
                num.setText(Double.toString(count));
                num.setText(Double.toString(stockhave[position]));

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



    }


//進入下一天button功能
                 private View.OnClickListener re = new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         count=0;
                         date+=1;
                         day+=1;
                         counter+=1;

                         TextView setzero = findViewById(R.id.textView20);
                         setzero.setText("0");

                         TextView t22 = findViewById(R.id.textView22);
                         t22.setText("Day" + day + ':');

                         changePrice(date,stocknam);

                         GraphView graph = findViewById(R.id.graph);
                         graph.removeAllSeries();
                         initGraph(graph,counter,stocknam);

                         if(day>90){
                             Intent i = new Intent();
                             i.setClass(getApplicationContext(),Report2Activity.class);
                             startActivity(i);
                             GameActivity.this.finish();

                         }

                     }
                 };
    //送出button功能
                 private View.OnClickListener subm = new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         TextView pyes = findViewById(R.id.textView11);
                         double py = Double.parseDouble(pyes.getText().toString());

                         date+=1;
                         counter+=1;
                         changePrice(date,stocknam);

                         TextView t = findViewById(R.id.textView11);
                         TextView c = findViewById(R.id.textView14);
                         //轉換資料型態
                         double p1 = Double.parseDouble(t.getText().toString());
                         double cap1 = Double.parseDouble(c.getText().toString());

                         double res = (cap1 - (p1 * 1000 * count));


                         //double all = cap1+(p1-py)*n1*1000;
                         //double sp = py*1000*count;
                         //結果
                         TextView result = findViewById(R.id.textView14);
                         result.setText(Double.toString(res));
                         TextView have =  findViewById(R.id.textView20);
                         have.setText(Double.toString(count));


                         TextView textcap = findViewById(R.id.textView14);
                         TextView textnum = findViewById(R.id.textView20);


                         Intent intent = new Intent(getApplicationContext(), changegameActivity.class);
                         intent.putExtra("textcap",res);
                         intent.putExtra("textnum", textnum.getText().toString());
                         intent.putExtra("day", day);
                         intent.putExtra("counter",counter);
                         intent.putExtra("date",date);
                         intent.putExtra("stockhavelist",stockhave);
                         startActivity(intent);


                     }
                 };
     //重置button功能
    private View.OnClickListener rese = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count=0;
            TextView setzero = findViewById(R.id.textView20);
            setzero.setText("0");
        }
    };

    //買入button功能
    private View.OnClickListener buynum = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText n = findViewById(R.id.editText3);
            double n1 = Double.parseDouble(n.getText().toString());
            if(stocknam=="tsmc") {
                count=0;
                count = count + n1;
                TextView num = findViewById(R.id.textView20);
                num.setText(Double.toString(count));
                stockhave[0] += count;
                num.setText(Double.toString(stockhave[0]));
            }
            else {
                count=0;
                count = count + n1;
                TextView num = findViewById(R.id.textView20);
                num.setText(Double.toString(count));
                stockhave[1]+= count;
            }
        }
    };
}



