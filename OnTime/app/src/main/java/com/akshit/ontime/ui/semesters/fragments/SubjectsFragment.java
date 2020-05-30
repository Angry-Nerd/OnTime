package com.akshit.ontime.ui.semesters.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akshit.ontime.R;
import com.akshit.ontime.adapters.SubjectsListAdapter;
import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.DbConstants;
import com.akshit.ontime.constants.IntentConstants;
import com.akshit.ontime.models.SemesterDetails;
import com.akshit.ontime.models.SubjectDetails;
import com.akshit.ontime.util.AppContext;
import com.akshit.ontime.util.AppUtils;
import com.akshit.ontime.util.FirebaseUtil;
import com.akshit.ontime.util.SharedPreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
     * Seletced Semester details.
     */
    private SemesterDetails mSemesterDetails;
    private SubjectsListAdapter mSubjectsListAdapter;


    public SubjectsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subjects, container, false);


        if (getArguments() != null) {
            mSemesterDetails = getArguments().getParcelable(IntentConstants.SEMESTER_DETAILS);
            Log.d(TAG, "Got semester details " + mSemesterDetails);
        } else {
            Toast.makeText(getContext(), "There's some error. Please retry again.", Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
        }
        AppUtils.disableTouch(getActivity());

        init(view);
        fetchSubjectDetails(view);

        return view;
    }

    private void fetchSubjectDetails(final View view) {
        final String university = SharedPreferenceManager.getUniversityName();
        final CollectionReference subjectCollection = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY)
                .document(university).collection(DbConstants.STREAM).document("CSE").collection(DbConstants.SEMESTERS)
                .document("First").collection(DbConstants.SUBJECTS);
        OnSuccessListener<QuerySnapshot> onSuccessListener = qs -> {
            final List<SubjectDetails> subjectDetailsList = new ArrayList<>();
            for (DocumentSnapshot ds : qs.getDocuments()) {
                Log.d(TAG, "fetchSubjectDetails: " + ds.toObject(SubjectDetails.class));
                subjectDetailsList.add(ds.toObject(SubjectDetails.class));
            }
            view.findViewById(R.id.subject_progress_bar).setVisibility(View.GONE);
            mSubjectsListAdapter.setSemesterDetails(subjectDetailsList);
            AppUtils.enableTouch(getActivity());
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
     * @param view of fragment.
     */
    private void init(final View view) {

        mSubjectsListAdapter = new SubjectsListAdapter(getContext());
        final RecyclerView subjectsList = view.findViewById(R.id.semester_subjects_list);

        final ResourcesFragment resourcesFragment = new ResourcesFragment();

        mSubjectsListAdapter.setOnSemesterClickListener(subjectDetails -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.semester_fragment, resourcesFragment)
                    .addToBackStack(null)
                    .commit();
        });


        subjectsList.setHasFixedSize(true);
        subjectsList.setLayoutManager(new LinearLayoutManager(getContext()));
        subjectsList.setAdapter(mSubjectsListAdapter);

    }
}
