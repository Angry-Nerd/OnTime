package com.akshit.ontime.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.akshit.ontime.models.SemesterDetails;
import com.akshit.ontime.room.repository.SemesterRepository;

import java.util.List;

public class SemesterDetailsViewModel extends AndroidViewModel {

    private SemesterRepository mSemesterRepository;
    private LiveData<List<SemesterDetails>> semesterDetails;

    public SemesterDetailsViewModel(Application application) {
        super(application);
        Log.d("akshiban", "SemesterDetailsViewModel: came here");
        mSemesterRepository = new SemesterRepository(application);
    }


    public void insert(SemesterDetails semesterDetails) {
        mSemesterRepository.insertSemester(semesterDetails);
    }

    public void update(SemesterDetails semesterDetails) {
        mSemesterRepository.updateSemesterDetails(semesterDetails);
    }

    public void delete(SemesterDetails semesterDetails) {
        mSemesterRepository.deleteSemesterDetails(semesterDetails);
    }

    public void deleteAll() {
        mSemesterRepository.deleteAllSemesters();
    }

    public void insertAll(SemesterDetails... semesterDetails) {
        mSemesterRepository.insertSemesters(semesterDetails);
    }

    public LiveData<List<SemesterDetails>> get() {
        return mSemesterRepository.getSemesterDetails();
    }

}
