package com.example.biblereading.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biblereading.R;

/**
 * Created by Neramit777 on 1/18/2018 at 4:10 PM.
 */

public class AnnouceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_annouce, container, false);
        return view;
    }
}
