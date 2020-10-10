package com.kelompoka.tubes.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kelompoka.tubes.model.Student;

@Database(entities = {Student.class}, version = 1)
public abstract class AppDatabase2 extends RoomDatabase {
    public abstract StudentDao studentDao();
}



