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

    /*private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;

    private static final String DB_NAME="lists.db";
    private static final int DB_VERSION = 1;

    private static final String LISTS_TABLE = "Lists";

    private static final String  = "_id";
    private static final int ID_COLUMN = 0;*/

    EditText listname;
    Button addEntry;
    Button previous;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page);

        listname = findViewById(R.id._txtName);
        addEntry = findViewById(R.id._btnAddEntry);
        previous = findViewById(R.id._btnViewLists);

        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = listname.getText().toString();
                Intent data = new Intent();
                data.putExtra("title", name);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

    }

}
