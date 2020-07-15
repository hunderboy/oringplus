package o2corn.oringclone.oringmaster2.DimensionSearchFragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.oringclone.oringmaster2.R;

public class BackupRingFragment extends Fragment {

    View view;

    public static BackupRingFragment newInstance() {
        BackupRingFragment tab3 = new BackupRingFragment();
        return tab3;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_backupring, container, false);
        return view;
    }

}
