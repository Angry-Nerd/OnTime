package com.akshit.ontime.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.akshit.ontime.models.SubjectDetails;
import com.akshit.ontime.room.repository.SubjectRepository;

import java.util.List;

public class SubjectDetailsViewModel extends AndroidViewModel {


    private SubjectRepository mSubjectRepository;
    private LiveData<List<SubjectDetails>> subjectDetails;

    public SubjectDetailsViewModel(@NonNull Application application, @NonNull String semName) {
        super(application);
        mSubjectRepository = new SubjectRepository(application, semName);
        subjectDetails = mSubjectRepository.getSubjectDetails();
    }


    public void insert(SubjectDetails subjectDetails) {
        mSubjectRepository.insertSubject(subjectDetails);
    }

    public void update(SubjectDetails subjectDetails) {
        mSubjectRepository.updateSubjectDetails(subjectDetails);
    }

    public void deleteForSemName(String semName) {
        mSubjectRepository.deleteSubjectDetails(semName);
    }

    public void deleteAll() {
        mSubjectRepository.deleteAllSubjects();
    }

    public void insertAll(SubjectDetails... subjectDetails) {
        mSubjectRepository.insertSubjects(subjectDetails);
    }

    public LiveData<List<SubjectDetails>> get() {
        return subjectDetails;
    }
}
