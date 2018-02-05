
/*
 * Copyright (c) $2.5.2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.yxiong4_subbook;

/**
 * Created by yxion on 2018-02-02.
 */

// This subscription class's main purpose is to create the subscription object which has
// its name, date, charge, comment and can get its name, date, charge and comment


import java.io.Serializable;

public class Subscript implements Serializable {
    //initialize the name,date,charge and comment
    private String name;
    private String date;
    private Float charge;
    private String comment;

    Subscript(String name,String date,Float charge){
        this.name = name;
        this.date = date;
        this.charge = charge;
    }

    Subscript(String name,String date,Float charge,String comment){
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;
    }
    // return subscription name
    public String getName(){
        return this.name;
    }

    // shows the information on the subscription in array list
    public String toString(){
        return "Name: "+this.name+"     "+"Date: "+this.date+"     "+"Charge: "+this.charge;
    }
    //get subscription's charge,date and comment
    public float getCharge(){
        return this.charge;
    }
    public String getDate(){
        return this.date;
    }
    public String getComment(){return this.comment;}
    // set subscription's name
    public void setName(String name){
        this.name = name;
    }
}
