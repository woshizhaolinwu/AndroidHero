package jrdcom.com.androidhero.Four;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import jrdcom.com.androidhero.R;

/**
 * Created by longcheng on 2017/5/11.
 */

public class JrdColorPrimary {
    /*ColorPrimary*/
    private ImageView colorImage;
    private SeekBar colorSeekBarHue;
    private SeekBar colorSeekBarSaturation;
    private SeekBar colorSeekBarLight;
    private final static int MAX_VALUE = 255;
    private final static int MID_VALUE = 127;
    private float mHue = 0;
    private float mSturation = 0;
    private float mLight = 0;
    private Bitmap bitmap;
    private Context mContext;
    public JrdColorPrimary(Context context, View view){
        mContext = context;
        registerColorPrimary(context, view);
    }

    /*ColorPrimary*/
    private void registerColorPrimary(Context context,View view){
        //创建bitmap
        colorImage = (ImageView)view.findViewById(R.id.color_image);
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.lufy);
        colorImage.setImageBitmap(bitmap);
        /*设置Listener后在设置值，这样才会有初始值*/
        colorSeekBarHue = (SeekBar)view.findViewById(R.id.seekbar_hue);
        colorSeekBarHue.setOnSeekBarChangeListener(onSeekBarChangeListener);
        colorSeekBarHue.setMax(MAX_VALUE);
        colorSeekBarHue.setProgress(MID_VALUE);

        colorSeekBarSaturation =(SeekBar)view.findViewById(R.id.seekbar_saturation);
        colorSeekBarSaturation.setOnSeekBarChangeListener(onSeekBarChangeListener);
        colorSeekBarSaturation.setMax(MAX_VALUE);
        colorSeekBarSaturation.setProgress(MID_VALUE);

        colorSeekBarLight = (SeekBar)view.findViewById(R.id.seekbar_light);
        colorSeekBarLight.setOnSeekBarChangeListener(onSeekBarChangeListener);
        colorSeekBarLight.setMax(MAX_VALUE);
        colorSeekBarLight.setProgress(MID_VALUE);
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()){
                /*
                *  这边的计算方法是关键的
                * */
                case R.id.seekbar_hue:
                    //调整色调
                    mHue = (progress -MID_VALUE)*1.0f/MID_VALUE*180;
                    break;
                case R.id.seekbar_saturation:
                    mSturation = progress*1.0f/MID_VALUE;
                    break;
                case R.id.seekbar_light:
                    mLight= progress*1.0f/MID_VALUE;
                    break;
            }

            //设置图像

            //Bitmap imageBm = ((BitmapDrawable)colorImage.getDrawable()).getBitmap();
            Bitmap bm = getColorMatrixBitMap(bitmap, mHue, mSturation, mLight);
            colorImage.setImageBitmap(bm);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private Bitmap getColorMatrixBitMap(Bitmap bm, float hue, float sturation, float light){
        //创建一块空白的bitmap
        Bitmap bitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        //创建画布，传入bitmap,后面绘画则都会生成bitmap
        Canvas canvas = new Canvas(bitmap);

        //对ColorMatrix进行设置
        //色调
        ColorMatrix hueColorMatrix = new ColorMatrix();
        hueColorMatrix.setRotate(0, hue);
        hueColorMatrix.setRotate(1, hue);
        hueColorMatrix.setRotate(2, hue);

        //饱和度
        ColorMatrix staColorMatrix = new ColorMatrix();
        staColorMatrix.setSaturation(sturation);

        //亮度
        ColorMatrix lightColorMatrix = new ColorMatrix();
        lightColorMatrix.setScale(light, light, light, 1);

        //三者整合在一起
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.postConcat(hueColorMatrix);
        colorMatrix.postConcat(staColorMatrix);
        colorMatrix.postConcat(lightColorMatrix);

        //这个很关键， 需要paint设置ColorFilter
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        canvas.drawBitmap(bm, 0, 0, paint);

        //返回bmp
        return bitmap;
    }
}
