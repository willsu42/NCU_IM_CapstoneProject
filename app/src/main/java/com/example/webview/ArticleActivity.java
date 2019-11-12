package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Button button = (Button)findViewById(R.id.button01);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(ArticleActivity.this, Main2Activity.class);
                startActivity(intent);
                ArticleActivity.this.finish();
            }
        });
        Button button1 = (Button)findViewById(R.id.button03);
        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(ArticleActivity.this, Main3Activity.class);
                startActivity(intent);
                ArticleActivity.this.finish();
            }
        });
        Button button2 = (Button)findViewById(R.id.button04);
        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(ArticleActivity.this, Main4Activity.class);
                startActivity(intent);
                ArticleActivity.this.finish();
            }
        });
        Button button3 = (Button)findViewById(R.id.button05);
        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(ArticleActivity.this, Main5Activity.class);
                startActivity(intent);
                ArticleActivity.this.finish();
            }
        });
        Button button4 = (Button)findViewById(R.id.button06);
        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(ArticleActivity.this, Main6Activity.class);
                startActivity(intent);
                ArticleActivity.this.finish();
            }
        });
        Button button5 = (Button)findViewById(R.id.button07);
        button5.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(ArticleActivity.this, Main7Activity.class);
                startActivity(intent);
                ArticleActivity.this.finish();
            }
        });


    }
}




