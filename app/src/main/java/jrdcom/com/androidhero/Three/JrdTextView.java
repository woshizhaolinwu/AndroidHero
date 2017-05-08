package jrdcom.com.androidhero.Three;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import jrdcom.com.androidhero.R;

/**
 * Created by longcheng on 2017/5/8.
 */

public class JrdTextView extends AppCompatTextView {
    private Paint mPaint1 = new Paint();
    private Paint mPaint2 = new Paint();
    public JrdTextView(Context context){
        super(context, null);
    }

    public JrdTextView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        initPaint();
    }

    private void initPaint(){
        mPaint1.setColor(Color.RED);
        mPaint1.setStyle(Paint.Style.FILL);

        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    private void drawRect(Canvas canvas){
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        canvas.drawRect(0, 0, width, height, mPaint1);
        canvas.drawRect(10, 10, width -10, height -10, mPaint2);
        canvas.save();      //保存画布状态
        canvas.translate(10, 10); //把画布远点移到 10，10
    }
    @Override
    protected void onDraw(Canvas canvas) {
        /*在绘制文字前绘画两个重叠的矩形*/
        drawRect(canvas);
        super.onDraw(canvas);
        //canvas.restore(); //返回上一次保存的状态
    }

}
