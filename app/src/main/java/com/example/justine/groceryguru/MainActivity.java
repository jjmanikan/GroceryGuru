package com.example.justine.groceryguru;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    DatabaseHelper database;

    Button addList;
    ListView list;
    ListAdapter adapter;

    public static final int MY_NOTIFICATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addList = findViewById(R.id._btnAdd);
        list = findViewById(R.id.listView);

        database = new DatabaseHelper(this);

        Cursor data = database.getTitleData();
        ArrayList<String> titleData = new ArrayList<>();
        while (data.moveToNext()) {
            titleData.add(data.getString(2));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titleData);
        list.setAdapter(adapter);

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addListItemIntent = new Intent(MainActivity.this, AddList.class);
                startActivity(addListItemIntent);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();

                Cursor data = database.getTitleItemId(name);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }

                Intent viewListIntent = new Intent(MainActivity.this, ViewListItem.class);
                viewListIntent.putExtra("ID", itemID);
                viewListIntent.putExtra("name", name);
                startActivity(viewListIntent);
            }
        });
    }

    public void onResume() {
        super.onResume();

        Cursor data = database.getTitleData();
        ArrayList<String> titleData = new ArrayList<>();
        while (data.moveToNext()) {
            titleData.add(data.getString(2));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titleData);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.action_notify:
                //if(adapter.getCount() == 0) {
                    final EditText hoursEditText = new EditText(this);
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("Notify me in: (hours)")
                            .setView(hoursEditText)
                            .setPositiveButton("Notify", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int hours = Integer.parseInt(hoursEditText.getText().toString());

                                    scheduleNotification(buildNotification("Please complete complete your lists"), hours);

                                }


                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();
                    return true;
                //}
                //else {
                   // Toast.makeText(this, "Create/Fill a Grocery List", Toast.LENGTH_LONG).show();
                    //break;
               // }

            case R.id.action_delete_lists:
                Toast.makeText(this, "Unimplemented", Toast.LENGTH_LONG).show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public Notification buildNotification(String content) {

        Notification.Builder notification = new Notification.Builder(this);
        notification.setSmallIcon(R.drawable.ic_launcher_background);
        notification.setTicker("Notification");
        notification.setContentTitle("You have pending Grocery Lists");
        notification.setContentText(content);

        return notification.build();


    }

    //not used yet
    public void cancelNotification() {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.cancel(MY_NOTIFICATION);

    }

    public void scheduleNotification(Notification notification, int hours){
        Intent schedIntent = new Intent(this, NotificationBroadcast.class);
        schedIntent.putExtra(NotificationBroadcast.MY_NOTIFICATION, MY_NOTIFICATION);
        schedIntent.putExtra(NotificationBroadcast.NOTIFICATION, notification);

        long time = SystemClock.elapsedRealtime() + hours;
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        PendingIntent notifyPIntent = PendingIntent.getActivity(getApplicationContext(), 0, schedIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, time, notifyPIntent);
    }
}
