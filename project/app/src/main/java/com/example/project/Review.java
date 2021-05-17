package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Review extends AppCompatActivity {
    MyDatabaseHelper dbHelper;
    LinearLayout layout;
    Button back1;
    Button back2;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        dbHelper = new MyDatabaseHelper(this);
        layout = (LinearLayout)findViewById(R.id.layout_list);
        back1 = (Button)findViewById(R.id.back1);
        back2 = (Button)findViewById(R.id.back);
        content = (TextView)findViewById(R.id.content);
        initializeChecklist();
    }

    public void initializeChecklist() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Review", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String order_num = cursor.getString(0);
                String factory = cursor.getString(1);
                String time = cursor.getString(3);
                addNewRow(order_num, factory, time);
            }while(cursor.moveToNext());
        }
    }

    public void addNewRow(String order_num, String factory, String time) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        View rowview = getLayoutInflater().inflate(R.layout.review_components, null, false);
        LinearLayout card_layout = (LinearLayout)rowview.findViewById(R.id.card);
        ImageView remove = (ImageView)rowview.findViewById(R.id.remove);
        TextView textv_t = (TextView)rowview.findViewById(R.id.time);
        TextView textv_n = (TextView)rowview.findViewById(R.id.num);
        TextView textv_f = (TextView)rowview.findViewById(R.id.factory);
        textv_f.setText("Factory: " + factory);
        textv_n.setText("#: " + order_num);
        textv_t.setText("Time: " + time);

        card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.rawQuery("Select * from Review where order_number=? and factory=? and created_at=?", new String[] {order_num, factory, time});
                if(cursor.moveToFirst()){
                    String message = cursor.getString(2);
                    content.setText("Order Number: " + order_num + "\n\n" + "Factory: " + factory + "\n\n" + message);
                    content.setVisibility(View.VISIBLE);
                    back1.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                    back2.setVisibility(View.GONE);
                }
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long ins = db.delete("Review", "order_number=? and factory=? and created_at=?", new String[]{order_num,factory,time});
                if(ins == -1) Toast.makeText(getApplicationContext(), "Fail to delete content to the database", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(), "Successfully delete content from the database", Toast.LENGTH_SHORT).show();
                layout.removeView(rowview);
            }
        });


        layout.addView(rowview);
    }

    public void switch_back(View view){
        Intent act_action = new Intent(this, AdminPage.class);
        startActivity(act_action);
    }

    public void back_to_list(View view) {
        content.setText("");
        content.setVisibility(View.GONE);
        back1.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
        back2.setVisibility(View.VISIBLE);
    }
}