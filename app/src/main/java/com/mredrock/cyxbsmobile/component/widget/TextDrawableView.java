package com.mredrock.cyxbsmobile.component.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mredrock.cyxbsmobile.R;
import com.mredrock.cyxbsmobile.util.LogUtils;

/**
 * Created by Stormouble on 16/5/5.
 */
public class TextDrawableView extends TextView {
    private static final String TAG = LogUtils.makeLogTag(TextDrawableView.class);

    private int drawableWidth = 0;
    private int drawableHeight = 0;

    public TextDrawableView(Context context) {
        this(context, null);
    }

    public TextDrawableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextDrawableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttributeSet(context, attrs, defStyleAttr);

        setupDrawable();
    }

    private void parseAttributeSet(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(
                attrs, R.styleable.TextDrawableView, defStyleAttr, 0);

        drawableWidth = array.getDimensionPixelOffset(
                R.styleable.TextDrawableView_drawable_width, 0);
        drawableHeight = array.getDimensionPixelOffset(
                R.styleable.TextDrawableView_drawable_height, 0);
        array.recycle();
    }

    private void setupDrawable() {
        Drawable[] drawableList = getCompoundDrawables();
        if (drawableList != null) {
            for (int i = 0; i < drawableList.length; i++) {
                Drawable drawable = drawableList[0];
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawableWidth, drawableHeight);
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Drawable[] drawables = getCompoundDrawables();
//        if (drawables != null) {
//            Drawable drawableLeft = drawables[0];
//            if (drawableLeft != null) {
//                float textWidth = getPaint().measureText(getText().toString());
//                int drawablePadding = getCompoundDrawablePadding();
//                int drawableWidth = 0;
//                drawableWidth = drawableLeft.getIntrinsicWidth();
//                float bodyWidth = textWidth + drawableWidth + drawablePadding;
//                canvas.translate((getWidth() - bodyWidth) / 2, 0);
//            }
//        }

        super.onDraw(canvas);
    }
}
