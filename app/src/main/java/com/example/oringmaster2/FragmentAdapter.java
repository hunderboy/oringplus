package com.example.oringmaster2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.oringmaster2.DimensionSearchFragement.BackupRingFragment;
import com.example.oringmaster2.DimensionSearchFragement.OringFragment;
import com.example.oringmaster2.DimensionSearchFragement.XringFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return OringFragment.newInstance();
            case 1:
                return XringFragment.newInstance();
            case 2:
                return BackupRingFragment.newInstance();
            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return 3;
    }
}

