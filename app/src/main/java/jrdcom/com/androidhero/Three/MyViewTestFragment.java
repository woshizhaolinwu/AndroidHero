package jrdcom.com.androidhero.Three;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jrdcom.com.androidhero.R;

/**
 * Created by dhcui on 2017/5/7.
 */

public class MyViewTestFragment extends Fragment {
    int mType = 0;
    public final static String TAG = MyViewTestFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mType = bundle.getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return  view;
    }

    private int getLayoutId(){
        int layoutId = 0;
        switch (mType){
            case 0:
            layoutId = R.layout.three_layout_teaching;
        }
        return layoutId;
    }
}
