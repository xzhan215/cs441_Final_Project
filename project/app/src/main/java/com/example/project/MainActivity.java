package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MyDatabaseHelper dbHelper;
    EditText username, password;
    Button login_b;
    Spinner user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this);
        username = (EditText) findViewById(R.id.usr);
        password = (EditText) findViewById(R.id.pw);
        user_type = (Spinner) findViewById(R.id.type);
        login_b = (Button) findViewById(R.id.login);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.usertype, R.layout.support_simple_spinner_dropdown_item);
        user_type.setAdapter(adapter);
    }

    public void switch_activities(View view){
        String email = username.getText().toString();
        String ps = password.getText().toString();
        String type = user_type.getSelectedItem().toString();
        boolean pairExist = find_pair(email, ps, type);
        if(pairExist){
            if (type.equals("admin")) {
                Intent act_action = new Intent(this, AdminPage.class);
                startActivity(act_action);
            } else if (type.equals("user")) {
                Intent act_action = new Intent(this, UserPage.class);
                startActivity(act_action);
            }
        }else{
            Toast.makeText(getApplicationContext(), "Username is not exited or incorrect password", Toast.LENGTH_SHORT).show();
        }
    }

    public void switch_register(View view){
        Intent act_action = new Intent(this, Register.class);
        startActivity(act_action);
    }

    public boolean find_pair(String email, String password, String type) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where email=? and password=? and type=?", new String[] {email, password, type});
        if(cursor.getCount()>0) return true;
        else return false;
    }
}