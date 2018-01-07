package com.example.justine.groceryguru;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class ViewListItem extends Activity {

    DatabaseHelper database;

    EditText input;
    Button add;
    Button delete;
    ListView list;

    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_list);

        database = new DatabaseHelper(this);

        input = findViewById(R.id._newItem);
        add = findViewById(R.id._btnAddItem);
        delete = findViewById(R.id._btnDltList);
        list = findViewById(R.id.listView2);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("ID", -1);
        selectedName = receivedIntent.getStringExtra("name");

        Cursor data = database.getListItemData(selectedID);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(2));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        list.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input != null) {
                    String item = input.getText().toString();
                    database.addListItem(item, selectedID);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.deleteTitleName(selectedID);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                Cursor data = database.getListItemId(name);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                Intent indvListIntent = new Intent(ViewListItem.this, IndividualItem.class);
                indvListIntent.putExtra("id", itemID);
                indvListIntent.putExtra("name", name);
                startActivity(indvListIntent);
            }
        });
    }

    public void onResume() {
        super.onResume();

        Cursor data = database.getListItemData(selectedID);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(2));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        list.setAdapter(adapter);
    }


}
