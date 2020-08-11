package com.akshit.ontime.ui.semesters.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akshit.ontime.R;
import com.akshit.ontime.adapters.ResourceMaterialAdapter;
import com.akshit.ontime.adapters.SyllabusItemAdapter;
import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.DbConstants;
import com.akshit.ontime.constants.IntentConstants;
import com.akshit.ontime.managers.UserManager;
import com.akshit.ontime.models.ResourceMaterialItem;
import com.akshit.ontime.models.SemesterDetails;
import com.akshit.ontime.models.SyllabusItem;
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
public class ResourceMaterialFragment extends Fragment {

    private ResourceMaterialAdapter mResourceMaterialAdapter;

    public static final String TAG = AppConstants.APP_PREFIX + ResourceMaterialFragment.class.getSimpleName();

    private SemesterDetails mSemesterDetails;

    private SyllabusItemAdapter mResourceItemAdapter;

    private String mSubjectName;

    private ProgressBar resourceMaterialProgressBar;



    public ResourceMaterialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resource_material, container, false);

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

        fetchResourceMaterials(view);
        return view;
    }

    /**
     * Fetch the syllabus details.
     *
     * @param view of the frame layout
     */
    private void fetchResourceMaterials(final View view) {
        final User user = UserManager.getInstance().getUser();
        final String university = SharedPreferenceManager.getUniversityName();
        final String semesterName = NumberToNameConverter.convertNumber(mSemesterDetails.getSemesterNumber());
        final CollectionReference subjectCollection = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY)
                .document("cgc.coe.edu.in").collection(DbConstants.STREAM).document(user.getStream()).collection(DbConstants.SEMESTERS)
                .document(semesterName).collection(DbConstants.SUBJECTS).document(mSubjectName).collection(DbConstants.RESOURCES)
                .document(DbConstants.RESOURCE_MATERIAL).collection(DbConstants.FILES);
        OnSuccessListener<QuerySnapshot> onSuccessListener = qs -> {
            final List<ResourceMaterialItem> items = new ArrayList<>();
            for (DocumentSnapshot ds : qs.getDocuments()) {
                Log.d(TAG, "fetchSubjectDetails: " + ds.toObject(ResourceMaterialItem.class));
                items.add(ds.toObject(ResourceMaterialItem.class));
            }
            resourceMaterialProgressBar.setVisibility(View.GONE);
            mResourceMaterialAdapter.setResourceMaterialItems(items);
            if (getActivity() != null) {
                AppUtils.enableTouch(getActivity());
            }
        };

        OnFailureListener onFailureListener = e -> {
            Toast.makeText(AppContext.getContext(), "Unable to fetch subject details. Please retry later.", Toast.LENGTH_LONG).show();
            resourceMaterialProgressBar.setVisibility(View.GONE);
            if (getActivity() != null) {
                AppUtils.enableTouch(getActivity());
            }
        };

        FirebaseUtil.getFromCollection(subjectCollection, onSuccessListener, onFailureListener);
    }

    private void init(final View view) {
        final RecyclerView recyclerView = view.findViewById(R.id.resource_material_recycler_view);
        mResourceMaterialAdapter = new ResourceMaterialAdapter(getContext());

        resourceMaterialProgressBar = view.findViewById(R.id.resource_material_progress_bar);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mResourceMaterialAdapter);
    }
}
