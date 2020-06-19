package com.akshit.ontime.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.akshit.ontime.models.SubjectDetails;
import com.akshit.ontime.room.dao.SubjectDao;

@Database(entities = SubjectDetails.class, version = 1)
public abstract class SubjectDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "SUBJECT_DETAILS";
    private static SubjectDatabase subjectDatabase;

    public synchronized static SubjectDatabase getInstance(Context context) {
        if (subjectDatabase == null) {
            subjectDatabase = Room.databaseBuilder(context, SubjectDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return subjectDatabase;
    }

    public abstract SubjectDao subjectDao();
}
