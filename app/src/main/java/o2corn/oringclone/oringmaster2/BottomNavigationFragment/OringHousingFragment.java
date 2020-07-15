package o2corn.oringclone.oringmaster2.BottomNavigationFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import o2corn.oringclone.oringmaster2.FragmentAdapter;
import com.oringclone.oringmaster2.R;
import com.google.android.material.tabs.TabLayout;


public class OringHousingFragment extends Fragment {

    View view; // OringHousingFragment View

    View fragment_oring;    // Oring Fragment View
//    View layout_oring;
    View layout_xring;
    View layout_backupring;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_oring_housing, container, false);
        Log.e(this.getClass().getSimpleName(), "onCreateView()");

        fragment_oring = view.findViewById(R.id.fragment_oring) ;

//        layout_oring = view.findViewById(R.id.layout_oring) ;
        layout_xring = view.findViewById(R.id.layout_xring) ;
        layout_backupring = view.findViewById(R.id.layout_backupring) ;



        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout) ;

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { // TODO : tab의 상태가 선택 상태로 변경.
                int pos = tab.getPosition() ;
                changeView(pos) ;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { // TODO : tab의 상태가 선택되지 않음으로 변경.

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { // TODO : 이미 선택된 tab이 다시

            }
        });


        return view;
    }



    private void changeView(int index) {
        switch (index) {
            case 0 :
                fragment_oring.setVisibility(View.VISIBLE);
                layout_xring.setVisibility(View.GONE);
                layout_backupring.setVisibility(View.GONE);
                break ;
            case 1 :
                fragment_oring.setVisibility(View.GONE);
                layout_xring.setVisibility(View.VISIBLE);
                layout_backupring.setVisibility(View.GONE);
                break ;
            case 2 :
                fragment_oring.setVisibility(View.GONE);
                layout_xring.setVisibility(View.GONE);
                layout_backupring.setVisibility(View.VISIBLE);
                break ;

        }
    }




}
