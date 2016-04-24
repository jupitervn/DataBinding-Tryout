package com.jupitervn.newdatabinding.home;

import com.jupitervn.newdatabinding.BR;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 4/11/16.
 */
public class MovieListingViewModel extends BaseObservable {

    public static final String LOADING_STATE = "Loading";
    public static final String DISPLAY_DATA_STATE = "Diplay";
    public static final String ERROR_STATE = "Error";
    public static final String IDLE_STATE = "Idle";
    private static final String TAG = "ListingVM";
    public static final String EXTRA_PAGE_STATE = "extra-page-state";

    @StringDef({LOADING_STATE, DISPLAY_DATA_STATE, ERROR_STATE, IDLE_STATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PAGE_STATE {

    }

    private @PAGE_STATE String pageState = IDLE_STATE;

    public MovieListingViewModel(Bundle savedInstanceState) {
        @PAGE_STATE String pageState = IDLE_STATE;
        if (savedInstanceState != null) {
            pageState = savedInstanceState.getString(EXTRA_PAGE_STATE);
        }
        this.pageState = pageState;
    }

    public Bundle onSavedInstanceState() {
        Bundle savedState = new Bundle();
        savedState.putString(EXTRA_PAGE_STATE, pageState);
        return savedState;
    }

    @Bindable
    public String getPageState() {
        return pageState;
    }

    public void setPageState(@PAGE_STATE String pageState) {
        if (!pageState.equalsIgnoreCase(this.pageState)) {
            Log.d(TAG, "setPageState: " + pageState);
            this.pageState = pageState;
            notifyPropertyChanged(BR.pageState);
            switch (pageState) {
                case LOADING_STATE:
                    onRefreshData();
                    notifyPropertyChanged(BR.pageLoading);
                    break;
                case IDLE_STATE:
                    notifyPropertyChanged(BR.pageLoading);
                    break;
            }
        }
    }
    @Bindable
    public boolean isPageLoading() {
        return pageState.equalsIgnoreCase(LOADING_STATE);
    }

    public void setIsPageLoading(boolean setPageLoading) {
        Log.d(TAG, "setIsLoading: " + pageState + " " + setPageLoading);
        setPageState(setPageLoading ? LOADING_STATE : IDLE_STATE);
    }

    public void onRefreshData() {
        Log.d(TAG, "onRefreshData: " + pageState);
    }

    public void stopRefreshing() {
        setIsPageLoading(false);
    }

    public void startRefreshing() {
        setIsPageLoading(true);
    }
}
