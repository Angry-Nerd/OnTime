package com.akshit.ontime.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.akshit.ontime.models.SubjectDetails;

import java.util.List;

@Dao
public interface SubjectDao {

    @Query("SELECT * FROM subject_details WHERE semester_name=:sem_name")
    LiveData<List<SubjectDetails>> getSubjectDetailsBySemesterNumber(String sem_name);

    @Query("SELECT * FROM subject_details")
    LiveData<List<SubjectDetails>> getSubjectDetails();

    @Insert
    void insertSubject(SubjectDetails SubjectDetails);

    @Insert
    void insertSubjects(SubjectDetails... SubjectDetails);

    @Update
    void updateSubjectDetails(SubjectDetails SubjectDetails);

    @Query("DELETE FROM subject_details WHERE semester_name=:sem_name")
    void deleteSubjectDetailsForSemesterNumber(String sem_name);

    @Query("DELETE FROM subject_details")
    void deleteAllSubjects();


}
