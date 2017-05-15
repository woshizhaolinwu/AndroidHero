package jrdcom.com.androidhero.Four;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import jrdcom.com.androidhero.R;

/**
 * Created by longcheng on 2017/5/15.
 */

public class JrdColorPixel  {
    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private Bitmap mBitmap;
    private String[] dataList = {"底片效果","过滤蓝色背景","老照片效果","浮雕效果","还原"};
    private Context mContext;
    public JrdColorPixel(Context context, View view){
        mContext = context;
        findViewId(view);
        initView(context);
    }

    private void findViewId(View view){
        mImageView = (ImageView)view.findViewById(R.id.color_matrix_image);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
    }

    private void initView(Context context){
        mBitmap = (Bitmap) BitmapFactory.decodeResource(context.getResources(), R.mipmap.lufy);
        mImageView.setImageBitmap(mBitmap);

        //设置recyclerView
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        mRecyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置点击回调
        recyclerAdapter.setmOnItemCLick(onItemClick);
    }

    private onItemClick onItemClick = new onItemClick() {
        @Override
        public void onClick(int position) {
            switch (position){
                case 0://底片效果
                    Bitmap bitmap = handleBitmapDiPian(mBitmap);
                    mImageView.setImageBitmap(bitmap);
                    break;
                case 1: //过滤蓝色背景
                    Bitmap bitmap1 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.image);
                    mImageView.setImageBitmap(handleBitmapBlue(bitmap1));
                    break;
                case 2: //老照片效果
                    Bitmap bitmap2 = handleBitmapOldImage(mBitmap);
                    mImageView.setImageBitmap(bitmap2);
                    break;
                case 3: //浮雕效果
                    Bitmap bitmap3 = handleBitmapFuDiao(mBitmap);
                    mImageView.setImageBitmap(bitmap3);

                    break;
                case 4: //还原
                    mImageView.setImageBitmap(mBitmap);
                    break;
            }
        }
    };

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private onItemClick mOnItemCLick;
        private List<TextView> mTextViewList;
        public RecyclerAdapter(){
            mTextViewList = new ArrayList<>();
        }
        @Override
        public int getItemCount() {
            return dataList.length;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.four_layout_recycler_item,parent,false);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

            return recyclerViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((RecyclerViewHolder)holder).mTextView.setText(dataList[position]);

        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder{
            private TextView mTextView ;
            public RecyclerViewHolder(View view){
                super(view);
                mTextView = (TextView)view.findViewById(R.id.text_view);
                mTextView.setSelected(true);
                mTextViewList.add(mTextView);
                mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getPosition();
                        int size = mTextViewList.size();
                        for(int i = 0; i < size;i ++){
                            TextView textView = mTextViewList.get(i);

                            if(i == position){
                                textView.setBackground(mContext.getDrawable(R.drawable.bg_btn_selected));
                            }else{
                                textView.setBackground(mContext.getDrawable(R.drawable.bg_btn_normal));
                            }
                        }
                        if(mOnItemCLick != null){
                            mOnItemCLick.onClick(position);
                        }
                    }
                });
            }
        }

        public void setmOnItemCLick(onItemClick click){
            mOnItemCLick = click;
        }
    }

    public interface onItemClick{
        void onClick(int position);
    }


    /*底片效果*/
    private Bitmap handleBitmapDiPian(Bitmap bm){
        int width = bm.getWidth();
        int heiht = bm.getHeight();
        int color;
        int r, g, b, a;
        Bitmap bitmap = Bitmap.createBitmap(width, heiht, Bitmap.Config.ARGB_8888);

        int oldPixel[] = new int[width*heiht];
        int newPixel[] = new int[width*heiht];

        bm.getPixels(oldPixel, 0, width, 0,0, width,heiht);

        //将oldPixed转化为new
        for(int i = 0; i < width*heiht; i++){
            color = oldPixel[i];  //像素点的颜色
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);
            /*反转*/
            r = 255-r;
            g = 255-g;
            b = 255-b;

            if(r > 255){r = 255;}else if(r< 0){r = 0;}
            if(g > 255){g = 255;}else if(g< 0){g = 0;}
            if(b > 255){b = 255;}else if(b< 0){b = 0;}
            //生成新的图片
            newPixel[i] = Color.argb(r, g, b, a);

        }
        bitmap.setPixels(newPixel, 0, width, 0, 0, width, heiht);
        return bitmap;
    }

    //过滤蓝色背景
    private Bitmap handleBitmapBlue(Bitmap bm){
        int width = bm.getWidth();
        int heiht = bm.getHeight();
        int color;
        int r, g, b, a;
        int j = 0;
        Bitmap bitmap = Bitmap.createBitmap(width, heiht, Bitmap.Config.ARGB_8888);

        int oldPixel[] = new int[width*heiht];
        int newPixel[] = new int[width*heiht];

        bm.getPixels(oldPixel, 0, width, 0,0, width,heiht);

        //将oldPixed转化为new
        for(int i = 0; i < width*heiht; i++){
            color = oldPixel[i];  //像素点的颜色
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);
            /*反转*/
            //r = 255-r;
            //g = 255-g;
            //b = 255-b;

            if(b > r && b > g && b> 208 && r <150 && g <150){
                Log.d("zlwu", "r = "+r +"g = "+g+"b = "+b);

                j++;
                /*r = 255;
                b = 255;
                g = 255;*/
                a = 0;
            }
            if(r > 255){r = 255;}else if(r< 0){r = 0;}
            if(g > 255){g = 255;}else if(g< 0){g = 0;}
            if(b > 255){b = 255;}else if(b< 0){b = 0;}
            //生成新的图片
            newPixel[i] = Color.argb(a, r, g, b);
        }
        bitmap.setPixels(newPixel, 0, width, 0, 0, width, heiht);
        return bitmap;
    }

    //过滤蓝色背景
    private Bitmap handleBitmapOldImage(Bitmap bm){
        int width = bm.getWidth();
        int heiht = bm.getHeight();
        int color;
        int r, g, b, a;
        int r1, g1, b1;
        int j = 0;
        Bitmap bitmap = Bitmap.createBitmap(width, heiht, Bitmap.Config.ARGB_8888);

        int oldPixel[] = new int[width*heiht];
        int newPixel[] = new int[width*heiht];

        bm.getPixels(oldPixel, 0, width, 0,0, width,heiht);

        //将oldPixed转化为new
        for(int i = 0; i < width*heiht; i++){
            color = oldPixel[i];  //像素点的颜色
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);
            /*反转*/
            r1 = (int)(0.393*r+0.769*g+0.189*b);
            g1 = (int)(0.349*r+0.686*g+0.168*b);
            b1 = (int)(0.272*r+0.534*g+0.131*b);


            r1 = checkValidRGB(r1);//if(r > 255){r = 255;}else if(r< 0){r = 0;}
            b1 = checkValidRGB(b1);//if(g > 255){g = 255;}else if(g< 0){g = 0;}
            g1 = checkValidRGB(g1);//if(b > 255){b = 255;}else if(b< 0){b = 0;}
            //生成新的图片
            newPixel[i] = Color.argb(a, r1, g1, b1);
        }
        bitmap.setPixels(newPixel, 0, width, 0, 0, width, heiht);
        return bitmap;
    }

    //浮雕效果
    private Bitmap handleBitmapFuDiao(Bitmap bm){
        int width = bm.getWidth();
        int heiht = bm.getHeight();
        int color;
        int current_color;
        int r, g, b, a;
        int r1, g1, b1;
        int j = 0;
        Bitmap bitmap = Bitmap.createBitmap(width, heiht, Bitmap.Config.ARGB_8888);

        int oldPixel[] = new int[width*heiht];
        int newPixel[] = new int[width*heiht];

        bm.getPixels(oldPixel, 0, width, 0,0, width,heiht);

        //将oldPixed转化为new
        for(int i = 1; i < width*heiht; i++){
            color = oldPixel[i-1];
            r1 = Color.red(color);
            g1 = Color.green(color);
            b1 = Color.blue(color);
            a = Color.alpha(color);

            current_color = oldPixel[i];
            r = Color.red(current_color);
            g = Color.green(current_color);
            b = Color.blue(current_color);

            r1 = (r1 -r +127);
            g1 = (g1 - g +127);
            b1 = (b1 - b +127);

            r1 = checkValidRGB(r1);//if(r > 255){r = 255;}else if(r< 0){r = 0;}
            b1 = checkValidRGB(b1);//if(g > 255){g = 255;}else if(g< 0){g = 0;}
            g1 = checkValidRGB(g1);//if(b > 255){b = 255;}else if(b< 0){b = 0;}
            //生成新的图片
            newPixel[i] = Color.argb(a,r1, g1, b1);
        }
        bitmap.setPixels(newPixel, 0, width, 0, 0, width, heiht);
        return bitmap;
    }

    private int checkValidRGB(int value){
        if(value > 255){value = 255;}else if(value< 0){value = 0;}
        return value;
    }
}
