package com.akshit.ontime.util;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.akshit.ontime.constants.DbConstants;
import com.akshit.ontime.models.User;
import com.akshit.ontime.ui.HomeActivity;
import com.akshit.ontime.ui.auth.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Map;

public class FirebaseUtil {

    private static final String TAG = "FirebaseUtil";
    private static FirebaseStorage storage;
    private static FirebaseFirestore db;
    private static FirebaseAuth auth;

    public static boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    public static void initFirebase() {
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public static FirebaseAuth getAuth() {
        return auth;
    }

    public static FirebaseStorage getStorage() {
        return storage;
    }

    /**
     * Get db instance.
     *
     * @return the db instance
     */
    public static FirebaseFirestore getDb() {
        return db;
    }



    private static void getUserDetails(String id, String domain, LoginActivity activity) {
        final DocumentReference documentReference = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY)
                .document(domain).collection(DbConstants.USERS).document(id);

        final OnSuccessListener<DocumentSnapshot> onSuccessListener = userDoc -> {
            SharedPreferenceManager.setLoggedInEmail(id + "@" + domain);
            User user = userDoc.toObject(User.class);
            SharedPreferenceManager.setUser(user);
            SharedPreferenceManager.setLoggedInEmail(id);
            SharedPreferenceManager.setUniversityName(domain);
            activity.mBinding.loginProgress.setVisibility(View.GONE);
            activity.startActivity(new Intent(activity, HomeActivity.class));
            activity.finish();
        };


        final OnFailureListener failureListener = e -> {
            auth.signOut();
            AppUtils.enableTouch(activity);
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
            activity.mBinding.loginProgress.setVisibility(View.GONE);
        };


        getFromDocument(documentReference, onSuccessListener, failureListener);
    }


    /**
     * Get server key.
     */
    public static void getServerKey() {
        OnSuccessListener<DocumentSnapshot> onSuccessListener = d -> {
            String serverKey = (String) d.get(DbConstants.SERVER_KEY);
            Log.d(TAG, "getServerKey: " + serverKey);
//            SharedPreferenceManager.setServerKey(serverKey);
        };
        OnFailureListener onFailureListener = e -> {
            //TODO server key check
            Log.d(TAG, "getServerKey: failed");
        };
        getServerKey(onSuccessListener, onFailureListener);
    }

//    /**
//     * Set the user token
//     *
//     * @param domain   of the university
//     * @param id       of the user
//     * @param activity login activity instance
//     */
//    private static void setTokenToUser(String domain, String id, com.akshit.college.ui.LoginActivity activity) {
//        final DocumentReference documentReference = FirebaseUtil.getDb().collection(DbConstants.UNIVERSITY)
//                .document(domain).collection(DbConstants.USERS).document(id);
//
//        final OnSuccessListener<DocumentSnapshot> onSuccessListener = user -> {
//
//        };
//
//
//        final OnFailureListener failureListener = e -> {
//            auth.signOut();
//            AppUtils.enableTouch(activity);
//            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
//            activity.mBinding.loginProgress.setVisibility(View.GONE);
//        };
//
//        final Map<String, Object> tokenMap = new HashMap<>();
//        final String token = SharedPreferenceManager.getUserToken();
//        tokenMap.put("token", token);
//        tokenMap.put("lastLoggedIn", System.currentTimeMillis());
//        updateInFirebase(documentReference, onSuccessListener, failureListener, tokenMap);
//    }

    public static void getFromFirebase(DocumentReference documentReference,
                                       OnSuccessListener<DocumentSnapshot> listener, OnFailureListener onFailureListener) {
        documentReference.get().addOnSuccessListener(listener).addOnFailureListener(onFailureListener);
    }

    public static void updateInFirebase(DocumentReference documentReference,
                                        OnSuccessListener listener, OnFailureListener onFailureListener, Map<String, Object> object) {
        documentReference.update(object).addOnSuccessListener(listener).addOnFailureListener(onFailureListener);
    }


    public static void addToCollection(CollectionReference collection,
                                       OnSuccessListener<DocumentReference> onSuccessListener, Object object
            , OnFailureListener onFailureListener) {
        collection.add(object).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static void getFromCollection(CollectionReference reference,
                                         OnSuccessListener<QuerySnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        reference.get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static void getFromDocument(DocumentReference reference,
                                       OnSuccessListener<DocumentSnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        reference.get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static void signOut(Activity activity) {
        if (AppUtils.checkInternetConnectivity(activity)) {
            SharedPreferenceManager.clearAll();
            FirebaseAuth.getInstance().signOut();
            activity.startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();
        } else {
            Toast.makeText(activity, "No Internet connection.", Toast.LENGTH_LONG).show();
        }
    }

    private static void getServerKey(OnSuccessListener<DocumentSnapshot> referenceOnSuccessListener,
                                     OnFailureListener failureListener) {
        DocumentReference reference = getDb().collection(DbConstants.MASTER_ADMIN).document(DbConstants.SERVER);
        getFromDocument(reference, referenceOnSuccessListener, failureListener);
    }

    public static void setDocument(DocumentReference documentReference, OnSuccessListener onSuccessListener,
                                   OnFailureListener onFailureListener, Object object) {
        documentReference.set(object).addOnFailureListener(onFailureListener).addOnSuccessListener(onSuccessListener);
    }
}
