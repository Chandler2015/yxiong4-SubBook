/*
 * Copyright (c) $2.5.2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.yxiong4_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yxiong4_subbook.R;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.example.yxiong4_subbook.MainActivity.arrayList;

// This update activity's main purpose is to help user to edit or delete the subscription created


public class UpdateActivity extends AppCompatActivity {
    // initialize the filename
    private static final String FILENAME = "subBook";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent i = getIntent();
        // set the bundle to get the information of subscription and passed by main activity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //get the information by id and set them to edittext of this activity
            EditText name2 = findViewById(R.id.name2);
            EditText date2 = findViewById(R.id.date2);
            EditText charge2 = findViewById(R.id.charge2);
            EditText comment2 = findViewById(R.id.comment2);
            name2.setText(bundle.getString("name"));
            date2.setText(bundle.getString("date"));
            Float newcharge = bundle.getFloat("charge");
            String newCharge = Float.toString(newcharge);
            charge2.setText(newCharge);
            comment2.setText(bundle.getString("comment"));
        }

    }
    public void saveUpdate(View view){
        // find the name,date,charge,comment by ids
        EditText name2 = findViewById(R.id.name2);
        EditText date2 = findViewById(R.id.date2);
        EditText charge2 = findViewById(R.id.charge2);
        EditText comment2 = findViewById(R.id.comment2);
        Intent intent = new Intent();
        // convert text to string
        String Subname = name2.getText().toString();
        String Subdate = date2.getText().toString();
        Float Subcharge = Float.parseFloat(charge2.getText().toString());
        String subcharge = Float.toString(Subcharge);

        // force the user not to make name,date and charge empty
        if (Subname.matches("")) {
            Toast.makeText(this, "Name can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (Subdate.matches("")) {
            Toast.makeText(this, "Date can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (subcharge.matches("")) {
            Toast.makeText(this, "Charge can not be empty", Toast.LENGTH_SHORT).show();
            return;}

        //get the position of the subscription in the list
        Bundle bundle = getIntent().getExtras();
        int index = bundle.getInt("index");
        String Subcomment = comment2.getText().toString();
        // set the new subscription
        Subscript subscript = new Subscript(Subname,Subdate,Subcharge,Subcomment);
        // put new subscription to instead the old one
        arrayList.set(index,subscript);
        // save file
        saveInFile();
        setResult(RESULT_OK,intent);
        //close and back to main screen
        finish();}
    //delete function
    public void DeleteIt(View view){
        Bundle bundle = getIntent().getExtras();
        // get the position of subscription in list
        int index = bundle.getInt("index");
        // remove the subscription from list
        arrayList.remove(index);
        Intent intent = new Intent();
        //set result to main activiity
        setResult(RESULT_CANCELED,intent);
        //save file
        saveInFile();
        //close and back to main screen
        finish();
    }
    // save file function
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(arrayList, out);
            out.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
