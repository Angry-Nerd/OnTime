package com.akshit.ontime.ui.semesters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.akshit.ontime.R;
import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.IntentConstants;
import com.akshit.ontime.databinding.ActivitySubjectBinding;
import com.akshit.ontime.models.SemesterDetails;
import com.akshit.ontime.ui.semesters.fragments.SubjectsFragment;

public class SubjectActivity extends AppCompatActivity {

    /**
     * Class tag.
     */
    public static final String TAG = AppConstants.APP_PREFIX + SubjectActivity.class.getSimpleName();

    /**
     * View binder.
     */
    private ActivitySubjectBinding mBinding;

    /**
     * Semester details.
     */
    private SemesterDetails mSemesterDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_subject);

        final Intent receivedIntent = getIntent();

        mSemesterDetails = receivedIntent.getParcelableExtra(IntentConstants.SEMESTER_DETAILS);

        if (mSemesterDetails == null) {
            Toast.makeText(this, "There's some error. Please select again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        final SubjectsFragment subjectsFragment = new SubjectsFragment();

        final Bundle semesterDetailsBundle = new Bundle();
        semesterDetailsBundle.putParcelable(IntentConstants.SEMESTER_DETAILS, mSemesterDetails);
        subjectsFragment.setArguments(semesterDetailsBundle);

        //show subject in the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(mBinding.semesterFragment.getId(), subjectsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
