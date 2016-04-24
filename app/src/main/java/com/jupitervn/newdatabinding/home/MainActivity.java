package com.jupitervn.newdatabinding.home;

import com.jupitervn.newdatabinding.R;
import com.jupitervn.newdatabinding.databinding.ActivityMainBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MovieListingViewModel listingVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Bundle vmState = null;
        if (savedInstanceState != null) {
            vmState = savedInstanceState.getBundle("extra-vm");
        }
        listingVM = new MovieListingViewModel(vmState);
        viewDataBinding.setListingVM(listingVM);
        //https://www.reddit.com/r/CommitStripFeed/hot.json?after=t3_46lzkz
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBundle("extra-vm", listingVM.onSavedInstanceState());
        super.onSaveInstanceState(outState);
    }
}
