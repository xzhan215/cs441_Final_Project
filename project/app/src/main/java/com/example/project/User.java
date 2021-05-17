package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class User extends AppCompatActivity {

    LinearLayout list;
    MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        list = (LinearLayout)findViewById(R.id.layout_list);
        dbHelper = new MyDatabaseHelper(this);
        initializeChecklist();
    }

    public void initializeChecklist() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Checklist", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String content = "*  " + cursor.getString(0);
                addNewRow(content);
            }while(cursor.moveToNext());
        }
    }

    public void addNewRow(String message) {
        View rowview = getLayoutInflater().inflate(R.layout.add_row_user, null, false);
        TextView content = (TextView)rowview.findViewById(R.id.content);
        content.setText(message);
        list.addView(rowview);
    }

    public void switch_back(View view){
        Intent act_action = new Intent(this, UserPage.class);
        startActivity(act_action);
    }
}