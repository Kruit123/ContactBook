package com.example.krutant.contactbook;

import android.app.AlertDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Text;


public class adding2 extends Fragment {
DatabaseHelper myDb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.listview2, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        myDb = new DatabaseHelper(getActivity());
        final ListView lv1 = (ListView)getView().findViewById(R.id.lv1);
        final List<String> array = new ArrayList<String>();
        Cursor res = myDb.getAllData();
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            String Name = res.getString(0);
            array.add(Name);
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice, array);
        lv1.setAdapter(adapter);
         final EditText Phone=(EditText)getView().findViewById(R.id.Phone);
        final EditText Name=(EditText)getView().findViewById(R.id.Name);
        ((Button)getView().findViewById(R.id.ADD)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selected = "";
                int cntChoice = lv1.getCount();
                SparseBooleanArray sparseBooleanArray = lv1.getCheckedItemPositions();
                final List<String> store = new ArrayList<String>();
                for(int i = 0; i < cntChoice; i++){
                    if(sparseBooleanArray.get(i)) {
                        store.add(lv1.getItemAtPosition(i).toString()); //STORED HERE IN STRING
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

                String Names = Name.getText().toString();
                String Phones = Phone.getText().toString();
                boolean isInserted = myDb.insertData(Names,
                        Phones,
                        str);

                if(isInserted == true)
                    Toast.makeText(getActivity(),"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(),"Data not Inserted",Toast.LENGTH_LONG).show();

                Toast.makeText(getActivity(), "submit clicked", Toast.LENGTH_LONG).show();
            }
        });


        Bundle bundle = this.getArguments();
        if (bundle!=null){
            String x = bundle.getString("hey", null);
            Toast.makeText(getActivity(), "submit clicked", Toast.LENGTH_LONG).show();
        }

    }



}