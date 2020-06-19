package com.akshit.ontime.room.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.akshit.ontime.models.SubjectDetails;
import com.akshit.ontime.room.dao.SubjectDao;
import com.akshit.ontime.room.database.SubjectDatabase;

import java.util.List;

public class SubjectRepository {

    private static SubjectRepository subjectRepository;

    private SubjectDao mSubjectDao;

    private SubjectDatabase mSubjectDatabase;

    private LiveData<List<SubjectDetails>> subjectDetails;

    public SubjectRepository(final Application application, String semName) {
        mSubjectDatabase = SubjectDatabase.getInstance(application);
        mSubjectDao = mSubjectDatabase.subjectDao();
        subjectDetails = mSubjectDao.getSubjectDetailsBySemesterNumber(semName);
    }

    public LiveData<List<SubjectDetails>> getSubjectDetails() {
        return subjectDetails;
    }

    public void insertSubject(SubjectDetails SubjectDetails) {
        new InsertSubjectDetailsAsyncTask(mSubjectDao).execute(SubjectDetails);
    }

    public void insertSubjects(SubjectDetails... SubjectDetails) {
        new InsertAllSubjectDetailsAsyncTask(mSubjectDao).execute(SubjectDetails);
    }

    public void updateSubjectDetails(SubjectDetails SubjectDetails) {
        new UpdateSubjectDetailsAsyncTask(mSubjectDao).execute(SubjectDetails);
    }

    public void deleteSubjectDetails(String semName) {
        new DeleteSubjectDetailsAsyncTask(mSubjectDao).execute(semName);
    }

    public void deleteAllSubjects() {
        new DeleteAllSubjectDetailsAsyncTask(mSubjectDao).execute();
    }


    private static class InsertSubjectDetailsAsyncTask extends AsyncTask<SubjectDetails, Void, Void> {

        private SubjectDao mSubjectDAO;

        private InsertSubjectDetailsAsyncTask(SubjectDao SubjectDAO) {
            mSubjectDAO = SubjectDAO;
        }

        @Override
        protected Void doInBackground(SubjectDetails... subjectDetails) {
            mSubjectDAO.insertSubject(subjectDetails[0]);
            return null;
        }
    }

    private static class UpdateSubjectDetailsAsyncTask extends AsyncTask<SubjectDetails, Void, Void> {

        private SubjectDao mSubjectDao;

        private UpdateSubjectDetailsAsyncTask(SubjectDao subjectDao) {
            mSubjectDao = subjectDao;
        }

        @Override
        protected Void doInBackground(SubjectDetails... SubjectDetails) {
            mSubjectDao.updateSubjectDetails(SubjectDetails[0]);
            return null;
        }
    }

    private static class DeleteSubjectDetailsAsyncTask extends AsyncTask<String, Void, Void> {

        private SubjectDao mSubjectDao;

        private DeleteSubjectDetailsAsyncTask(SubjectDao subjectDao) {
            mSubjectDao = subjectDao;
        }

        @Override
        protected Void doInBackground(String... semName) {
            mSubjectDao.deleteSubjectDetailsForSemesterNumber(semName[0]);
            return null;
        }
    }

    private static class DeleteAllSubjectDetailsAsyncTask extends AsyncTask<Void, Void, Void> {

        private SubjectDao mSubjectDao;

        private DeleteAllSubjectDetailsAsyncTask(SubjectDao subjectDAO) {
            mSubjectDao = subjectDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mSubjectDao.deleteAllSubjects();
            return null;
        }
    }

    private static class InsertAllSubjectDetailsAsyncTask extends AsyncTask<SubjectDetails, Void, Void> {

        private SubjectDao mSubjectDao;

        private InsertAllSubjectDetailsAsyncTask(SubjectDao subjectDAO) {
            mSubjectDao = subjectDAO;
        }

        @Override
        protected Void doInBackground(SubjectDetails... subjectDetails) {
            mSubjectDao.insertSubjects(subjectDetails);
            return null;
        }
    }

}
