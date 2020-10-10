package com.kelompoka.tubes.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient databaseClient;

    private AppDatabase database;
    private AppDatabase2 database2;

    private DatabaseClient(Context context){
        this.context = context;
        database = Room.databaseBuilder(context, AppDatabase.class, "guru").build();
        database2 = Room.databaseBuilder(context, AppDatabase2.class, "student").build();
    }

    public static synchronized DatabaseClient getInstance(Context context){
        if (databaseClient==null){
            databaseClient = new DatabaseClient(context);
        }
        return databaseClient;
    }

    public AppDatabase getDatabase(){
        return database;
    }

    public AppDatabase2 getDatabase2(){
        return database2;
    }
}


