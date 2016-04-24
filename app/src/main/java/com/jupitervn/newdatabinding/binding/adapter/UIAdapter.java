package com.jupitervn.newdatabinding.binding.adapter;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 4/12/16.
 */
public class UIAdapter {

    private static final String TAG = "VMUIAdapter";

    @BindingAdapter("refreshing")
    public static void setRefreshing(final CustomSwipeRefreshLayout swipeRefreshLayout, boolean oldValue, final boolean newValue) {
        Log.d(TAG, "setRefreshing: " + oldValue + " " + newValue);
        if (newValue != oldValue) {
            if (newValue) {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(newValue);
                    }
                });
            } else {
                swipeRefreshLayout.setRefreshing(newValue);
            }
        }
    }

    @InverseBindingAdapter(attribute = "refreshing")
    public static boolean getRefreshingPageState(CustomSwipeRefreshLayout view) {
        Log.d(TAG, "getRefreshingPageState: " + view.isRefreshing());
        return view.isRefreshing();
    }

    @BindingAdapter(value = {"refreshingAttrChanged"}, requireAll = false)
    public static void setRefreshingListener(CustomSwipeRefreshLayout view,
            final InverseBindingListener refreshStateChange) {
        if (refreshStateChange == null) {
            view.setOnRefreshStateChangedListener(null);
        } else {
            view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Log.d(TAG, "onRefresh Called");
                    refreshStateChange.onChange();
                }
            });
            view.setOnRefreshStateChangedListener(new CustomSwipeRefreshLayout.OnRefreshStateChangedListener() {
                @Override
                public void onRefreshStateChanged(boolean isRefreshing) {
                    Log.d(TAG, "onRefreshStateChanged: " + isRefreshing);
                    refreshStateChange.onChange();
                }
            });
        }
    }

}
