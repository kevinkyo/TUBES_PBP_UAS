package com.kelompoka.tubes.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kelompoka.tubes.model.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM Student")
    List<Student> getAll();

    @Insert
    void insert(Student student);

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);
}


