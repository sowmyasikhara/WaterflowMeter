package com.example.waterflowmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GetStatusActivity extends AppCompatActivity {

    ListView listViews ;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_status);



        Bundle bundle = getIntent().getExtras();

        ArrayList arraylist = bundle.getParcelableArrayList("lists");
        listViews = (ListView) findViewById(R.id.listview1);
        adapter = new ArrayAdapter<String>(this,R.layout.listview_layout,arraylist);
        listViews.setAdapter(adapter);



    }
}