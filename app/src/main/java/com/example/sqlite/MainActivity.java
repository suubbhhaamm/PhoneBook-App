package com.example.sqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    EditText id,name,contact;
    Button insert,view,update,delete;
    TextView text;
    DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id=(EditText)findViewById(R.id.id);
        name=(EditText)findViewById((R.id.name));
        contact=(EditText)findViewById((R.id.contact));
        insert=(Button)findViewById(R.id.insert);
        view=(Button)findViewById(R.id.view);
        update=(Button)findViewById(R.id.update);
        delete=(Button)findViewById(R.id.delete);
        text=(TextView)findViewById(R.id.text);
        db=new DBHandler(getApplicationContext());
    }
    public void buttonAction(View view)
    {
        switch (view.getId()){
            case R.id.insert:
                Boolean check = db.insertRecord(name.getText().toString(), contact.getText().toString());

                if(check == true){
                    Toast.makeText(getApplicationContext(), "Record inserted successfully!",
                            Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Record could not be inserted!",
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.view:
                Cursor res = db.getRecords();
                if(res.getCount() == 0){
                    Toast.makeText(MainActivity.this,"No Data Available", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("ID : " + res.getString(0) + "\n");
                    buffer.append("Name : " + res.getString(1) + "\n");
                    buffer.append("Contact : " + res.getString(2) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Contact Details");
                builder.setMessage(buffer.toString());
                builder.show();
                break;

            case R.id.update:
                Boolean checkUp = db.updateRecord(id.getText().toString(),name.getText().toString(), contact.getText().toString());

                if(checkUp == true){
                    Toast.makeText(getApplicationContext(),"Record updated successfully!",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Record could not be updated!",
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.delete:
                Boolean checkDel = db.deleteRecord(id.getText().toString());

                if(checkDel == true) {
                    Toast.makeText(getApplicationContext(),"Record deleted successfully!",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Record could not be deleted!",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}

