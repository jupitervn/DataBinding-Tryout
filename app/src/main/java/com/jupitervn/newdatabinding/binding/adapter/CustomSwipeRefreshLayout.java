package com.jupitervn.newdatabinding.binding.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 4/14/16.
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {
    private OnRefreshStateChangedListener onRefreshStateChangedListener;

    public CustomSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        boolean oldRefreshing = isRefreshing();
        Log.d("VM", "setRefreshing: " + refreshing + " " + oldRefreshing);
        super.setRefreshing(refreshing);
        if (oldRefreshing != refreshing) {
            onRefreshStateChangedListener.onRefreshStateChanged(refreshing);
        }
    }

    public boolean getRefreshing() {
        return isRefreshing();
    }

    public void setOnRefreshStateChangedListener(OnRefreshStateChangedListener onRefreshStateChangedListener) {
        this.onRefreshStateChangedListener = onRefreshStateChangedListener;
    }

    public interface OnRefreshStateChangedListener {
        void onRefreshStateChanged(boolean isRefreshing);
    }

}
