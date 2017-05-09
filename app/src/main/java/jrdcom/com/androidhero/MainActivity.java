package jrdcom.com.androidhero;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import jrdcom.com.androidhero.Three.MyViewTestFragment;

public class MainActivity extends AppCompatActivity {

    private List<String> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        ListFragment listFragment = new ListFragment();
        //替换成listFragment
        getFragmentManager().beginTransaction()
                .add(R.id.main_screen, listFragment)
                .addToBackStack(ListFragment.TAG)
                .commit();
        //list设置adapter
        initData();
        listFragment.setListAdapter(this,dataList);
        //注册回调
        listFragment.setListOnClick(mListOnCLick);
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 1){
            getFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }
    /*Main list*/
    private ListFragment.ListOnClick mListOnCLick = new ListFragment.ListOnClick() {
        @Override
        public void onClick(int position) {
            //对个条目进行点击实践
            switch (position){
                case JrdCommon.CHAPTER_THREE: //第三章
                    //进入第三张的界面,第三章也是这个list
                    ListFragment listFragment = new ListFragment();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.main_screen, listFragment)
                            .addToBackStack(ListFragment.TAG)
                            .commit();
                    listFragment.setListAdapter(MainActivity.this, getChapterThreeList());
                    listFragment.setListOnClick(mThreeListOnClick);
                    break;
            }
        }
    };

    /*第三章的list*/
    private ListFragment.ListOnClick mThreeListOnClick = new ListFragment.ListOnClick() {
        @Override
        public void onClick(int position) {
            /*第三章中的所有的View都是传递到一个fragment里面*/
            MyViewTestFragment myViewTestFragment = new MyViewTestFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", position);
            myViewTestFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_screen, myViewTestFragment)
                    .addToBackStack(MyViewTestFragment.TAG)
                    .commit();
        }
    };


    private void initData(){
        dataList.add("第三章");
    }

    /*定义第三章的list*/
    private List<String> getChapterThreeList(){
        List<String> list = new ArrayList<>();
        list.add("Teaching View");
        list.add("My TextView");
        list.add("JrdToolBar");
        list.add("JrdCircleView");
        list.add("JrdScrollView");
        list.add("JrdScrollViewGroup");
        return list;
    }

}
