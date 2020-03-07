package com.example.oringmaster2.BottomNavigationFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.oringmaster2.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OringHousingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OringHousingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OringHousingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_oring_housing, container, false);
    }

}
