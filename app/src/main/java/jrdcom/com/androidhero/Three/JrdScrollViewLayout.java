package jrdcom.com.androidhero.Three;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by longcheng on 2017/5/9.
 */

public class JrdScrollViewLayout extends ViewGroup {
    private Scroller mScroller;
    private int mScreenHeight;
    private int mStartY = 0; //当前的偏移
    private int mLastY= 0; //这个主要做move的时候用到
    public JrdScrollViewLayout(Context context){
        this(context, null);
    }

    public JrdScrollViewLayout(Context context, AttributeSet attributeSet){
        this(context, attributeSet, 0);
    }

    public JrdScrollViewLayout(Context context, AttributeSet att, int des){
        super(context, att, des);
        initView(context);
    }

    private void initView(Context context){
        mScroller = new Scroller(context);
        mScreenHeight = getScreenHeight(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //ViewGroup的总高度
        MarginLayoutParams pa = (MarginLayoutParams)getLayoutParams();
        pa.height = mScreenHeight *count;
        setLayoutParams(pa);

        for(int i = 0; i < count; i++){
            View child = getChildAt(i);
            child.layout(l, i* mScreenHeight, r, (i+1)*mScreenHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*遍历child*/
        int count = getChildCount();
        for(int i = 0; i < count; i++){
           View child =  getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    /*主要工作就在这个TouchEvent里面:
    * Touch Move,跟随手指滑动
    * Touch Up, 如果滑动查过屏幕的1/3，则滑出整屏，否则复原
    * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mStartY = getScrollY(); //获取偏移量，计算滑动到了第几屏幕
                mLastY = (int)event.getY(); //获取当前点击的Y
                break;
            case MotionEvent.ACTION_MOVE:
                int count = getChildCount();
                int y = (int) event.getY(); //获取当前点击的Y
                int dy = (y - mLastY);
                Log.d("zlwu","startY = "+mStartY+"y ="+y+"mLastY"+mLastY);
                if((mStartY - dy) <= 0){ //如果当前没有偏移量， 则判断向上还是向下
                    if(y > mLastY){ //当前是第一页，则不需要向下滑动
                        mLastY = y;
                        return true;
                    }
                }

                if(mStartY >= (count -1 )*mScreenHeight){ //如果是最后一页
                    if(y  < mLastY){
                        mLastY = y;
                        return true;
                    }
                }

                //进行滑动
                scrollBy(0, mLastY -y);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                /*在这里就要做些判断
                * mStartY是初始的偏移量
                * mCurrentY 获取当前的偏移量
                * */
                int mCurrentY= (int)getScrollY();
                if(mCurrentY > mStartY){ //向上
                    if((mCurrentY - mStartY) > mScreenHeight/3){
                        //滚动到下一屏幕
                        mScroller.startScroll(0, mCurrentY, 0, mStartY+mScreenHeight - mCurrentY);
                    }else{
                        //回滚
                        mScroller.startScroll(0, mCurrentY, 0, mStartY -mCurrentY);
                    }

                }else if(mCurrentY < mStartY) //向上
                {
                    if((mStartY - mCurrentY) > mScreenHeight/3){
                        //滚动到上一个屏幕
                        mScroller.startScroll(0, mCurrentY, 0, (mStartY - mCurrentY)-mScreenHeight);
                    }else{
                        //回滚
                        mScroller.startScroll(0, mCurrentY, 0, mStartY - mCurrentY);
                    }
                }
                postInvalidate();

                break;
        }
        return true; //消费了 touch事件
    }

    /*
        * private函数
        * */
    private int getScreenHeight(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(0, mScroller.getCurrY());
        }
    }
}
