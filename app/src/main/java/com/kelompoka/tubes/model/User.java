package com.kelompoka.tubes.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class User implements Serializable {

    @ColumnInfo(name="nama")
    public String nama;

    public User() {
    }

    public User(String nama){
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}