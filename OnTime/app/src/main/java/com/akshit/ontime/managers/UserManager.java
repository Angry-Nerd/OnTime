package com.akshit.ontime.managers;

import android.util.Log;

import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.DbConstants;
import com.akshit.ontime.models.User;
import com.akshit.ontime.util.FirebaseUtil;
import com.akshit.ontime.util.SharedPreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Class to manage user.
 */
public class UserManager {

    public static final String TAG = AppConstants.APP_PREFIX + UserManager.class.getSimpleName();

    /**
     * Class static instance.
     */
    private static UserManager instance;

    /**
     * User instance.
     */
    private static User mUser;

    /**
     * Private constructor.
     */
    private UserManager() {
        mUser = SharedPreferenceManager.getUser();
    }

    /**
     * Get the user.
     * @return user.
     */
    public User getUser() {
        return mUser;
    }

    /**
     * Get manager instance.
     *
     * @return manager instance.
     */
    public synchronized static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    /**
     * Method to fetch user details from fire store.
     */
    public void fetchUserDetails() {
        Log.d(TAG, "Fetching user details from firebase.");
        setUser(SharedPreferenceManager.getUser());
//        int applicationStatus = SharedPreferenceManager.getApplicationStatus();
//         || applicationStatus == 0 || applicationStatus == 1
        if (!FirebaseUtil.isLoggedIn()) {
            return;
        }
        final String university = SharedPreferenceManager.getUniversityName();
        final String email = SharedPreferenceManager.getLoggedEmail();
        final DocumentReference documentReference = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY).document(university)
                .collection(DbConstants.USERS).document(email);

        final OnSuccessListener<DocumentSnapshot> onSuccessListener = doc -> {
            if (doc.exists()) {
                User docUser = doc.toObject(User.class);
                mUser = docUser;
                SharedPreferenceManager.setUser(docUser);
                Log.i(TAG, "Got document " + docUser);
            } else {
                Log.e(TAG, "Document do not exists.");
            }
        };
        final OnFailureListener onFailureListener = o -> Log.d(TAG, "Error occurred." + o.getMessage());
        FirebaseUtil.getFromDocument(documentReference, onSuccessListener, onFailureListener);
    }

    /**
     * Set the logged in user.
     * @param user that is logged in
     */
    public void setUser(final User user) {
        Log.d(TAG, "setUser: " + user);
        mUser = user;
    }
}
