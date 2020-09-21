package com.example.largerloadvideo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.starot.larger.view.image.OnLargerDragListener;

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
    public void onCompletion() {
        super.onCompletion();
        posterImageView.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_jzstd_notitle;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (gestureDetector != null && largerDrag != null) {
            gestureDetector.onTouchEvent(ev);
            if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
                if (listener != null && largerDrag.isDragging().get()) {
                    largerDrag.isDragging().set(false);
                    listener.onDragEnd();
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }


    public void setDragListener(OnLargerDragListener listener) {
        this.listener = listener;
        largerDrag = new LargerDrag(listener, getContext());
        gestureDetector = new GestureDetector(getContext(), largerDrag);
    }

}
