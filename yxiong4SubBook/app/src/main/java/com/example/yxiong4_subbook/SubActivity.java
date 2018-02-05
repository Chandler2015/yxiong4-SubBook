


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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static com.example.yxiong4_subbook.MainActivity.arrayList;


// This subactivity's main purpose is to guide user to add new subscription, and save the information
// to main screen of the subscription list after clicking the finish button


public class SubActivity extends AppCompatActivity {
    //initialize the editexts and file name
    EditText name;
    EditText charge;
    EditText date;
    EditText comment;
    private static final String FILENAME = "subBook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        //find name,charge,date,comment editexts by ids
        name = findViewById(R.id.name);
        charge = findViewById(R.id.charge);
        date = findViewById(R.id.date);
        comment = findViewById(R.id.comment);
    }
    //save file method using gson
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

    //set the mehthod to the main screen when finish button being clicked
    public void SendMeHome(View view){
        Intent intent = new Intent();
        // get name,date,charge from edittext
        String Subname = name.getText().toString();
        String Subdate = date.getText().toString();
        String Subcharge = charge.getText().toString();
        // force user to enter non empty name,date and charge
        if (Subname.matches("")) {
            Toast.makeText(this, "Name can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (Subdate.matches("")) {
            Toast.makeText(this, "Date can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (Subcharge.matches("")) {
            Toast.makeText(this, "Charge can not be empty", Toast.LENGTH_SHORT).show();
            return;}
        Date date = null;
        //force user to enter date like formate "yyyy-mm-dd"
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            date = sdf.parse(Subdate);
            System.out.println(date);
            if (!Subdate.equals(sdf.format(date)))
            {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            Toast.makeText(this,"Date should formated as yyyy-mm-dd",Toast.LENGTH_SHORT).show();
            return;
        }
        //get the comment
        String Subcomment = comment.getText().toString();
        //convnert the charge from string to float
        Float subcharge = Float.parseFloat(Subcharge);
        Subscript subscript = new Subscript(Subname,Subdate,subcharge,Subcomment);
        //add the subscription user added to the arraylist
        arrayList.add(subscript);
        setResult(RESULT_OK,intent);
        //save file
        saveInFile();
        // close the adding page and back to main screen
        finish();
    }

}