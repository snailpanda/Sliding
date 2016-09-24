package com.snail.administrator.sliding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/9/24.
 * 自定义ListView，实现侧滑删除
 */
public class MyListView extends ListView {

    View setTouchView = null;//触摸事件
    View viewTouch = null;//触摸的view
    GestureDetector mGestureDetector;//设置手势
    View moveView = null;//移动的View
    float startX = 0;//按下的时候的X坐标
    public MyListView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
        if(context instanceof MyCallBack) {
            mMyCallBack = (MyCallBack) context;
        }
    }

    /**
     * 初始化容器
     * @param context
     */
    private void init(Context context) {
        mGestureDetector = new GestureDetector(context,new MyGestureListener());//设置手势

    }

    /**
     * GestureListener的监听事件
     */
    private class MyGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (!(distanceX<0)) {
                moveView.scrollBy((int) distanceX,0);
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    /**
     * 定义一个方法接收触摸的View
     */
    public void setTouchView(View view) {
        setTouchView = view;
        setTouchView.setLongClickable(true);
        setTouchView.setOnTouchListener(new MyTouchListener());
    }

    /**
     * 触摸事件
     */
    private class MyTouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            viewTouch = v;//当前触摸的View
            FrameLayout layout = (FrameLayout) v;
            moveView = layout.findViewById(R.id.content_layout);
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN://按下
                    startX = event.getX();//获取按下的X坐标
                    break;
                case MotionEvent.ACTION_UP:
                    float endX = event.getX();//获取结束点的X坐标
                    if (startX > endX) {//从右向左滑动
                        if (Math.abs(startX-endX)>=getWidth()/2) {//滚动的距离大于一半
                        //删除这个数据，因为数据源在Activity里所以需要回调
                            mMyCallBack.deleteData((Integer) v.findViewById(R.id.text_view).getTag());
                        } else { //滚动距离小于一半，需要缩回去
                            viewTouch.findViewById(R.id.content_layout)
                                    .scrollBy(-(viewTouch.findViewById(R.id.content_layout).getScrollX()),0);
                        }
                    }
                    startX = 0;//置零
                    break;
            }
            return mGestureDetector.onTouchEvent(event);
        }
    }

    float startXX = 0;
    float startYY = 0;
    float endXX = 0;
    float endYY = 0;
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {//事件的拦截
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startXX = event.getX();
                startYY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                endXX = event.getX();
                endYY = event.getY();
                //判断上下移动还是作业移动
                if (Math.abs(startXX-endXX)>Math.abs(startYY-endYY)) {
                    startXX = endXX;
                    startYY = endYY;
                    return false;
                } else {
                    startXX = endXX;
                    startYY = endYY;
                    return true;
                }
        }
        return super.onInterceptHoverEvent(event);
    }

    /**
     * 回调接口
     */
    public interface MyCallBack {
        public void deleteData(int position);
    }
    MyCallBack mMyCallBack = null;

    /**
     * 注册回调的监听
     */
    public void setOnMyCallBackListener(MyCallBack mMyCallBack) {
        this.mMyCallBack = mMyCallBack;
    }

}
