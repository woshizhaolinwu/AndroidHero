package jrdcom.com.androidhero;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhcui on 2017/5/7.
 */

public class ListFragment extends android.app.ListFragment {
    private List<String> listData;//new ArrayList<>();
    public final static String TAG = ListFragment.class.getSimpleName();
    private ListOnClick mListOnClick;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initData();
    }

    private void initData(){
        listData.add("第三章，自定义控件");
    }

    public void setListAdapter(Context context, List<String> list){
        setListAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(mListOnClick != null){
            mListOnClick.onClick(position);
        }
    }

    /*设置回调*/
    public void setListOnClick(ListOnClick onClick) {
        mListOnClick = onClick;
    }

    /*定义回调接口给到Main*/
    public interface ListOnClick{
        void onClick(int position);
    }
}
