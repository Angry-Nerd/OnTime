package com.akshit.ontime.ui.semesters.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akshit.ontime.R;
import com.akshit.ontime.adapters.ResourceListAdapter;
import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.DbConstants;
import com.akshit.ontime.constants.IntentConstants;
import com.akshit.ontime.managers.UserManager;
import com.akshit.ontime.models.ResourceItem;
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
public class ResourcesFragment extends Fragment {

    public static final String TAG = AppConstants.APP_PREFIX + ResourcesFragment.class.getSimpleName();

    private SemesterDetails mSemesterDetails;

    private ResourceListAdapter mResourceListAdapter;

    private String mSubjectName;

    private TextView mSubjectNameTV;

    public ResourcesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resources, container, false);

        mSubjectNameTV = view.findViewById(R.id.subject_name);
        mSemesterDetails = getActivity().getIntent().getParcelableExtra(IntentConstants.SEMESTER_DETAILS);

        if (getArguments() != null) {
            mSubjectName = getArguments().getString(IntentConstants.SUBJECT_DETAILS);
            mSubjectNameTV.setText(mSubjectName);
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
        fetchResources(view);
        return view;
    }

    /**
     * Fetch the list of resources.
     * @param view of the frame layout
     */
    private void fetchResources(final View view) {
        final User user = UserManager.getInstance().getUser();
        final String university = SharedPreferenceManager.getUniversityName();
        final String semesterName = NumberToNameConverter.convertNumber(mSemesterDetails.getSemesterNumber());
        final CollectionReference subjectCollection = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY)
                .document("cgc.coe.edu.in").collection(DbConstants.STREAM).document(user.getStream()).collection(DbConstants.SEMESTERS)
                .document(semesterName).collection(DbConstants.SUBJECTS).document(mSubjectName).collection(DbConstants.RESOURCES);
        OnSuccessListener<QuerySnapshot> onSuccessListener = qs -> {
            final List<ResourceItem> resourcesList = new ArrayList<>();
            for (DocumentSnapshot ds : qs.getDocuments()) {
                Log.d(TAG, "fetchSubjectDetails: " + ds.toObject(ResourceItem.class));
                resourcesList.add(ds.toObject(ResourceItem.class));
            }
            view.findViewById(R.id.resources_progress_bar).setVisibility(View.GONE);
            mResourceListAdapter.setSemesterDetails(resourcesList);
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

    private void init(View view) {
        mResourceListAdapter = new ResourceListAdapter(getContext());
        final RecyclerView subjectsList = view.findViewById(R.id.resource_list);



        mResourceListAdapter.setOnResourceItemClickListener(resourceItem -> {
            final String typeOfResource = resourceItem.getTypeOfResource();

            switch (typeOfResource) {
                case "syllabus": {
                    SyllabusFragment syllabusFragment = new SyllabusFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentConstants.SUBJECT_DETAILS, mSubjectName);
                    syllabusFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.semester_fragment, syllabusFragment)
                            .addToBackStack(null)
                            .commit();
                    break;
                }
                case "resource_material": {
                    ResourceMaterialFragment resourceMaterialFragment = new ResourceMaterialFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentConstants.SUBJECT_DETAILS, mSubjectName);
                    resourceMaterialFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.semester_fragment, resourceMaterialFragment)
                            .addToBackStack(null)
                            .commit();
                    break;
                }
            }

        });

        subjectsList.setHasFixedSize(true);
        subjectsList.setLayoutManager(new LinearLayoutManager(getContext()));
        subjectsList.setAdapter(mResourceListAdapter);
    }
}
