package com.kelompoka.tubes.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Guru implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "number")
    public String number;

    @ColumnInfo(name = "full_name")
    public String fullName;

    @ColumnInfo(name = "age")
    public int age;

    public Guru() {
    }

    public Guru(int id, String number, String fullName, int age) {
        this.id = id;
        this.number = number;
        this.fullName = fullName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}


