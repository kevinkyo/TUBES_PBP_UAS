package com.kelompoka.tubes.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Student implements Serializable {
    @PrimaryKey(autoGenerate =  true)
    public int id;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="kelas")
    public String kelas;

    @ColumnInfo(name="age")
    public int age;

    @ColumnInfo(name="alamat")
    public String alamat;

    public Student() {
    }

    public Student(int id, String name, String kelas, int age, String alamat) {
        this.id = id;
        this.name = name;
        this.kelas = kelas;
        this.age = age;
        this.alamat = alamat;
    }

    public int getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getKelas() {
        return kelas;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public void setAlamat(String alamat) { this.alamat = alamat; }

}


