package com.example.justine.groceryguru;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Asus on 2018-01-04.
 */

public class AddList extends Activity {

    DatabaseHelper database;

    EditText listname;
    Button addEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page);

        listname = findViewById(R.id._txtName);
        addEntry = findViewById(R.id._btnAddEntry);

        database = new DatabaseHelper(this);

        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listname != null) {
                    database.addTitle(listname.getText().toString());
                    Intent returnIntent = new Intent(AddList.this, MainActivity.class);
                    startActivity(returnIntent);
                }
            }
        });

    }

}
