package com.example.krutant.contactbook;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ListActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class MainActivity extends FragmentActivity {
    /** Items entered by the user is stored in this ArrayList variable */
    ArrayList list = new ArrayList();
    /** Declaring an ArrayAdapter to set items to ListView */
    ArrayAdapter adapter;
    ArrayAdapter adapter2;
    DatabaseHelper myDb;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            myDb = new DatabaseHelper(this);
            final String[] x;


            Button btn = (Button) findViewById(R.id.btnAdd);

            final ListView lv1 = (ListView) findViewById(android.R.id.list);
            Button btnDel = (Button) findViewById(R.id.btnDel);

            /** Defining the ArrayAdapter to set items to ListView */
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, list);


            //   Intent intent = getIntent();
            // String[] myStrings = intent.getStringArrayExtra("counter");
            // List<String> list = Arrays.asList(myStrings);

            Cursor res = myDb.getAllData();
            if (res.getCount() == 0) {
                // show message
                showMessage("Oops!", "No Contacts Added Yet");
                //return;
            }
            final List<String> array = new ArrayList<String>();
            final List<String> Phonom = new ArrayList<String>();
            final List<String> friends = new ArrayList<String>();
            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {

                String Name = res.getString(0);
                String Phone = res.getString(1);
                String Friend = res.getString(2);
                array.add(Name);
                friends.add(Name);

            }
            adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, friends);
            adapter2.notifyDataSetChanged();

            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    String item = ((TextView) view).getText().toString();
                    //    Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                    Intent new_intent = new Intent(MainActivity.this, contactpage.class);
                    new_intent.putExtra("name", item);
                    startActivity(new_intent);

                }

            });




     /*
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            x = extras.getStringArray("counter"); //CONTINUE FRO HERE
            String[] y = extras.getStringArray("counter2");
            String[] phonen = extras.getStringArray("phonen");
            final String[] amigon = extras.getStringArray("amigon");

            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(x));
            adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, arrayList);
            adapter2.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), amigon[2],
                    Toast.LENGTH_SHORT).show();
            // list.add(x);
            //   adapter.notifyDataSetChanged();

        }

*/

            /** Defining a click event listener for the button "Add" */
            OnClickListener listener = new OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(MainActivity.this, ContactDetails.class);
                    startActivity(intent);

                }
            };

            lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long arg3) {
                    String item2 = ((TextView) view).getText().toString();
                    myDb.deleteData(item2);
                    Cursor res = myDb.getAllData();
                    friends.remove(position);
                    adapter2.notifyDataSetChanged();



                    return true;
                }

            });




            /** Setting the event listener for the add button */
            btn.setOnClickListener(listener);



            lv1.setAdapter(adapter2);
            /** Setting the adapter to the ListView */
            //setListAdapter(adapter);
            //setListAdapter(adapter2);
        }



    }






    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }





}
