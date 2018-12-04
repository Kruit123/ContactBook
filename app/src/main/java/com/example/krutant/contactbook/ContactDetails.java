package com.example.krutant.contactbook;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog.Builder;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactDetails extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText Name,Phone;
    private ListView lv;

    Button Add;
String firstname;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.listview);
        myDb = new DatabaseHelper(this);
        lv = (ListView) findViewById(R.id.lv1);
        Phone=(EditText)findViewById(R.id.Phone);
        Name=(EditText)findViewById(R.id.Name);
        Button Add = (Button)findViewById(R.id.ADD);

        Cursor res = myDb.getAllData();
   //     if(res.getCount() == 0) {
            // show message
    //        showMessage("Error","Nothing found");
           // return;
     //   }
        final List<String> array = new ArrayList<String>();
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            String Name = res.getString(0);
            array.add(Name);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, array);
        lv.setAdapter(adapter);
        Add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String selected = "";
                        int cntChoice = lv.getCount();
                        SparseBooleanArray sparseBooleanArray = lv.getCheckedItemPositions();
                        final List<String> store = new ArrayList<String>();
                        for(int i = 0; i < cntChoice; i++){
                            if(sparseBooleanArray.get(i)) {
                                store.add(lv.getItemAtPosition(i).toString()); //STORED HERE IN STRING
                                //selected += lv.getItemAtPosition(i).toString() + "\n";
                            }
                        }
                        //Toast.makeText(ContactDetails.this, selected, Toast.LENGTH_LONG).show();
                        String[] myArray = store.toArray(new String[store.size()]);
                        String seperate = "_,_";
                        String str = "";
                        for (int i=0;i<myArray.length;i++){
                            str = str + myArray[i];
                            if(i<myArray.length-1){
                                str = str + seperate;
                            }
                        }


                        boolean isInserted = myDb.insertData(Name.getText().toString(),
                                Phone.getText().toString(),
                                str);
                        if(isInserted == false)
                            Toast.makeText(ContactDetails.this,"Data not Inserted",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(ContactDetails.this, MainActivity.class);
                        startActivity(intent);
                        finish();



                    }
                }
        );


        /*
        db=openOrCreateDatabase("ContactDB", Context.MODE_PRIVATE, null);
       db.execSQL("CREATE TABLE IF NOT EXISTS contact(Name VARCHAR,Phone VARCHAR,Friends VARCHAR);");



        Cursor crs=db.rawQuery("SELECT * FROM contact", null);


        final List<String> array = new ArrayList<String>();
        final List<String> Phonom = new ArrayList<String>();
        final List<String> friends = new ArrayList<String>();
        while(crs.moveToNext()){
            String fname = crs.getString(crs.getColumnIndex("Name"));
            String phonen = crs.getString(crs.getColumnIndex("Phone"));
            String amigo = crs.getString(crs.getColumnIndex("Friends"));
            array.add(fname);
            friends.add(amigo);
            Phonom.add(phonen);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                array );

        lv.setAdapter(arrayAdapter);



        View.OnClickListener AddPhone = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstname = Name.getText().toString();
                String phonenum = Phone.getText().toString();
                String selected = "";

//VALUE OF CHECKBOXES
                int cntChoice = lv.getCount();
                SparseBooleanArray sparseBooleanArray = lv.getCheckedItemPositions();
                final List<String> store = new ArrayList<String>();
                for(int i = 0; i < cntChoice; i++){
                    if(sparseBooleanArray.get(i)) {
                        store.add(lv.getItemAtPosition(i).toString()); //STORED HERE IN STRING
                        //selected += lv.getItemAtPosition(i).toString() + "\n";
                    }
                }
               Toast.makeText(ContactDetails.this, selected, Toast.LENGTH_LONG).show();
                String[] myArray = store.toArray(new String[store.size()]);
                 String seperate = "_,_";
                String str = "";
                for (int i=0;i<myArray.length;i++){
                    str = str + myArray[i];
                    if(i<myArray.length-1){
                    str = str + seperate;
                        }
                    }
                friends.add(str);
                String[] Fnd = new String[friends.size()];
                Fnd = friends.toArray(Fnd);
               // Toast.makeText(ContactDetails.this, str, Toast.LENGTH_LONG).show();

                db.execSQL("INSERT INTO contact VALUES('"+Name.getText()+"','"+ Phone.getText()+"','"+str+"');");

               // db.execSQL("INSERT INTO contact VALUES('"+Name.getText()+ "','"+Phone.getText()+"' );");
                Builder builder=new Builder(ContactDetails.this);
                builder.setCancelable(true);
                builder.setTitle("Success");
                builder.setMessage("Added");
                builder.show();

                array.add(Name.getText().toString());
                Intent new_intent=new Intent(ContactDetails.this,MainActivity.class);

               //new_intent.putExtra("counter",Name.getText().toString()); //here the value is integer so you use the  new_intent.putExtra(String name,int value)
                String[] Hello = new String[array.size()];
                String[] Phono = new String[array.size()];
                String[] Amigo = new String[array.size()];
               Hello = array.toArray(Hello);
                Phono = Phonom.toArray(Phono);
                Amigo = friends.toArray(Amigo);
                new_intent.putExtra("counter",Hello);
                new_intent.putExtra("phonen",Phono);
                new_intent.putExtra("amigon",Amigo);
                new_intent.putExtra("counter2",Fnd);
                startActivity(new_intent);
                finish();
               // clearText();
            }
        };





        View.OnClickListener View = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c=db.rawQuery("SELECT * FROM contact", null);


                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("Name: "+c.getString(0)+"\n");
                    buffer.append("Phone: "+c.getString(1)+"\n");
                }

                Builder builder=new Builder(ContactDetails.this);
                builder.setCancelable(true);
                builder.setTitle("Details");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        };

        Add.setOnClickListener(AddPhone);
        Show.setOnClickListener(View);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String hello = extras.getString("hey"); //CONTINUE FRO HERE
            Toast.makeText(getBaseContext(), hello, Toast.LENGTH_LONG).show();
             }

*/
    }


    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }




}
