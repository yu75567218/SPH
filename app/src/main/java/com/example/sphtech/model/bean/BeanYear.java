package com.example.sphtech.model.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "year", unique = true)})
public class BeanYear {
    @PrimaryKey
    @NonNull
    public String year;
    public double total;
    public String quarterData1;
    public String quarterData2;
    public String quarterData3;
    public String quarterData4;
    public boolean isReduceQ1;
    public boolean isReduceQ2;
    public boolean isReduceQ3;
    public boolean isReduceQ4;
}
