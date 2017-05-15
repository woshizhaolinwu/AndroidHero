package jrdcom.com.androidhero;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import jrdcom.com.androidhero.Four.MyFourTestFragment;
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
            finish();
            //super.onBackPressed();
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
                case JrdCommon.CHAPTER_FOUR: //第四章
                    //进入第四章的界面， 第四章也是这个list
                    ListFragment listFragment4 = new ListFragment();
                    getFragmentManager().beginTransaction()
                        .replace(R.id.main_screen, listFragment4)
                        .addToBackStack(ListFragment.TAG)
                        .commit();
                    listFragment4.setListAdapter(MainActivity.this, getChapterFourList());
                    listFragment4.setListOnClick(mFourListOnClick);
                    break;
            }
        }
    };

    private void initData(){
        dataList.add("第三章-控件基础");
        dataList.add("第四章-绘图基础");
    }

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

    /*第四章的list*/
    private ListFragment.ListOnClick mFourListOnClick = new ListFragment.ListOnClick() {
        @Override
        public void onClick(int position) {
            /*第三章中的所有的View都是传递到一个fragment里面*/
            MyFourTestFragment myViewTestFragment = new MyFourTestFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", position);
            myViewTestFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_screen, myViewTestFragment)
                    .addToBackStack(MyViewTestFragment.TAG)
                    .commit();
        }
    };

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

    /*定义第四章的list*/
    private List<String> getChapterFourList(){
        List<String> list = new ArrayList<>();
        list.add("ShpaeTest");
        list.add("TimeView");
        list.add("PrimaryColor");
        list.add("MatrixColor");
        list.add("ColorPixel");

        return list;
    }

}
