package jrdcom.com.androidhero.Four;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jrdcom.com.androidhero.R;
import jrdcom.com.androidhero.Three.JrdToolBar;

/**
 * Created by dhcui on 2017/5/7.
 */

public class MyFourTestFragment extends Fragment {
    int mType = 0;
    private JrdToolBar jrdToolBar;
    public final static String TAG = MyFourTestFragment.class.getSimpleName();

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
        doFunction(view);
        return  view;
    }

    private int getLayoutId(){
        int layoutId = 0;
        switch (mType){
            case 0: //Shape Test, 汇总所有shape的用法
                layoutId = R.layout.four_layout_shape;
            break;

            case 1:
                layoutId = R.layout.four_layout_time;
                break;

        }
        return layoutId;
    }

    private void doFunction(View view){
        switch (mType){

        }
    }


}
