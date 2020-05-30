package com.akshit.ontime.ui.semesters.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akshit.ontime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResourcesFragment extends Fragment {

    public ResourcesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resources, container, false);

        init(view);
        return view;
    }

    private void init(View view) {

    }
}
