package com.example.kingvern.miniweather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by kingvern on 17/11/22.
 */

public class ClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher{
    private Drawable mClearDrawable;

    public ClearEditText(Context context) {
        this(context,null);
    }

    public ClearEditText(Context context, AttributeSet attrs){
        this(context,attrs,android.R.attr.editTextStyle);

    }



//    public ClearEditText(Context context, Atri)
    public ClearEditText(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init();

    }

    private void init() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null){
            mClearDrawable = getResources().getDrawable(R.drawable.title_update);
        }
        mClearDrawable.setBounds(0,0,mClearDrawable.getIntrinsicWidth(),mClearDrawable.getIntrinsicHeight());
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
        setClearIconVisible(false);
//        setCursorVisible(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(getCompoundDrawables()[2] != null){
            if(event.getAction() == MotionEvent.ACTION_UP){
                boolean touchable = event.getX() > (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth())&& (event.getX() < ((getWidth() - getPaddingRight())));
                if(touchable){
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        setClearIconVisible(s.length() > 0);

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

//    public void setSakeAnimation(){
//        this.setAnimation(shakeAnimation(5));
//    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(hasFocus){
            setClearIconVisible(getText().length() > 0);
        }else{
            setClearIconVisible(false);
        }

    }

    protected void setClearIconVisible(boolean visible){
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],right,getCompoundDrawables()[3]);
    }
}