package com.akshit.ontime.ui;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.akshit.ontime.Profile;
import com.akshit.ontime.R;
import com.akshit.ontime.adapters.SemesterAdapter;
import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.DbConstants;
import com.akshit.ontime.constants.IntentConstants;
import com.akshit.ontime.databinding.ActivityHomeBinding;
import com.akshit.ontime.models.SemesterDetails;
import com.akshit.ontime.ui.semesters.SubjectActivity;
import com.akshit.ontime.util.AppContext;
import com.akshit.ontime.util.AppUtils;
import com.akshit.ontime.util.FirebaseUtil;
import com.akshit.ontime.util.SharedPreferenceManager;
import com.akshit.ontime.viewmodel.SemesterDetailsViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
//import Aks#it B2ns2L


public class HomeActivity extends AppCompatActivity {

    public static final String TAG = AppConstants.APP_PREFIX + HomeActivity.class.getSimpleName();

    private ActivityHomeBinding mBinding;

    private SemesterAdapter mSemesterAdapter;

    private SemesterDetailsViewModel mSemesterDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        setSupportActionBar(mBinding.homeToolbar);

        final ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //disable touch unitl we get data
        AppUtils.disableTouch(this);

        mSemesterAdapter = new SemesterAdapter(this);

        //view model
        mSemesterDetailsViewModel = new ViewModelProvider(this, new SemesterDetailsViewModelFactory()).get(SemesterDetailsViewModel.class);

        mSemesterDetailsViewModel.get().observe(this, (semesterDetails) -> {
            Log.d(TAG, "Semester details changed.");
            observeSemesterDetails(semesterDetails);
        });


        //refresh layout
        mBinding.homeSwipe.setOnRefreshListener(() -> mSemesterDetailsViewModel.deleteAll());


        initNavView();
        initRecyclerView();

    }

    /**
     * Observe change in semester details.
     *
     * @param semesterDetails changes semester details list
     */
    private void observeSemesterDetails(final List<SemesterDetails> semesterDetails) {
        if (semesterDetails != null && semesterDetails.size() != 0) {
            mSemesterAdapter.setSemesterDetails(semesterDetails);
            mBinding.homeProgressBar.setVisibility(View.GONE);
            mBinding.homeSwipe.setRefreshing(false);

            AppUtils.enableTouch(this);
        } else {
            fetchSemesterDetails();
        }
    }

    /**
     * Fetch semester details if user not cached or forced.
     */
    private void fetchSemesterDetails() {
        Log.i(TAG, "Fetching semester details.");
        final String universityName = SharedPreferenceManager.getUniversityName();
        final OnSuccessListener<QuerySnapshot> onSuccessListener = documents -> {
            List<SemesterDetails> list = new ArrayList<>();
            for (final DocumentSnapshot snapshot : documents.getDocuments()) {
                final SemesterDetails semesterDetails = snapshot.toObject(SemesterDetails.class);
                if (semesterDetails == null) {
                    //TODO add a check here.
                    continue;
                }
                list.add(semesterDetails);
            }
            mSemesterDetailsViewModel.insertAll(list.toArray(new SemesterDetails[0]));

            //store the
            SharedPreferenceManager.setLastTimeSemesterSynced(System.currentTimeMillis());
//            new InsertAsyncTask(this).execute(mSemesterDetailsList.toArray(new SemesterDetails[0]));
            mBinding.homeProgressBar.setVisibility(View.GONE);
            AppUtils.enableTouch(this);
            mSemesterAdapter.notifyDataSetChanged();
            mBinding.homeSwipe.setRefreshing(false);
        };
        final OnFailureListener onFailureListener = e -> {
            Toast.makeText(this, "Can't fetch semesters info. Please try again later.", Toast.LENGTH_LONG).show();
            AppUtils.enableTouch(this);
            mBinding.homeSwipe.setRefreshing(false);
        };
        //TODO update stream
        final CollectionReference collectionReference = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY).document(universityName)
                .collection(DbConstants.STREAM).document("CSE").collection(DbConstants.SEMESTERS);
        FirebaseUtil.getFromCollection(collectionReference, onSuccessListener, onFailureListener);
    }

    /**
     * Init recycler view to show semester details.
     */
    private void initRecyclerView() {

        mSemesterAdapter.setOnSemesterClickListener(semesterDetails -> {
            final Intent intent = new Intent(this, SubjectActivity.class);
            intent.putExtra(IntentConstants.SEMESTER_DETAILS, semesterDetails);
            startActivity(intent);
        });
        mBinding.semestersGrid.setLayoutManager(new GridLayoutManager(this, 2));
        mBinding.semestersGrid.setHasFixedSize(true);
        mBinding.semestersGrid.setAdapter(mSemesterAdapter);
    }

    /**
     * Method to initialise the nav actions.
     */
    private void initNavView() {
        mBinding.homeNavView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.logout: {
                    FirebaseUtil.signOut(this);
                    break;
                }
                case R.id.profile: {
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    break;
                }

            }
            mBinding.drawerLayout.closeDrawer(GravityCompat.START, true);
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mBinding.drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Class to instantiate the semster details view model class.
     */
    private static class SemesterDetailsViewModelFactory implements ViewModelProvider.Factory {
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                return modelClass.getConstructor(Application.class).newInstance((Application) AppContext.getContext());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException();
            } catch (InstantiationException e) {
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


