package com.akshit.ontime.room.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.akshit.ontime.models.SemesterDetails;
import com.akshit.ontime.room.dao.SemesterDAO;

@Database(entities = SemesterDetails.class, version = 1)
public abstract class SemesterDatabase extends RoomDatabase {


    public static final String DATABASE_NAME = "SEMESTER_DETAILS";

    public abstract SemesterDAO semesterDAO();

    private static SemesterDatabase semesterDatabase;

    public synchronized static SemesterDatabase getInstance(Context context) {
        if (semesterDatabase == null) {
            semesterDatabase = Room.databaseBuilder(context, SemesterDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return semesterDatabase;
    }



}
