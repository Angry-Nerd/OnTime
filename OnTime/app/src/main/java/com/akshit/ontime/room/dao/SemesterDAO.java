package com.akshit.ontime.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.akshit.ontime.models.SemesterDetails;

import java.util.List;

@Dao
public interface SemesterDAO {

    @Query("SELECT * FROM semester_details WHERE semester_number=:sem_num")
    SemesterDetails getSemesterDetailsBySemester(int sem_num);

    @Query("SELECT * FROM semester_details")
    LiveData<List<SemesterDetails>> getSemesterDetails();

    @Insert
    void insertSemester(SemesterDetails semesterDetails);

    @Insert
    void insertSemesters(SemesterDetails...semesterDetails);

    @Update
    void updateSemesterDetails(SemesterDetails semesterDetails);

    @Delete
    void deleteSemesterDetails(SemesterDetails semesterDetails);

    @Query("DELETE FROM semester_details")
    void deleteAllSemesters();
}
