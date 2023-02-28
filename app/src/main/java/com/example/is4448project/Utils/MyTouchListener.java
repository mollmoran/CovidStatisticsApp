package com.example.is4448project.Utils;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Michael Gleeson on 27/10/2020
 * Copyright (c) 2020 | gleeson.io
 */

/*
       This class is adapted from MyRecyclerViewApp by Michael Gleeson
         */
public class MyTouchListener implements RecyclerView.OnItemTouchListener {


    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static final int SWIPE_MAX_OFF_PATH = 250;

    private OnTouchActionListener mOnTouchActionListener;
    private GestureDetectorCompat mGestureDetector;

    public interface OnTouchActionListener {
        void onLeftSwipe(View view, int position);
        void onRightSwipe(View view, int position);
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    public MyTouchListener(Context context, final RecyclerView recyclerView,
                           OnTouchActionListener onTouchActionListener){

        mOnTouchActionListener = onTouchActionListener;
        mGestureDetector = new GestureDetectorCompat(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // Find the item view that was swiped based on the coordinates
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                int childPosition = recyclerView.getChildAdapterPosition(child);
                mOnTouchActionListener.onClick(child, childPosition);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mOnTouchActionListener != null) {
                    mOnTouchActionListener.onLongClick(child, recyclerView.getChildLayoutPosition(child));
                }
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {

                try {
                    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                        return false;
                    }


                    View child = recyclerView.findChildViewUnder(e1.getX(), e1.getY());
                    int childPosition = recyclerView.getChildAdapterPosition(child);


                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                        if (mOnTouchActionListener != null && child != null) {
                            mOnTouchActionListener.onLeftSwipe(child, childPosition);
                        }

                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        if (mOnTouchActionListener != null && child != null) {
                            mOnTouchActionListener.onRightSwipe(child, childPosition);
                        }
                    }
                } catch (Exception e) {

                }

                return false;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
       
    }
//END
}
