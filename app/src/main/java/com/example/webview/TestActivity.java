package com.example.webview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private String DB_NAME = "question.db";

    private String DB_PATH = "/data/data/com.example.webview/databases/";

    private int count;

    private int corrent;

    private TextView tv_title;

    RadioButton[] mRadioButton = new RadioButton[4];

    private Button btn_up;

    private Button btn_down;

    private TextView tv_result;

    private RadioGroup mRadioGroup;

    private boolean wrongMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initFile();
        initView();
        initDB();

        Button button = (Button)findViewById(R.id.button08);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(TestActivity.this, homeActivity.class);
                startActivity(intent);
                TestActivity.this.finish();
            }
        });

    }


    private void initView() {

        wrongMode = false;

        tv_title = (TextView) findViewById(R.id.tv_title);

        mRadioButton[0] = (RadioButton) findViewById(R.id.RadioA);
        mRadioButton[1] = (RadioButton) findViewById(R.id.RadioB);
        mRadioButton[2] = (RadioButton) findViewById(R.id.RadioC);
        mRadioButton[3] = (RadioButton) findViewById(R.id.RadioD);

        btn_down = (Button) findViewById(R.id.btn_down);
        btn_up = (Button) findViewById(R.id.btn_up);

        tv_result = (TextView) findViewById(R.id.tv_result);

        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
    }


    private void initDB() {
        DBService dbService = new DBService();
        final List<Question> list = dbService.getQuestion();

        count = list.size();
        corrent = 0;

        Question q = list.get(0);
        tv_title.setText(q.question);

        mRadioButton[0].setText(q.answerA);
        mRadioButton[1].setText(q.answerB);
        mRadioButton[2].setText(q.answerC);
        mRadioButton[3].setText(q.answerD);


        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (corrent > 0) {
                    corrent--;

                    Question q = list.get(corrent);

                    tv_title.setText(q.question);

                    mRadioButton[0].setText(q.answerA);
                    mRadioButton[1].setText(q.answerB);
                    mRadioButton[2].setText(q.answerC);
                    mRadioButton[3].setText(q.answerD);

                    tv_result.setText(q.explaination);

                    mRadioGroup.clearCheck();


                    if (q.selectedAnswer != -1) {
                        mRadioButton[q.selectedAnswer].setChecked(true);
                    }
                }

            }
        });


        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (corrent < count - 1) {
                    corrent++;
                    Question q = list.get(corrent);

                    tv_title.setText(q.question);

                    mRadioButton[0].setText(q.answerA);
                    mRadioButton[1].setText(q.answerB);
                    mRadioButton[2].setText(q.answerC);
                    mRadioButton[3].setText(q.answerD);

                    tv_result.setText(q.explaination);

                    mRadioGroup.clearCheck();


                    if (q.selectedAnswer != -1) {
                        mRadioButton[q.selectedAnswer].setChecked(true);
                    }
                } else if (corrent == count - 1 && wrongMode == true) {

                    new AlertDialog.Builder(TestActivity.this).setTitle("提示").setMessage("已經到達最後一道題，是否退出？")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setNegativeButton("取消",null).show();

                } else {

                    final List<Integer> wrongList = checkAnswer(list);

                    if(wrongList.size() == 0){
                        new AlertDialog.Builder(TestActivity.this).setTitle("提示").setMessage("答對了所有題目！")
                                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setNegativeButton("取消",null).show();
                    }


                    new AlertDialog.Builder(TestActivity.this).setTitle("恭喜，答題完成！")
                            .setMessage("答對了" + (list.size() - wrongList.size()) + "道题" + "\n"
                                    + "答錯了" + wrongList.size() + "道題" + "\n" + "是否查看錯題？").setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            wrongMode = true;
                            List<Question> newList = new ArrayList<Question>();
                            for (int i = 0; i < wrongList.size(); i++) {
                                newList.add(list.get(wrongList.get(i)));
                            }
                            list.clear();
                            for (int i = 0; i < newList.size(); i++) {
                                list.add(newList.get(i));
                            }
                            corrent = 0;
                            count = list.size();


                            Question q = list.get(corrent);

                            tv_title.setText(q.question);

                            mRadioButton[0].setText(q.answerA);
                            mRadioButton[1].setText(q.answerB);
                            mRadioButton[2].setText(q.answerC);
                            mRadioButton[3].setText(q.answerD);

                            tv_result.setText(q.explaination);

                            tv_result.setVisibility(View.VISIBLE);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
                }
            }
        });

        //答案选中
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < 4; i++) {
                    if (mRadioButton[i].isChecked() == true) {
                        list.get(corrent).selectedAnswer = i;
                        break;
                    }
                }
            }
        });
    }


    private List<Integer> checkAnswer(List<Question> list) {
        List<Integer> wrongList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).answer != list.get(i).selectedAnswer){
                wrongList.add(i);
            }
        }
        return wrongList;
    }


    private void initFile() {

        if (new File(DB_PATH + DB_NAME).exists() == false) {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }


            try {
                InputStream is = getBaseContext().getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);


                byte[] buffer = new byte[4096];

                int length;


                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }


                os.flush();

                os.close();
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}