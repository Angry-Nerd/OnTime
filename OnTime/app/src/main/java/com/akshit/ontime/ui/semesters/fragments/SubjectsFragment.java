package com.akshit.ontime.ui.semesters.fragments;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.akshit.ontime.R;
import com.akshit.ontime.adapters.SubjectsListAdapter;
import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.DbConstants;
import com.akshit.ontime.constants.IntentConstants;
import com.akshit.ontime.managers.UserManager;
import com.akshit.ontime.models.SemesterDetails;
import com.akshit.ontime.models.SubjectDetails;
import com.akshit.ontime.models.User;
import com.akshit.ontime.util.AppContext;
import com.akshit.ontime.util.AppUtils;
import com.akshit.ontime.util.FirebaseUtil;
import com.akshit.ontime.util.NumberToNameConverter;
import com.akshit.ontime.util.SharedPreferenceManager;
import com.akshit.ontime.viewmodel.SubjectDetailsViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to show the subjects list.
 */
public class SubjectsFragment extends Fragment {

    /**
     * Class tag.
     */
    public static final String TAG = AppConstants.APP_PREFIX + SubjectsFragment.class.getSimpleName();

    /**
     * Selected Semester details.
     */
    private SemesterDetails mSemesterDetails;
    private SubjectsListAdapter mSubjectsListAdapter;

    private TextView mSemesterName;

    private SubjectDetailsViewModel mSubjectDetailsViewModel;

    private ProgressBar loadingProgressBar;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String semesterName;

    public SubjectsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subjects, container, false);

        mSemesterName = view.findViewById(R.id.semester_name);

        if (getArguments() != null) {
            mSemesterDetails = getArguments().getParcelable(IntentConstants.SEMESTER_DETAILS);
            Log.d(TAG, "Got semester details " + mSemesterDetails);
        } else {
            Toast.makeText(getContext(), "There's some error. Please retry again.", Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
        }
        if (getActivity() != null) {
            AppUtils.disableTouch(getActivity());
        } else {
            throw new IllegalStateException("No parent activity for this parent.");
        }


        semesterName = NumberToNameConverter.convertNumber(mSemesterDetails.getSemesterNumber());
        Log.d(TAG, "onCreateView: " + semesterName);
        init(view);
        observeSubjects(view);
        return view;
    }

    private void observeSubjects(final View view) {
        mSubjectDetailsViewModel = new ViewModelProvider(getActivity(), new SubjectDetailsViewModelFactory(semesterName)).get(SubjectDetailsViewModel.class);
        mSubjectDetailsViewModel.get().observe(this, subjects -> {
            Log.d(TAG, "observeSubjects: ");
            if (subjects != null && subjects.size() != 0) {
                Log.d(TAG, "observeSubjects: 2");
                mSubjectsListAdapter.setSemesterDetails(subjects);
                loadingProgressBar.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                AppUtils.enableTouch(getActivity());
            } else {
                Log.d(TAG, "observeSubjects: 3");
                fetchSubjectDetails(view);
            }
        });
    }

    private void fetchSubjectDetails(final View view) {
        final User user = UserManager.getInstance().getUser();
        final String university = SharedPreferenceManager.getUniversityName();
        final CollectionReference subjectCollection = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY)
                .document("cgc.coe.edu.in").collection(DbConstants.STREAM).document(user.getStream()).collection(DbConstants.SEMESTERS)
                .document(semesterName).collection(DbConstants.SUBJECTS);
        OnSuccessListener<QuerySnapshot> onSuccessListener = qs -> {
            final List<SubjectDetails> subjectDetailsList = new ArrayList<>();
            for (DocumentSnapshot ds : qs.getDocuments()) {
                SubjectDetails subjectDetails = ds.toObject(SubjectDetails.class);
                subjectDetails.setSemesterName(semesterName);
                Log.d(TAG, "fetchSubjectDetails: " + subjectDetails);
                subjectDetailsList.add(subjectDetails);
            }
            mSubjectDetailsViewModel.insertAll(subjectDetailsList.toArray(new SubjectDetails[0]));
            Log.d(TAG, "fetchSubjectDetails: 4");
            if (getActivity() != null) {
                AppUtils.enableTouch(getActivity());
            }
        };

        OnFailureListener onFailureListener = e -> {
            Toast.makeText(AppContext.getContext(), "Unable to fetch subject details. Please retry later.", Toast.LENGTH_LONG).show();
            view.findViewById(R.id.subject_progress_bar).setVisibility(View.GONE);
            if (getActivity() != null) {
                AppUtils.enableTouch(getActivity());
            }
        };

        FirebaseUtil.getFromCollection(subjectCollection, onSuccessListener, onFailureListener);
    }

    /**
     * Init the view and adapters.
     *
     * @param view of fragment.
     */
    private void init(final View view) {

        mSubjectsListAdapter = new SubjectsListAdapter(getContext());

        loadingProgressBar = view.findViewById(R.id.subject_progress_bar);

        mSwipeRefreshLayout = view.findViewById(R.id.subject_swipe_refresh);

        final RecyclerView subjectsList = view.findViewById(R.id.semester_subjects_list);
        mSemesterName.setText(semesterName == null ? "Subjects" : semesterName + " Semester");


        mSubjectsListAdapter.setOnSemesterClickListener(subjectDetails -> {
            final ResourcesFragment resourcesFragment = new ResourcesFragment();
            String subjectName = subjectDetails.getNameOfSubject();
            Bundle bundle = new Bundle();
            bundle.putString(IntentConstants.SUBJECT_DETAILS, subjectName);
            resourcesFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.semester_fragment, resourcesFragment)
                    .addToBackStack(null)
                    .commit();
        });

        subjectsList.setHasFixedSize(true);
        subjectsList.setLayoutManager(new LinearLayoutManager(getContext()));
        subjectsList.setAdapter(mSubjectsListAdapter);

        String finalSemesterName = semesterName;
        mSwipeRefreshLayout.setOnRefreshListener(() -> mSubjectDetailsViewModel.deleteForSemName(finalSemesterName));

    }


    /**
     * Class to instantiate the subject details view model class.
     */
    private static class SubjectDetailsViewModelFactory implements ViewModelProvider.Factory {

        private String semesterName;

        public SubjectDetailsViewModelFactory(String semesterName) {
            this.semesterName = semesterName;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                return modelClass.getConstructor(Application.class, String.class).newInstance(AppContext.getContext(), semesterName);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }
}
