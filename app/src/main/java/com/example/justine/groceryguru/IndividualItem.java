package com.example.justine.groceryguru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Asus on 2018-01-06.
 */

public class IndividualItem extends Activity {

    DatabaseHelper database;

    TextView indvItem;
    Button delete;

    private String selectedListItem;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);

        database = new DatabaseHelper(this);

        indvItem = findViewById(R.id._txtIndvItem);
        delete = findViewById(R.id._btnDlt);

        Intent receivedIntent = getIntent();
        selectedListItem = receivedIntent.getStringExtra("name");
        selectedID = receivedIntent.getIntExtra("id", -1);

        indvItem.setText(selectedListItem);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.deleteListItemName(selectedID);
            }
        });

    }

}
