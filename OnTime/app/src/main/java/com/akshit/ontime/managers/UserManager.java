package com.akshit.ontime.managers;

import android.util.Log;
import android.widget.Toast;

import com.akshit.ontime.constants.AppConstants;
import com.akshit.ontime.constants.DbConstants;
import com.akshit.ontime.models.User;
import com.akshit.ontime.util.AppContext;
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
    private static User user;

    /**
     * Private constructor.
     */
    private UserManager() {

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

    public void fetchUserDetails() {
        if (!FirebaseUtil.isLoggedIn()) {
            Log.i(TAG, "User not logged in. Returning.");
            return;
        }
        final String university = SharedPreferenceManager.getUniversityName();
        final String email = SharedPreferenceManager.getLoggedEmail();
        final DocumentReference documentReference = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY).document(university)
                .collection(DbConstants.USERS).document(email);

        final OnSuccessListener<DocumentSnapshot> onSuccessListener = doc -> {
            if (doc.exists()) {
                User docUser = doc.toObject(User.class);
                Log.i(TAG, "Got document " + docUser);
            } else {
                Log.e(TAG, "Document do not exists.");
            }
        };
        final OnFailureListener onFailureListener = o -> Log.d(TAG, "Error occurred." + o.getMessage());
        FirebaseUtil.getFromDocument(documentReference, onSuccessListener, onFailureListener);
    }
}
