package com.example.justine.groceryguru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button addList;
    ListView list;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    private static final int DETAIL_ACTIVITY = 2;
    private static final int VIEW_ACTIVITY = 3;
    private int pos;

    public void setPos(int num){this.pos = num;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addList = findViewById(R.id._btnAdd);
        list = findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);

        setPos(0);

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddList.class);
                startActivityForResult(intent, DETAIL_ACTIVITY);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        ViewListItem.class
                );

                startActivityForResult(intent, VIEW_ACTIVITY);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == DETAIL_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {

                String title = data.getStringExtra("title");

            }
        }

        if (requestCode == VIEW_ACTIVITY){
            if (requestCode == Activity.RESULT_OK){

            }
        }
    }
}
