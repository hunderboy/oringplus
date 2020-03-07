package com.example.oringmaster2.BottomNavigationFragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.oringmaster2.FragmentAdapter;
import com.example.oringmaster2.R;
import com.example.oringmaster2.RecyclerViewAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class DimensionSearchFragment extends Fragment {
    View view;

    private TabLayout tabLayout;
    private ArrayList<String> tabNames = new ArrayList<>();

//    @Override
//    public void onAttach(Context context) {
//        Log.e(this.getClass().getSimpleName(), "onAttach()");
//        super.onAttach(context);
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        Log.e(this.getClass().getSimpleName(), "onCreate()");
//        super.onCreate(savedInstanceState);
//    }
//    @Override
//    public void onDestroyView() {
//        Log.e(this.getClass().getSimpleName(), "onDestroyView()");
//        super.onDestroyView();
//        tabNames.clear();   // 탭 초기화
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dimension_search, container, false);

        loadTabName();
        setTabLayout();
        setViewPager();

        return view;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void setTabLayout(){
        tabLayout = view.findViewById(R.id.tab);
        tabNames.forEach(name ->tabLayout.addTab(tabLayout.newTab().setText(name)));
    }

    private void loadTabName(){
        tabNames.clear();
        tabNames.add("O-ring"); // 오링
        tabNames.add("X-ring"); // 엑스링
        tabNames.add("Back-Up ring"); // 백업링
    }

    private void setViewPager() {
        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
