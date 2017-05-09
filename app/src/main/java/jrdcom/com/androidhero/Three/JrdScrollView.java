package jrdcom.com.androidhero.Three;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by longcheng on 2017/5/9.
 * 这个View是用作调试Scroller方法的
 */

public class JrdScrollView extends View{
    private Paint drawPaint;
    private Scroller mScroller;
    private float start_x = 0;
    private float start_y = 0;
    private int last_x = 0;
    private int last_y = 0;
    /*构造函数*/
    public JrdScrollView(Context context){
        this(context, null);
    }

    public JrdScrollView(Context context, AttributeSet attributeSet){
        this(context, attributeSet, 0);
    }

    public JrdScrollView(Context context, AttributeSet attributeSet, int desi){
        super(context, attributeSet, desi);
        initPaint();
        initScroller(context);
    }

    private void initPaint(){
        drawPaint = new Paint();
        drawPaint.setColor(Color.YELLOW);
        drawPaint.setStyle(Paint.Style.FILL);
    }

    private void initScroller(Context context){
        mScroller = new Scroller(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawRect(0, 0, 200, 200, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //记录起始点，通过Scroll记录当前快移动位置
                last_x = (int) event.getX();
                last_y = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                //up的时候在返回
                mScroller.startScroll(getScrollX(), getScrollY(), -getScrollX(),-getScrollY() ,1000);
                invalidate();
                //smoothScrollTo(0, 0);
                break;
            case MotionEvent.ACTION_MOVE:
                //进行移动
                //在move里面根据手指移动
                int x = (int)event.getX();
                int y = (int)event.getY();
                scrollBy(last_x -x, last_y - y);
                last_x = x;
                last_y = y;
                break;
        }
        return true;
    }

    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }
    public void smoothScrollBy(int dx, int dy) {
        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy,1000);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
        super.computeScroll();
    }
}
