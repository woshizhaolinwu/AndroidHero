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

public class JrdLayoutView extends View {
    private Paint mFirstPaint;
    private Paint mSecondPaint;
    private static final int LAYERS_FLAGS=Canvas.MATRIX_SAVE_FLAG|
            Canvas.CLIP_SAVE_FLAG |
            Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
            Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
            Canvas.CLIP_TO_LAYER_SAVE_FLAG;

    private int mWidth;
    private int mHeight;
    public JrdLayoutView(Context context){
        this(context, null);
    }

    public JrdLayoutView(Context context, AttributeSet attributeSet){
        this(context, attributeSet, 0);
    }

    public JrdLayoutView(Context context, AttributeSet attributeSet, int des){
        super(context, attributeSet, des);
        initPaint();
    }

    private void initPaint(){
        mFirstPaint = new Paint();
        mFirstPaint.setStyle(Paint.Style.FILL);
        mFirstPaint.setColor(Color.RED);

        mSecondPaint = new Paint();
        mSecondPaint.setStyle(Paint.Style.FILL);
        mSecondPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画4个圆进行对比，用图层的和不用图层的
        //左半部分画两个不用图层的圆
        canvas.drawCircle(mWidth/4, mHeight/2 -50, mWidth/4, mFirstPaint);
        canvas.drawCircle(mWidth/4, mHeight/2 +50, mWidth/4, mSecondPaint);

        //有半部分画两个用图层的圆
        canvas.drawCircle(mWidth*3/4, mHeight/2 -50, mWidth/4, mFirstPaint);
        canvas.saveLayerAlpha(mWidth/2, 0, mWidth, mHeight, 127,LAYERS_FLAGS);
        canvas.drawCircle(mWidth*3/4, mHeight/2 +50, mWidth/4, mSecondPaint);
        canvas.restore();
    }
}
