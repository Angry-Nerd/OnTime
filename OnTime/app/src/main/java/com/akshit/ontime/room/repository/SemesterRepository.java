package com.akshit.ontime.room.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.lifecycle.LiveData;

import com.akshit.ontime.models.SemesterDetails;
import com.akshit.ontime.room.dao.SemesterDAO;
import com.akshit.ontime.room.database.SemesterDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SemesterRepository {


    private static SemesterRepository semesterRepository;

    private SemesterDAO mSemesterDAO;

    private SemesterDatabase mSemesterDatabase;

    private LiveData<List<SemesterDetails>> semesterDetails;

    public SemesterRepository(final Application application) {
        mSemesterDatabase = SemesterDatabase.getInstance(application);
        mSemesterDAO = mSemesterDatabase.semesterDAO();
        semesterDetails = mSemesterDAO.getSemesterDetails();
    }

    public SemesterDetails getSemesterDetailsBySemester(int sem_num) {
        List<SemesterDetails> semester = new ArrayList<>();
        List<SemesterDetails> allSem = getSemesterDetails().getValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (allSem != null) {
                semester = allSem.stream().filter(sem -> sem.getSemesterNumber() == sem_num).collect(Collectors.toList());
            }
        }
        return semester.size() >= 1 ? semester.get(0) : null;
    }

    public LiveData<List<SemesterDetails>> getSemesterDetails() {
        return semesterDetails;
    }

    public void insertSemester(SemesterDetails semesterDetails) {
        new InsertSemesterDetailsAsyncTask(mSemesterDAO).execute(semesterDetails);
    }

    public void insertSemesters(SemesterDetails... semesterDetails) {
        new InsertAllSemesterDetailsAsyncTask(mSemesterDAO).execute(semesterDetails);
    }

    public void updateSemesterDetails(SemesterDetails semesterDetails) {
        new UpdateSemesterDetailsAsyncTask(mSemesterDAO).execute(semesterDetails);
    }

    public void deleteSemesterDetails(SemesterDetails semesterDetails) {
        new DeleteSemesterDetailsAsyncTask(mSemesterDAO).execute(semesterDetails);
    }

    public void deleteAllSemesters() {
        new DeleteAllSemesterDetailsAsyncTask(mSemesterDAO).execute();
    }


    private static class InsertSemesterDetailsAsyncTask extends AsyncTask<SemesterDetails, Void, Void> {

        private SemesterDAO mSemesterDAO;

        private InsertSemesterDetailsAsyncTask(SemesterDAO semesterDAO) {
            mSemesterDAO = semesterDAO;
        }

        @Override
        protected Void doInBackground(SemesterDetails... semesterDetails) {
            mSemesterDAO.insertSemester(semesterDetails[0]);
            return null;
        }
    }

    private static class UpdateSemesterDetailsAsyncTask extends AsyncTask<SemesterDetails, Void, Void> {

        private SemesterDAO mSemesterDAO;

        private UpdateSemesterDetailsAsyncTask(SemesterDAO semesterDAO) {
            mSemesterDAO = semesterDAO;
        }

        @Override
        protected Void doInBackground(SemesterDetails... semesterDetails) {
            mSemesterDAO.updateSemesterDetails(semesterDetails[0]);
            return null;
        }
    }

    private static class DeleteSemesterDetailsAsyncTask extends AsyncTask<SemesterDetails, Void, Void> {

        private SemesterDAO mSemesterDAO;

        private DeleteSemesterDetailsAsyncTask(SemesterDAO semesterDAO) {
            mSemesterDAO = semesterDAO;
        }

        @Override
        protected Void doInBackground(SemesterDetails... semesterDetails) {
            mSemesterDAO.deleteSemesterDetails(semesterDetails[0]);
            return null;
        }
    }

    private static class DeleteAllSemesterDetailsAsyncTask extends AsyncTask<Void, Void, Void> {

        private SemesterDAO mSemesterDAO;

        private DeleteAllSemesterDetailsAsyncTask(SemesterDAO semesterDAO) {
            mSemesterDAO = semesterDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mSemesterDAO.deleteAllSemesters();
            return null;
        }
    }

    private static class InsertAllSemesterDetailsAsyncTask extends AsyncTask<SemesterDetails, Void, Void> {

        private SemesterDAO mSemesterDAO;

        private InsertAllSemesterDetailsAsyncTask(SemesterDAO semesterDAO) {
            mSemesterDAO = semesterDAO;
        }

        @Override
        protected Void doInBackground(SemesterDetails... semesterDetails) {
            mSemesterDAO.insertSemesters(semesterDetails);
            return null;
        }
    }
}
