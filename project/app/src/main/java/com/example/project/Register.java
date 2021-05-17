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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    MyDatabaseHelper dbHelper;
    EditText username, password1, password2;
    Button register_B;
    Spinner user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new MyDatabaseHelper(this);
        username = (EditText) findViewById(R.id.usr);
        password1 = (EditText) findViewById(R.id.pw1);
        password2 = (EditText) findViewById(R.id.pw2);
        register_B = (Button) findViewById(R.id.reg);
        user_type = (Spinner) findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.usertype, R.layout.support_simple_spinner_dropdown_item);
        user_type.setAdapter(adapter);
    }

    public void registered(View view){
        String email = username.getText().toString();
        String ps1 = password1.getText().toString();
        String ps2 = password2.getText().toString();
        String type = user_type.getSelectedItem().toString();
        username.setText("");
        password1.setText("");
        password2.setText("");
        if(email.equals("") || ps1.equals("") || ps2.equals("")) {
            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
            if(ps1.equals(ps2)) {
                boolean ckemail = check_email(email);
                if(ckemail) {
                    boolean inserted = insert(email,ps1,type);
                    if(inserted == true)
                        Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "Passwords are not equal", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void switch_login(View view){
        Intent act_action = new Intent(this, MainActivity.class);
        startActivity(act_action);
    }

    public boolean insert(String email, String password, String type) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("type", type);
        long ins = db.insert("User", null, contentValues);
        if(ins == -1) return false;
        else return true;
    }

    public boolean check_email(String myemail) {
        SQLiteDatabase db =  dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where email=?", new String[] {myemail});
        if(cursor.getCount()>0) return false;
        else return true;
    }

}