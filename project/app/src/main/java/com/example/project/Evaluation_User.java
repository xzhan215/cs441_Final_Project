package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Evaluation_User extends AppCompatActivity {
    MyDatabaseHelper dbHelper;
    LinearLayout layout;
    Button back;
    Button submit;
    EditText order_num;
    EditText factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation__user);
        dbHelper = new MyDatabaseHelper(this);
        layout = (LinearLayout)findViewById(R.id.layout_list);
        back = (Button)findViewById(R.id.back);
        submit = (Button)findViewById(R.id.submit);
        order_num = (EditText)findViewById(R.id.number);
        factory = (EditText)findViewById(R.id.factory);
        initializeChecklist();
    }

    public void initializeChecklist() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Checklist", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String content = cursor.getString(0);
                addNewRow(content);
            }while(cursor.moveToNext());
        }
    }

    public void addNewRow(String message) {
        View rowview = getLayoutInflater().inflate(R.layout.evalu_component, null, false);
        Spinner levels = (Spinner)rowview.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.level, R.layout.support_simple_spinner_dropdown_item);
        levels.setAdapter(adapter);
        TextView content = (TextView)rowview.findViewById(R.id.content);
        content.setText(message);
        layout.addView(rowview);
    }

    public void switch_back(View view) {
        Intent act_action = new Intent(this, UserPage.class);
        startActivity(act_action);
    }

    public void submit(View view) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("order_number", order_num.getText().toString());
        contentValues.put("factory", factory.getText().toString());
        String inputs = loopViews(layout);
        contentValues.put("content", inputs);
        long ins = db.insert("Review", null, contentValues);
        if(ins == -1) Toast.makeText(getApplicationContext(), "Fail to add content to the database", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getApplicationContext(), inputs, Toast.LENGTH_SHORT).show();
    }

    private String loopViews(LinearLayout view) {
        String retval = "";
        for (int i = 0; i < view.getChildCount(); i++) {
            View v = view.getChildAt(i);
            TextView category = (TextView) v.findViewById(R.id.content);
            Spinner level = (Spinner) v.findViewById(R.id.spinner);
            EditText comments = (EditText) v.findViewById(R.id.inputbox);
            retval += Integer.toString(i+1) + ". " + category.getText() + "\nCondition Level: " + level.getSelectedItem().toString() + "\n Comments: " + comments.getText() + "\n";
        }
        return retval;
    }
}