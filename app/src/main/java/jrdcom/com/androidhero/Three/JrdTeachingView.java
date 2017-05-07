package jrdcom.com.androidhero.Three;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dhcui on 2017/5/7.
 */

public class JrdTeachingView extends View {
    public JrdTeachingView(Context context){
        super(context);
    }
    public JrdTeachingView(Context context ,AttributeSet att){
        super(context, att);
    }
    public JrdTeachingView(Context context, AttributeSet att , int Des){
        super(context, att, Des);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //对View进行测量,View有两中属性，一种Mode, 一种size
        setMeasuredDimension(getMeasureWidth(widthMeasureSpec), getMeasureHeight(heightMeasureSpec));

    }

    /*长和宽*/
    private int getMeasureWidth(int widMeasureSpect)
    {
        int specMode = MeasureSpec.getMode(widMeasureSpect);
        int specSize = MeasureSpec.getSize(widMeasureSpect);
        int result = 0;
        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else{//Mode = AT_MOST
            result = 200;
            if(specMode == MeasureSpec.AT_MOST)
            result = Math.min(result, specSize);
        }
        return result;
    }

    private int getMeasureHeight(int heightMeasureSpec){
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        int result = 0;
        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else{//Mode = AT_MOST
            result = 200;
            if(specMode == MeasureSpec.AT_MOST)
                result = Math.min(result, specSize);
        }
        return result;
    }



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
