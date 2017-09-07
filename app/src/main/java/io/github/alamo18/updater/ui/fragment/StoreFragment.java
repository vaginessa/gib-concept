package io.github.alamo18.updater.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.alamo18.updater.R;

/**
 * Created by alamo with his pizza on 7/9/2017.
 */

public class StoreFragment extends Fragment {
    @BindView(R.id.button1) Button button1;
    @BindView(R.id.button2) Button button2;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomtab_store_fragment, container, false);
        ButterKnife.bind(this, view);
        // add stuff
        return view;
    }
}