package com.example.largerloadvideo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.starot.larger.image.OnLargerDragListener;
import com.starot.larger.utils.LogUtils;

import cn.jzvd.JzvdStd;

public class MyVideoView extends JzvdStd {


    private GestureDetector gestureDetector;

    private LargerDrag largerDrag;

    private OnLargerDragListener listener;

    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (gestureDetector != null && largerDrag != null) {
            gestureDetector.onTouchEvent(ev);
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                if (listener != null && largerDrag.isDragging().get()) {
                    listener.onDragEnd();
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }


    public void setDragListener(OnLargerDragListener listener) {
        this.listener = listener;
        largerDrag = new LargerDrag(new OnLargerDragListener() {
            @Override
            public void onDrag(float x, float y) {
                listener.onDrag(x, y);
            }

            @Override
            public void onDragEnd() {
                listener.onDragEnd();
            }

            @Override
            public void onDragStart() {
                listener.onDragStart();
            }

            @Override
            public boolean onDragPrepare(float dx, float dy) {
                return listener.onDragPrepare(dx, dy);
            }
        }, getContext());
        gestureDetector = new GestureDetector(getContext(), largerDrag);
    }

}
