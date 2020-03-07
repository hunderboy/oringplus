package com.example.oringmaster2.DimensionSearchFragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.oringmaster2.R;

public class XringFragment extends Fragment {

    View view;

    public static XringFragment newInstance() {
        XringFragment tab2 = new XringFragment();
        return tab2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);
        return view;
    }

}
