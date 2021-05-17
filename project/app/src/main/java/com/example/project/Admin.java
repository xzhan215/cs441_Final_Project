package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Admin extends AppCompatActivity {

    LinearLayout list;
    Button add_button;
    EditText input_box;
    MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        list = (LinearLayout)findViewById(R.id.layout_list);
        add_button = (Button)findViewById(R.id.add);
        input_box = (EditText)findViewById(R.id.inputbox);
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
        View rowview = getLayoutInflater().inflate(R.layout.add_row, null, false);
        TextView content = (TextView)rowview.findViewById(R.id.content);
        content.setText(message);
        ImageView remove = (ImageView)rowview.findViewById(R.id.remove);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeview(rowview);
            }
        });

        list.addView(rowview);
    }

    public void addRow(View view) {
        View rowview = getLayoutInflater().inflate(R.layout.add_row, null, false);
        TextView content = (TextView)rowview.findViewById(R.id.content);
        String input = input_box.getText().toString();
        content.setText(input_box.getText());
        ImageView remove = (ImageView)rowview.findViewById(R.id.remove);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeview(rowview);
            }
        });
        if(!input.equals("")){
            list.addView(rowview);
            insert(input);
            input_box.setText("");
        }
    }

    public void removeview(View view){
        TextView textv = (TextView)view.findViewById(R.id.content);
        String content = textv.getText().toString();
        delete(content);
        list.removeView(view);
    }

    public void insert(String content) {
        //add data to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("item", content);
        long ins = db.insert("Checklist", null, contentValues);
        if(ins == -1) Toast.makeText(getApplicationContext(), "Fail to add content to the database", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getApplicationContext(), "Successfully add content to the database", Toast.LENGTH_SHORT).show();
    }

    public void delete(String content) {
        //delete data from database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long ins = db.delete("Checklist", "item=?", new String[]{content});
        if(ins == -1) Toast.makeText(getApplicationContext(), "Fail to delete content", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getApplicationContext(), "Successfully delete content", Toast.LENGTH_SHORT).show();
    }

    public void switch_back(View view){
        Intent act_action = new Intent(this, AdminPage.class);
        startActivity(act_action);
    }
}