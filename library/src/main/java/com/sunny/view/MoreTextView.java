/*
 * Created by sunny on 18-2-8.
 * <p>
 *----------Dragon be here!----------/
 *      ┏┓       ┏┓
 *     ┏┛┻━━━━━━━┛┻┓
 *     ┃　　　　    ┃
 *     ┃     ━     ┃
 *     ┃   ┳┛ ┗┳   ┃
 *     ┃　　　　    ┃
 *     ┃     ┻     ┃
 *     ┃　　　　    ┃
 *     ┗━━━┓   ┏━━━┛
 *         ┃   ┃神兽保佑
 *         ┃   ┃代码无BUG！
 *         ┃   ┗━━━━━━━━━━┓
 *         ┃   　　　 　   ┣┓
 *         ┃              ┏┛
 *         ┗┓┓┏━━━━━━━━┳┓┏┛
 *          ┃┫┫        ┃┫┫
 *          ┗┻┛        ┗┻┛
 * ━━━━━━神兽出没━━━━━━by:coder-sunny
 *
 *
 * 展开收起的 MoreTextView
 *
 */

package com.sunny.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import demo.sunny.com.mylibrary.R;

public class MoreTextView extends LinearLayout implements CompoundButton.OnCheckedChangeListener,ViewTreeObserver.OnPreDrawListener{


    private int mMoreSwitchTextSize = 12;

    private int mMoreTextSize = 12;

    private int mMoreTextColor = Color.parseColor("#3c3c40");

    private int mMoreSwitchTextColor = Color.parseColor("#fc9400");

    private int mMaxHeight,mMinHeight,mMaxLine;

    private int mMinLine = 3;

    private int mLineHeight = -1;


    private TextView mTextView;
    private CheckBox mCheckBox;

    private CharSequence [] mMoreSwitchHints = {"展开","收起"};


    private Drawable mMoreSwitchDrawable = new ColorDrawable(Color.parseColor("#00000000"));


    public MoreTextView(Context context) {
        this(context,null);
    }

    public MoreTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MoreTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MoreTextView, defStyleAttr, 0);
        mMoreSwitchTextColor = attributes.getColor(R.styleable.MoreTextView_moreSwitchTextColor, mMoreSwitchTextColor);
        mMoreTextColor = attributes.getColor(R.styleable.MoreTextView_moreTextColor,mMoreTextColor);
        mMinLine = attributes.getInt(R.styleable.MoreTextView_minLine, mMinLine);
        mMoreTextSize = attributes.getDimensionPixelSize(R.styleable.MoreTextView_moreTextSize, mMoreTextSize);
        mMoreSwitchTextSize = attributes.getDimensionPixelSize(R.styleable.MoreTextView_moreSwitchTextSize, mMoreSwitchTextSize);
        mMoreSwitchDrawable = attributes.getDrawable(R.styleable.MoreTextView_moreSwitchDrawable);
        attributes.recycle();
        init();
    }



    private void init() {
        setOrientation(VERTICAL);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_more, this, true);
        mTextView = inflate.findViewById(R.id.tv_more_content);
        mCheckBox = inflate.findViewById(R.id.cb_more_checked);
        mTextView.setMinLines(mMinLine);
        mTextView.setTextColor(mMoreTextColor);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,mMoreTextSize);
        mCheckBox.setTextColor(mMoreSwitchTextColor);
        mCheckBox.setTextSize(TypedValue.COMPLEX_UNIT_PX,mMoreSwitchTextSize);
        setSwitchDrawable(mMoreSwitchDrawable);
        mTextView.getViewTreeObserver().addOnPreDrawListener(this);
    }


    /**
     *
     * 设置文本
     *
     * @param sequence sequence
     */
    public void setText(CharSequence sequence){
        mTextView.setText(sequence);
        mTextView.getViewTreeObserver().addOnPreDrawListener(this);
        mCheckBox.setOnCheckedChangeListener(this);
    }


    /**
     *
     * 设置按钮的样式
     *
     *
     *
     * @param drawable drawable
     */
    public void setSwitchStyle(Drawable drawable){
        if (drawable == null) {
            throw  new NullPointerException("drawable is null !!!!!!!!");
        }

        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mCheckBox.setCompoundDrawables(drawable,null,null,null);
    }


    /**
     *
     * 设置按钮的样式
     *
     *
     *
     * @param drawable drawable
     */
    private void setSwitchDrawable(Drawable drawable){
        if (drawable == null) {
            return;
        }

        setSwitchStyle(drawable);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        mTextView.clearAnimation();

        final int deltaValue;
        final int startValue = mTextView.getHeight();
        if (b) {
            //展开
            deltaValue = mMaxHeight - startValue;
        } else {
            //缩进
            deltaValue = mMinHeight - startValue;
        }
        setMoreSwichHints();
        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                mTextView.setHeight((int) (startValue + deltaValue * interpolatedTime));
                if (interpolatedTime == 0) {
                    t.clear();
                }
            }
        };
        animation.setDuration(350);
        mTextView.startAnimation(animation);
    }

    private void setMoreSwichHints() {

        if (noDrawable()) {

            if (mCheckBox.isChecked()) {
                mCheckBox.setText(mMoreSwitchHints[1]);
            }else {
                mCheckBox.setText(mMoreSwitchHints[0]);
            }
        }
    }

    @Override
    public boolean onPreDraw() {
        mTextView.getViewTreeObserver().removeOnPreDrawListener(this);
        mMaxLine = mTextView.getLineCount(); //获取当前最大的line

        if (mMaxLine != 0) { // view初始化时  这个值肯定为0
            //计算最大的高度
            //得到行高
            int tempLineHeight = mTextView.getHeight() / mMaxLine;

            if (mLineHeight == -1 || tempLineHeight > mLineHeight) {
                mLineHeight = tempLineHeight;
            }
        }

        // 求出最大的高度
        mMaxHeight = mLineHeight * mMaxLine;

        //如果点击了展开  此时更新数据时 我们不想自动关闭它

        if (mCheckBox.isChecked()) {
            mTextView.setHeight(mMaxHeight);
            return false;
        }


        if (mMaxLine > mMinLine) {

            mTextView.setLines(mMinLine); //设置为最小的行数

            //获取最小行的高度
            mTextView.post(new Runnable() {
                @Override
                public void run() {
                    mMinHeight = mTextView.getHeight();
                }
            });

            if (noDrawable()) {
                mCheckBox.setText(mMoreSwitchHints[0]);
            }

            mCheckBox.setVisibility(VISIBLE);
            return false;
        }else {
            if (noDrawable()) {
                mCheckBox.setText(mMoreSwitchHints[1]);
            }
            mCheckBox.setVisibility(GONE);
        }

        return true;
    }



    private boolean noDrawable(){
        return mMoreSwitchDrawable == null;
    }


}
