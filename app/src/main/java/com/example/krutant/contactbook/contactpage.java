package com.example.krutant.contactbook;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.database.Cursor;
import java.util.ArrayList;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import java.util.Arrays;
import java.util.List;
import android.database.sqlite.SQLiteDatabase;
import android.app.AlertDialog.Builder;
import android.util.SparseBooleanArray;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class contactpage extends AppCompatActivity {
//
DatabaseHelper myDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactpage);



        myDb = new DatabaseHelper(this);
       final TextView name = (TextView) findViewById(R.id.subname);
        final TextView phone = (TextView) findViewById(R.id.subphone);
        final ListView lv1 = (ListView) findViewById(R.id.lv1);
final String Name;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Name = extras.getString("name"); //CONTINUE FRO HERE
           // Toast.makeText(getBaseContext(), Name, Toast.LENGTH_LONG).show();


            Cursor res = myDb.Search(Name);
            if (res.getCount() == 0) {
                // show message
                showMessage("Error", "Nothing found");
                //return;
            }

           StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {
                String fName =  res.getString(0);
                String Phone= res.getString(1);
                String Friend = res.getString(2);
                name.setText(fName);
                phone.setText(Phone);
                String str = "_,_";
                String[] arr = Friend.split(str);
                List<String> list = Arrays.asList(arr);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        list );
                lv1.setAdapter(arrayAdapter);
            }
        }

        lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView)view).getText().toString();
                Cursor res = myDb.Search(item);
                while (res.moveToNext()) {
                    String fName =  res.getString(0);
                    String Phone= res.getString(1);
                    String Friend = res.getString(2);
                    name.setText(fName);
                    phone.setText(Phone);
                    String str = "_,_";
                    String[] arr = Friend.split(str);
                    final List<String> list = Arrays.asList(arr);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            contactpage.this,
                            android.R.layout.simple_list_item_1,
                            list );
                    lv1.setAdapter(arrayAdapter);
                }


            }

        });

    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
