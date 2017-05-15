package jrdcom.com.androidhero.Four;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.media.Image;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import jrdcom.com.androidhero.R;

/**
 * Created by longcheng on 2017/5/11.
 */

public class JrdColorMatrix  {
    /*控件*/
    private ImageView imageView;
    private Bitmap bitmap;
    private Button btnChange;
    private Button btnReset;
    private RecyclerView recyclerView;
    private float[] mColorMatrix = new float[20];

    //Adapter
    private EditTextAdapter mEditTextAdapter;
    //数据列表
    private List<String> mDataList;

    public JrdColorMatrix(Context context, View view){
        findView(view);
        initView(context);
        changeMatrix();
    }

    private void findView(View view){
        imageView =(ImageView)view.findViewById(R.id.color_matrix_image);
        btnChange = (Button)view.findViewById(R.id.btn_change);
        btnReset = (Button)view.findViewById(R.id.btn_reset);
        btnChange.setOnClickListener(onClickListener);
        btnReset.setOnClickListener(onClickListener);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mDataList = new ArrayList<>();
        initData();
    }

    private void initView(Context  context){
        initImage(context);
        initRecyclerView(context);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.image);
        imageView.setImageBitmap(bitmap);
    }

    private void initImage(Context context){
        Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.lufy);
        imageView.setImageBitmap(imageBitmap);
    }

    private void initRecyclerView(Context context){
        //初始化 RecyclerView
        //定义adapter
        mEditTextAdapter = new EditTextAdapter(context);
        recyclerView.setAdapter(mEditTextAdapter);
        //定义layoutManager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));


    }


    //监听key事件
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_change:
                    changeMatrix();
                    break;
                case R.id.btn_reset:
                    resetMatrix();
                    break;
            }
        }
    };

    /*初始化数据*/
    private void initData(){
        for(int i = 0; i < 20 ; i++){
            /*可以取余6 的时候设为1*/
            if(i%6 ==0){
                mDataList.add("1");
            }else{
                mDataList.add("0");
            }
        }
    }
    private class EditTextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private Context mContext;
        private List<EditText> editTexts;
        public EditTextAdapter(Context context){
            mContext = context;
            editTexts= new ArrayList<>();
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.four_edittext_item, parent,false);
            JrdViewHolder jrdViewHolder  = new JrdViewHolder(view);
            return  jrdViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((JrdViewHolder)holder).editText.setText(mDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        private class JrdViewHolder extends RecyclerView.ViewHolder{
            private EditText editText;
            public JrdViewHolder(View view){
                super(view);
                editText = (EditText)view.findViewById(R.id.edit_text);
                editTexts.add(editText);
            }
        }

        //获取所有edit的值
        public String getEditText(int index){
            if(editTexts.size() == 0){
                return null;
            }
            return  editTexts.get(index).getText().toString();
        }

        public void setEditText(String editText, int index){
            editTexts.get(index).setText(editText);
        }
    }

    private void changeMatrix(){
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        getMatrix();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(mColorMatrix);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap,0, 0, paint);
        imageView.setImageBitmap(bitmap1);
    }
    private void resetColorMatrix(){
        for(int i = 0; i < 20; i++){
            mColorMatrix[i] =Float.valueOf(mDataList.get(i));
        }
    }
    private void resetMatrix(){
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        resetColorMatrix();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(mColorMatrix);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap,0, 0, paint);
        imageView.setImageBitmap(bitmap1);

        //恢复值
        for(int i = 0 ; i < 20; i++){
            mEditTextAdapter.setEditText(mDataList.get(i),i);
        }
    }

    private void getMatrix(){
        for(int i = 0; i < 20; i++){
            String string = mEditTextAdapter.getEditText(i);

            if(string == null)
            {
                mColorMatrix[i] =Float.valueOf(mDataList.get(i));
            }else{
                mColorMatrix[i] =Float.valueOf(mEditTextAdapter.getEditText(i));
            }
        }
    }


}
