package com.example.webview;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBService {
    private SQLiteDatabase db;


    public DBService() {

        db = SQLiteDatabase.openDatabase("/data/data/com.example.webview/databases/question.db", null, SQLiteDatabase.OPEN_READWRITE);

    }


    public List<Question> getQuestion() {
        List<Question> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from question", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();

            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                Question question = new Question();

                question.ID = cursor.getInt(cursor.getColumnIndex("Field1"));

                question.question = cursor.getString(cursor.getColumnIndex("Field2"));

                question.answerA = cursor.getString(cursor.getColumnIndex("Field3"));
                question.answerB = cursor.getString(cursor.getColumnIndex("Field4"));
                question.answerC = cursor.getString(cursor.getColumnIndex("Field5"));
                question.answerD = cursor.getString(cursor.getColumnIndex("Field6"));

                question.answer = cursor.getInt(cursor.getColumnIndex("Field7"));

                question.explaination = cursor.getString(cursor.getColumnIndex("Field8"));

                question.selectedAnswer = -1;
                list.add(question);
            }
        }
        return list;

    }
}
