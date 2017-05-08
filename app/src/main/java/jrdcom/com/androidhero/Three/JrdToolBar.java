package jrdcom.com.androidhero.Three;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jrdcom.com.androidhero.R;

/**
 * Created by longcheng on 2017/5/8.
 */

public class JrdToolBar extends RelativeLayout {

    /*
    *  toolbar的style
    * */
    private String title;
    private int titleColor;
    private float titleSize;
    private int leftColor;
    private String leftString;
    private String rightString;
    private int rightColor;

    /*Title, leftButton, rightButton*/
    TextView mTitle;
    Button mLeftButton;
    Button mRightButton;

    //Listener
    private toolbarButtonListener mToolBarButtonListener;
    public JrdToolBar(Context context)
    {
        this(context, null);
    }

    public JrdToolBar(Context context, AttributeSet attributeSet){
        this(context, attributeSet, 0);
    }

    public JrdToolBar(Context context, AttributeSet attributeSet, int Des){
        super(context, attributeSet, Des);
        initView(context, attributeSet);
    }

    private void initView(Context context,AttributeSet attributeSet){
        getDataFromStyle(context, attributeSet);
        //创建控件
        initButton(context);
        //注册监听
        addListener();
    }

    private void getDataFromStyle(Context context,AttributeSet attributeSet){
        /*从Style中获取资源*/
        TypedArray ta = context.obtainStyledAttributes(attributeSet,
                R.styleable.ToolBar);
        title = ta.getString(R.styleable.ToolBar_title);
        titleColor = ta.getColor(R.styleable.ToolBar_titleColor,0);
        titleSize = ta.getDimension(R.styleable.ToolBar_titleSize, 0);

        //left Button
        leftString = ta.getString(R.styleable.ToolBar_leftText);
        leftColor =  ta.getColor(R.styleable.ToolBar_leftColor, 0);

        //right Button
        rightColor = ta.getColor(R.styleable.ToolBar_rightColor, 0);
        rightString = ta.getString(R.styleable.ToolBar_rightText);
    }

    private void initButton(Context context){
        /*
        * 添加title
        * */
        mTitle = new TextView(context);
        mTitle.setText(title);
        mTitle.setTextSize(titleSize);
        mTitle.setTextColor(titleColor);
        //设置layout
        RelativeLayout.LayoutParams titleParames = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParames.addRule(RelativeLayout.CENTER_IN_PARENT);
        mTitle.setLayoutParams(titleParames);
        addView(mTitle);

        /*
        *  添加leftButton
        * */
        mLeftButton = new Button(context);
        mLeftButton.setText(leftString);
        mLeftButton.setTextColor(leftColor);
        //设置Layout
        RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mLeftButton.setLayoutParams(leftParams);
        addView(mLeftButton);

        /*
        *  添加rightButton
        * */
        mRightButton = new Button(context);
        mRightButton.setText(rightString);
        mRightButton.setTextColor(rightColor);
        //设置Layout
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mRightButton.setLayoutParams(rightParams);
        addView(mRightButton);
    }

    private void addListener(){
        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mToolBarButtonListener != null){
                    mToolBarButtonListener.onLeftButtonClick();
                }
            }
        });

        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mToolBarButtonListener != null){
                    mToolBarButtonListener.onRightButtonClick();
                }
            }
        });
    }



    public void setToolBarListener(toolbarButtonListener listener){
        mToolBarButtonListener = listener;
    }
    //定义接口方便
    public interface toolbarButtonListener{
        void onLeftButtonClick();
        void onRightButtonClick();
    }
}
