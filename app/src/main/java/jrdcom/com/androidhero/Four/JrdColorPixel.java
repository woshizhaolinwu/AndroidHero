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

import jrdcom.com.androidhero.R;

/**
 * Created by longcheng on 2017/5/15.
 */

public class JrdColorPixel  {
    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private Bitmap mBitmap;
    private String[] dataList = {"底片效果","过滤蓝色背景","还原"};
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
                case 2: //还原
                    mImageView.setImageBitmap(mBitmap);
                    break;
            }
        }
    };

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private onItemClick mOnItemCLick;

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
                mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnItemCLick != null){
                            mOnItemCLick.onClick(getPosition());
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

            if(b > r && b > g && b> 240  && (b -r )>100 && (b-g)>100){
                Log.d("zlwu", "r = "+r +"g = "+g+"b = "+b);

                j++;
                r = 255;
                b = 255;
                g = 255;
                //a = 0;
            }
            if(r > 255){r = 255;}else if(r< 0){r = 0;}
            if(g > 255){g = 255;}else if(g< 0){g = 0;}
            if(b > 255){b = 255;}else if(b< 0){b = 0;}
            //生成新的图片
            newPixel[i] = Color.argb(r, g, b, a);
        }
        bitmap.setPixels(newPixel, 0, width, 0, 0, width, heiht);
        return bitmap;
    }
}
