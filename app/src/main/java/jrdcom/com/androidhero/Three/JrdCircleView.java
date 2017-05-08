package jrdcom.com.androidhero.Three;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by longcheng on 2017/5/8.
 */

public class JrdCircleView extends View {
    //定义画圆和画弧的Paint
    Paint mCirclePaint;
    Paint mArcPaint;

    public JrdCircleView(Context context){
        this(context, null);
    }

    public JrdCircleView(Context context, AttributeSet attributeSet)
    {
        this(context, attributeSet, 0);
    }

    public JrdCircleView(Context context, AttributeSet attributeSet, int des){
        super(context, attributeSet, des);
        initPaint();
    }

    /*初始化Paint*/
    private void initPaint(){
        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.RED);

        mArcPaint = new Paint();
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(10);
        mArcPaint.setColor(Color.YELLOW);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int lenght = getMeasuredHeight();
        //画图
        //画圆
        /*
        * 圆的4个参数，中心点， 半径
        * */
        float cy = lenght/2;
        float radius = lenght/4;
        canvas.drawCircle(cy, cy, radius, mCirclePaint);

        //画弧
        /*
        * 弧的4个参数：
        * */
        RectF rectF = new RectF((float) (0.1*lenght),(float)0.1*lenght, (float)0.9*lenght,(float)0.9*lenght);
        canvas.drawArc(rectF, 0, 270, false, mArcPaint);
    }
}
