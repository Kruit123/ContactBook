package com.example.krutant.contactbook;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
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


public class adding extends ListFragment {

    DatabaseHelper myDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.listitemsinfo, container, false);
        final ListView lv1 = (ListView)view.findViewById(android.R.id.list);
        myDb = new DatabaseHelper(getActivity());
        Cursor res = myDb.getAllData();
        final List<String> array = new ArrayList<String>();
        while (res.moveToNext()) {
            String Name = res.getString(0);
            array.add(Name);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, array);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adding fragment = new adding();
                  String item = ((TextView) view).getText().toString();
                    Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("hey",item);
                    fragment.setArguments(bundle);

            }

        });

        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long arg3) {
                String item2 = ((TextView) view).getText().toString();
                myDb.deleteData(item2);
                Cursor res = myDb.getAllData();
                array.remove(position);
                adapter.notifyDataSetChanged();



                return true;
            }

        });

    //    ((Button)view.findViewById(R.id.ADD)).setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View view) {
    //            Toast.makeText(getActivity(), "submit clicked", Toast.LENGTH_LONG).show();
    //        }
    //    });




        return view;
    }




}