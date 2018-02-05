

/*
 * Copyright (c) $2.5.2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */


// Discussed some methods with Wyang2 and references some methods on Google and
// LonelyTwitter from lab


// This main class's main work including shows the main screen of list of subscriptions,
// allow user the add the new subscription after clicking add button
// allow user to edit the existing subscription after clicking subscription in list
// shows the total charge of the subscriptions

package com.example.yxiong4_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.List;

import com.example.yxiong4_subbook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




public class MainActivity extends AppCompatActivity {
    //initialize listview,arrayadapter,filename and arraylist
    private ListView subList;
    private ArrayAdapter adapter;
    private static final String FILENAME = "subBook";
    public static ArrayList<Subscript> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize some variable when the main screen shows to the user
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set the new array adapter to contain the arralist of subscriptions
        adapter = new ArrayAdapter<Subscript>(this, android.R.layout.simple_list_item_1, arrayList);
        Log.d("testfile","558");
        subList = (ListView) findViewById(R.id.subList);
        //set the subscription list to connect with the button
        subList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get the new intent when listview been clicked
                Intent update = new Intent(MainActivity.this, UpdateActivity.class);
                Subscript subscript = arrayList.get(i);
                //send information of subscription to update activity from main activity
                update.putExtra("name", subscript.getName());
                update.putExtra("date", subscript.getDate());
                update.putExtra("charge", subscript.getCharge());
                update.putExtra("comment", subscript.getComment());
                update.putExtra("index", i);
                //start new activity and set the requestcode to 2 to get the corresponding result
                //for updating subscription
                startActivityForResult(update, 2);
            }
        });}
    //start the new activity and set requestcode to 1 for adding new subscription
    public void addNew(View view) {
        Intent startNewActivity = new Intent(MainActivity.this, SubActivity.class);
        startActivityForResult(startNewActivity, 1);
    }

    @Override
    //get the result from activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if it is from add new subscription function
        if (requestCode == 1 && resultCode == RESULT_OK) {
            subList = (ListView) findViewById(R.id.subList);
            subList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            saveInFile();
        // if it is from update subscription function
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            subList = (ListView) findViewById(R.id.subList);
            subList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            saveInFile();
        // if it is sfrom delete subscription function
        } else if (requestCode == 2 && resultCode == RESULT_CANCELED) {
            subList = (ListView) findViewById(R.id.subList);
            subList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            saveInFile();
        }
    }
    protected void onStart() {
        // set what happens after on create
        super.onStart();
        Log.i("LifeCycle --->", "onStart is called");
        // load the file stored in machine
        loadFromFile();
        adapter = new ArrayAdapter<Subscript>(this, android.R.layout.simple_list_item_1, arrayList);
        subList.setAdapter(adapter);
        // get the size of array list
        Integer size = arrayList.size();
        float sum = 0;
        // calculate the total charge of subscriptions
        for (Integer x = size; x > 0; x--) {
            Subscript subscript = arrayList.get(x - 1);
            float tem_charge = subscript.getCharge();
            sum = sum + tem_charge;
        }
        // set the total charge to the textview of sum
        TextView textView = findViewById(R.id.sum);
        String sum2 = Float.toString(sum);
        textView.setText(sum2);
    }
    // load file method using gson
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Subscript>>(){}.getType();
            arrayList = gson.fromJson(in, listType);
        //handle exception if no file can found
        } catch (FileNotFoundException e) {
            arrayList = new ArrayList<Subscript>();
        // catch exception of the other cituation
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    //save file method using gson
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(arrayList, out);
            out.flush();
        //handle exception
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        //handle exception
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}