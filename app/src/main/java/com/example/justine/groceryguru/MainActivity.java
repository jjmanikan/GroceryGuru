package com.example.justine.groceryguru;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button addList;
    ListView list;
    ListView subList;
    //ArrayList<String> listItems;
    ArrayList<ArrayList<String>> dataMatrix;
    ArrayList<String> row;
    ArrayList<String> titles;
    //ArrayList<String> subListItems;
    ArrayAdapter<String> adapter;
    //ArrayAdapter<String> subAdapter;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String urlTxt;
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
        subList = findViewById(R.id.listView2);

        urlTxt = "http://hazzarja.dev.fast.sheridanc.on.ca/list_data.json";

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        row = new ArrayList<>();
        titles = new ArrayList<>();
        //subListItems = new ArrayList<>
        autoPopulate();


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
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
                /*Intent intent = new Intent(
                        getApplicationContext(),
                        ViewListItem.class
                );*/
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ViewListItem.class);
                intent.putExtra("position", position);

                startActivity(intent);

                //startActivity(intent);
                //startActivityForResult(intent, VIEW_ACTIVITY);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        String title = data.getStringExtra("title");
        titles.add(title);

        if (requestCode == DETAIL_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {

                //String title = data.getStringExtra("title");
                //titles.add(title);

            }
        }

        if (requestCode == VIEW_ACTIVITY){
            if (requestCode == Activity.RESULT_OK){

            }
        }
    }

    public void autoPopulate(){

        /*ViewListItem pg0;
        ViewListItem pg1;
        ViewListItem pg2;
        ViewListItem pg3;
        ViewListItem pg4;
        ViewListItem pg5;
        ViewListItem pg6;
        ViewListItem pg7;
        ViewListItem pg8;
        ViewListItem pg9;*/

        titles.add("Groceries");
        titles.add("Clothes");
        titles.add("Electronics");
        /*titles.add("");
        titles.add("");
        titles.add("");
        titles.add("");
        titles.add("");
        titles.add("");
        titles.add("");*/
    }

    public void loadWebJson(View view){

        ProcessTask process = new ProcessTask();
        process.execute(urlTxt);

    }

    private class ProcessTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL(strings[0]);
                URLConnection connect = url.openConnection();
                HttpURLConnection httpConnect = (HttpURLConnection) connect;

                int responseCode = httpConnect.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    InputStream is = httpConnect.getInputStream();
                    Scanner scanner = new Scanner(is);
                    StringBuilder builder = new StringBuilder();

                    while (scanner.hasNextLine()) {
                        builder.append(scanner.nextLine());
                    }

                    return parseJsonFile(builder.toString());

                } else {
                    return "Http Error";
                }
            } catch (Exception e) {
                return e.toString();
            }
        }

        private String parseJsonFile(String s) {

            //StringBuilder builder = new StringBuilder();

            try {
                JSONObject wrapper = new JSONObject(s);
                JSONObject listData = wrapper.getJSONObject("list-data");

                JSONArray groceries = listData.getJSONArray("groceries");
                JSONArray clothes = listData.getJSONArray("clothes");
                JSONArray electronics = listData.getJSONArray("electronics");


                for (int i = 0; i < groceries.length(); i++) {
                    JSONObject defaultOne = groceries.getJSONObject(i);
                    //builder.append(defaultOne.getString("name")).append(": ").append(defaultOne.getString("number")).append("\n");
                    row.add(defaultOne.getString("name"));
                }
                dataMatrix.add(row);
                row.clear();
                for (int i = 0; i < clothes.length(); i++) {
                    JSONObject defaultTwo = clothes.getJSONObject(i);
                    //builder.append(defaultOne.getString("name")).append(": ").append(defaultOne.getString("number")).append("\n");
                    row.add(defaultTwo.getString("name"));
                }
                dataMatrix.add(row);
                row.clear();
                for (int i = 0; i < electronics.length(); i++) {
                    JSONObject defaultThree = electronics.getJSONObject(i);
                    //builder.append(defaultOne.getString("name")).append(": ").append(defaultOne.getString("number")).append("\n");
                    row.add(defaultThree.getString("name"));
                }
                dataMatrix.add(row);
                row.clear();

                //return builder.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s){

            //dataTxt.setText(s);

        }

    }
}
