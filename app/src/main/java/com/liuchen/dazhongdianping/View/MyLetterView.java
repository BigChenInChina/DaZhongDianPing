package com.liuchen.dazhongdianping.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.liuchen.dazhongdianping.R;

/**
 * 城市分组的快速定位
 * Created by Administrator on 2017/6/23 0023.
 */

public class MyLetterView extends View{
    private String[] letters ={"热门","A","B","C","D","E","F","G",
            "H","I","J","K","L","M","N","O","P","Q","R","S","T",
            "U","V","W","X","Y","Z"};
    Paint paint;
    OnTouchLetterListener listener;
    int letterColor;


    public MyLetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = context.obtainStyledAttributes(attrs,R.styleable.MyLetterView);
        letterColor = t.getColor(R.styleable.MyLetterView_letter_color,Color.BLACK);
        t.recycle();
        initialPaint();
    }
    public void setOnTouchLetterListener(OnTouchLetterListener listener){
        this.listener = listener;
    }

    private void initialPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,14,getResources().getDisplayMetrics());
        paint.setTextSize(size);
        paint.setColor(letterColor);
    }

    /**
     * 是用来设定MyLetterView尺寸（宽高）
     * 并不一定需要重写
     * View的onMeasure已经有了很多的设定尺寸的代码，可以使用
     * 只有当View的onMeasure设定尺寸的代码逻辑不能满足实际要求时
     * 才有必要进行"改写"
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST){
            int lPadding = getPaddingLeft();
            int rPadding = getPaddingRight();
            int contentWidth = 0;
            for(int i = 0 ;i <letters.length;i++){
                String letter = letters[i];
                Rect bounds = new Rect();
                paint.getTextBounds(letter,0,letter.length(),bounds);
                if (bounds.width() > contentWidth){
                    contentWidth = bounds.width();
                }
            }
            int size = lPadding + contentWidth + rPadding;
            setMeasuredDimension(size,MeasureSpec.getSize(heightMeasureSpec));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();//控件宽度
        int height = getHeight();//控件高度
        for (int i = 0;i < letters.length;i++ ){
            String letter=letters[i];
            Rect bounds = new Rect();
            paint.getTextBounds(letter,0,letter.length(),bounds);
            float x = width/2 - bounds.width()/2;
            float y = height/letters.length/2 + bounds.height()/2 +i*height/letters.length;
            canvas.drawText(letter,x,y,paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setBackgroundColor(Color.GRAY);
                if (listener != null){
                    float y = event.getY();
                    int idx = (int)((y*letters.length)/getHeight());
                    if (idx >= 0 && idx < letters.length){
                        String letter = letters[idx];
                        listener.onTouchLetter(this,letter);
                    }
                }
            break;

            default:
                setBackgroundColor(Color.TRANSPARENT);
            break;
        }

        return true;
    }

    public interface OnTouchLetterListener{
        void onTouchLetter(MyLetterView view,String letter);
    }
}
