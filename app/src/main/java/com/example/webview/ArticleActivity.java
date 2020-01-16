package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Button web = findViewById(R.id.button6);
        web.setOnClickListener(website);
        Button webII = findViewById(R.id.button7);
        webII.setOnClickListener(web2);
        Button webIII = findViewById(R.id.button8);
        webIII.setOnClickListener(web3);


    }

    public void buttonClick(View v)
            {
                switch (v.getId())
                {
                    case R.id.buttonLayout1:
                        setContentView(R.layout.main);
                        this.setTitle(R.string.layout1);  //設定標題欄名稱

                        //點選按鈕，回到activity_main佈局檔案
                        //並設定標題欄名稱
                        Button buttonBack1=findViewById(R.id.buttonBack);
                        buttonBack1.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                setContentView(R.layout.activity_article);
                                setTitle(R.string.layoutMain);
                            }
                        });
                        break;
                    case R.id.buttonLayout2:
                        setContentView(R.layout.main2);
                        this.setTitle(R.string.layout2);  //設定標題欄名稱

                        //點選按鈕，回到activity_main佈局檔案
                        //並設定標題欄名稱
                        Button buttonBack2=findViewById(R.id.buttonBack);
                        buttonBack2.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                setContentView(R.layout.activity_article);
                                setTitle(R.string.layoutMain);
                            }
                        });
                        break;
                    case R.id.buttonLayout3:
                        setContentView(R.layout.main3);
                        this.setTitle(R.string.layout3);  //設定標題欄名稱

                        //點選按鈕，回到activity_main佈局檔案
                        //並設定標題欄名稱
                        Button buttonBack3=findViewById(R.id.buttonBack);
                        buttonBack3.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                setContentView(R.layout.activity_article);
                                setTitle(R.string.layoutMain);
                            }
                        });
                        break;
                    case R.id.buttonLayout4:
                        setContentView(R.layout.main4);
                        this.setTitle(R.string.layout4);  //設定標題欄名稱

                        //點選按鈕，回到activity_main佈局檔案
                        //並設定標題欄名稱
                        Button buttonBack4=findViewById(R.id.buttonBack);
                        buttonBack4.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                setContentView(R.layout.activity_article);
                                setTitle(R.string.layoutMain);
                            }
                        });
                        break;
                    case R.id.buttonLayout5:
                        setContentView(R.layout.main5);
                        this.setTitle(R.string.layout5);  //設定標題欄名稱

                        //點選按鈕，回到activity_main佈局檔案
                        //並設定標題欄名稱
                        Button buttonBack5=findViewById(R.id.buttonBack);
                        buttonBack5.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                setContentView(R.layout.activity_article);
                                setTitle(R.string.layoutMain);
                            }
                        });
                        break;
                    case R.id.buttonLayout6:
                        setContentView(R.layout.main6);
                        this.setTitle(R.string.layout6);  //設定標題欄名稱

                        //點選按鈕，回到activity_main佈局檔案
                        //並設定標題欄名稱
                        Button buttonBack6=findViewById(R.id.buttonBack);
                        buttonBack6.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                setContentView(R.layout.activity_article);
                                setTitle(R.string.layoutMain);
                            }
                        });
                        break;
                    case R.id.buttonLayout7:
                        setContentView(R.layout.main7);
                        this.setTitle(R.string.layout7);  //設定標題欄名稱

                        //點選按鈕，回到activity_main佈局檔案
                        //並設定標題欄名稱
                        Button buttonBack7=findViewById(R.id.buttonBack);
                        buttonBack7.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                setContentView(R.layout.activity_article);
                                setTitle(R.string.layoutMain);
                            }
                        });
                        break;
                    case R.id.buttonLayout8:
                        setContentView(R.layout.main8);
                        this.setTitle(R.string.layout8);  //設定標題欄名稱

                        //點選按鈕，回到activity_main佈局檔案
                        //並設定標題欄名稱
                        Button buttonBack8=findViewById(R.id.buttonBack);
                        buttonBack8.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                setContentView(R.layout.activity_article);
                                setTitle(R.string.layoutMain);
                            }
                        });
                        break;
                    case R.id.buttonLayout9:
                        setContentView(R.layout.main9);
                        this.setTitle(R.string.layout9);  //設定標題欄名稱

                        //點選按鈕，回到activity_main佈局檔案
                        //並設定標題欄名稱
                        Button buttonBack9=findViewById(R.id.buttonBack);
                        buttonBack9.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                setContentView(R.layout.activity_article);
                                setTitle(R.string.layoutMain);
                            }
                        });
                        break;


                    default:
                        break;
                }
            }

    private View.OnClickListener website = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



                int key=0;

                Intent i = new Intent();
                i.setClass(getApplicationContext(),webviewActivity.class);
                i.putExtra("key", key);
                startActivity(i);


            }


    };
    private View.OnClickListener web2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            int key=1;

            Intent i = new Intent();
            i.setClass(getApplicationContext(),webviewActivity.class);
            i.putExtra("key", key);
            startActivity(i);


        }


    };
    private View.OnClickListener web3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            int key=3;

            Intent i = new Intent();
            i.setClass(getApplicationContext(),webviewActivity.class);
            i.putExtra("key", key);
            startActivity(i);


        }


    };
}







