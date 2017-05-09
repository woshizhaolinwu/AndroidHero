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
    private JrdToolBar jrdToolBar;
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
        doFunction(view);
        return  view;
    }

    private int getLayoutId(){
        int layoutId = 0;
        switch (mType){
            case 0: //Teaching view
                layoutId = R.layout.three_layout_teaching;
            break;
            case 1: //My TextView
                layoutId = R.layout.three_layout_jrdtextview;
                break;
            case 2: //ToolBar
                layoutId = R.layout.three_layout_toolbar;
                break;
            case 3: //CircleView
                layoutId = R.layout.three_layout_circleview;
                break;
            case 4:
                layoutId = R.layout.three_layout_scrollview;
                break;
            case 5: //ScrollViewLayout
                layoutId = R.layout.three_layout_scrollview_group;
                break;
        }
        return layoutId;
    }

    private void doFunction(View view){
        switch (mType){
            case 2: //toolbar
                jrdToolBar = (JrdToolBar) view.findViewById(R.id.jrd_tool_bar);
                jrdToolBar.setToolBarListener(new JrdToolBar.toolbarButtonListener() {
                    @Override
                    public void onLeftButtonClick() {
                        getFragmentManager().popBackStack();
                    }

                    @Override
                    public void onRightButtonClick() {

                    }
                });
                break;
        }
    }


}
