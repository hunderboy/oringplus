package o2corn.oringclone.oringmaster2;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import o2corn.oringclone.oringmaster2.DimensionSearchFragement.BackupRingFragment;
import o2corn.oringclone.oringmaster2.DimensionSearchFragement.OringFragment;
import o2corn.oringclone.oringmaster2.DimensionSearchFragement.XringFragment;

public class FragmentAdapter extends FragmentPagerAdapter  {

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

//    @Override
//    public void destroyItem(View container, int position, Object object) {
//        // TODO Auto-generated method stub
//        super.destroyItem(container, position, object);
//    }


    @Override
    public int getCount() {
        return 3;
    }
}

