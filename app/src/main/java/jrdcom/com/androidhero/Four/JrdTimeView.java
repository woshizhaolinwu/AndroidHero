package jrdcom.com.androidhero.Four;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by longcheng on 2017/5/10.
 */

public class JrdTimeView extends View {
    //定义表盘的paint
    private Paint mPaintCircle;
    private Paint mPaintLine;
    private Paint mPaintShortLine;
    private int mWidth;
    private int mHeight;

    public JrdTimeView(Context context){
        this(context, null);
    }

    public JrdTimeView(Context context, AttributeSet attributeSet){
        this(context, attributeSet, 0);
    }

    public JrdTimeView(Context context, AttributeSet attributeSet, int des){
        super(context, attributeSet, des);
        initPaint();
    }

    private void initPaint(){
        mPaintCircle = new Paint();
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setStrokeWidth(2);
        mPaintCircle.setColor(Color.RED);

        mPaintLine = new Paint();
        mPaintLine.setStyle(Paint.Style.FILL);
        mPaintLine.setStrokeWidth(2);
        mPaintLine.setColor(Color.BLUE);

        mPaintShortLine = new Paint();
        mPaintShortLine.setStyle(Paint.Style.FILL);
        mPaintShortLine.setStrokeWidth(2);
        mPaintShortLine.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //在中心位置画一个圆
        canvas.drawCircle(mWidth/2, mHeight/2, mWidth/2, mPaintCircle);

        //利用canvas的rotate画刻度,一共24个刻度值
        for(int i = 0; i < 24; i++){
            //如果是3， 6， 9， 12， 15， 18， 21， 24，高亮加粗显示
            if(i%3 ==0){
                mPaintShortLine.setStrokeWidth(5);
                canvas.drawLine(mWidth/2, 0, mWidth/2, 5, mPaintShortLine);
            }else{
                mPaintShortLine.setStrokeWidth(3);
                canvas.drawLine(mWidth/2, 0, mWidth/2, 3, mPaintShortLine);
            }

            //旋转
            canvas.rotate(15, mWidth/2, mWidth/2);
        }

        canvas.save();
        canvas.translate(mWidth/2, mHeight/2);
        //利用canvas的translate画时针和分针
        //时针
        mPaintLine.setStrokeWidth(10);
        canvas.drawLine(0, 0, 50, 50, mPaintLine);
        //分针
        mPaintLine.setStrokeWidth(5);
        canvas.drawLine(0, 0, 100, 50, mPaintLine);
        canvas.restore();
    }
}
