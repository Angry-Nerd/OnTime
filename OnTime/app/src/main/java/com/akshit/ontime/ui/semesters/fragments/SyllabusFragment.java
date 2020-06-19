package com.akshit.ontime.ui.semesters.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akshit.ontime.R;
import com.akshit.ontime.adapters.SyllabusItemAdapter;
import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.DbConstants;
import com.akshit.ontime.constants.IntentConstants;
import com.akshit.ontime.managers.UserManager;
import com.akshit.ontime.models.SyllabusItem;
import com.akshit.ontime.models.SemesterDetails;
import com.akshit.ontime.models.User;
import com.akshit.ontime.util.AppContext;
import com.akshit.ontime.util.AppUtils;
import com.akshit.ontime.util.FirebaseUtil;
import com.akshit.ontime.util.NumberToNameConverter;
import com.akshit.ontime.util.SharedPreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SyllabusFragment extends Fragment {

    public static final String TAG = AppConstants.APP_PREFIX + SyllabusFragment.class.getSimpleName();

    private SemesterDetails mSemesterDetails;

    private SyllabusItemAdapter mSyllabusListAdapter, mBooksListAdapter;

    private String mSubjectName;

    private ProgressBar syllabusProgressBar;

    public SyllabusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_syllabus, container, false);

        mSemesterDetails = getActivity().getIntent().getParcelableExtra(IntentConstants.SEMESTER_DETAILS);

        if (getArguments() != null) {
            mSubjectName = getArguments().getString(IntentConstants.SUBJECT_DETAILS);
            Log.d(TAG, "Got semester details " + mSemesterDetails + mSubjectName);
        } else {
            Toast.makeText(getContext(), "There's some error. Please retry again.", Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
            return null;
        }

        if (getActivity() != null) {
            AppUtils.disableTouch(getActivity());
        }


        init(view);

        fetchSyllabusDetails(view);
        fetchBookDetails(view);
        return view;
    }

    /**
     * Fetch the books to refer from db.
     * @param view of the fragment
     */
    private void fetchBookDetails(View view) {
        final User user = UserManager.getInstance().getUser();
        final String university = SharedPreferenceManager.getUniversityName();
        final String semesterName = NumberToNameConverter.convertNumber(mSemesterDetails.getSemesterNumber());
        final CollectionReference subjectCollection = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY)
                .document(university).collection(DbConstants.STREAM).document(user.getStream()).collection(DbConstants.SEMESTERS)
                .document(semesterName).collection(DbConstants.SUBJECTS).document(mSubjectName).collection(DbConstants.RESOURCES)
                .document(DbConstants.SYLLABUS).collection(DbConstants.BOOKS_TO_REFER);
        OnSuccessListener<QuerySnapshot> onSuccessListener = qs -> {
            final List<SyllabusItem> items = new ArrayList<>();
            for (DocumentSnapshot ds : qs.getDocuments()) {
                Log.d(TAG, "fetchSubjectDetails: " + ds.toObject(SyllabusItem.class));
                items.add(ds.toObject(SyllabusItem.class));
            }
            syllabusProgressBar.setVisibility(View.GONE);
            mBooksListAdapter.setSyllabusItems(items);
            if (getActivity() != null) {
                AppUtils.enableTouch(getActivity());
            }
        };

        OnFailureListener onFailureListener = e -> {
            Toast.makeText(AppContext.getContext(), "Unable to fetch subject details. Please retry later.", Toast.LENGTH_LONG).show();
            syllabusProgressBar.setVisibility(View.GONE);
            if (getActivity() != null) {
                AppUtils.enableTouch(getActivity());
            }
        };

        FirebaseUtil.getFromCollection(subjectCollection, onSuccessListener, onFailureListener);
    }

    /**
     * Fetch the syllabus details.
     *
     * @param view of the frame layout
     */
    private void fetchSyllabusDetails(final View view) {
        final User user = UserManager.getInstance().getUser();
        final String university = SharedPreferenceManager.getUniversityName();
        final String semesterName = NumberToNameConverter.convertNumber(mSemesterDetails.getSemesterNumber());
        final CollectionReference subjectCollection = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY)
                .document(university).collection(DbConstants.STREAM).document(user.getStream()).collection(DbConstants.SEMESTERS)
                .document(semesterName).collection(DbConstants.SUBJECTS).document(mSubjectName).collection(DbConstants.RESOURCES)
                .document(DbConstants.SYLLABUS).collection(DbConstants.SYLLABUS_LIST);
        OnSuccessListener<QuerySnapshot> onSuccessListener = qs -> {
            final List<SyllabusItem> items = new ArrayList<>();
            for (DocumentSnapshot ds : qs.getDocuments()) {
                Log.d(TAG, "fetchSubjectDetails: " + ds.toObject(SyllabusItem.class));
                items.add(ds.toObject(SyllabusItem.class));
            }
            syllabusProgressBar.setVisibility(View.GONE);
            mSyllabusListAdapter.setSyllabusItems(items);
            if (getActivity() != null) {
                AppUtils.enableTouch(getActivity());
            }
        };

        OnFailureListener onFailureListener = e -> {
            Toast.makeText(AppContext.getContext(), "Unable to fetch subject details. Please retry later.", Toast.LENGTH_LONG).show();
            syllabusProgressBar.setVisibility(View.GONE);
            if (getActivity() != null) {
                AppUtils.enableTouch(getActivity());
            }
        };

        FirebaseUtil.getFromCollection(subjectCollection, onSuccessListener, onFailureListener);
    }

    private void init(final View view) {
        mSyllabusListAdapter = new SyllabusItemAdapter(getContext());
        mBooksListAdapter = new SyllabusItemAdapter(getContext());
        syllabusProgressBar = view.findViewById(R.id.syllabus_progress_bar);
        final RecyclerView syllabusItemList = view.findViewById(R.id.recycler_syllabus_list);
        final RecyclerView booksToReferList = view.findViewById(R.id.recycler_books_list);

        mSyllabusListAdapter.setOnItemClickListener(item -> {
            final String title = item.getTitle();
            Toast.makeText(getContext(), title, Toast.LENGTH_LONG).show();
        });

        syllabusItemList.setHasFixedSize(true);
        syllabusItemList.setLayoutManager(new LinearLayoutManager(getContext()));
        syllabusItemList.setAdapter(mSyllabusListAdapter);

        booksToReferList.setHasFixedSize(true);
        booksToReferList.setLayoutManager(new LinearLayoutManager(getContext()));
        booksToReferList.setAdapter(mBooksListAdapter);
    }
}
