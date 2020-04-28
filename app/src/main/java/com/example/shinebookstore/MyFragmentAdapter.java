package com.example.shinebookstore;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i){
            case 0:
                fragment=new StoreFragment();
                break;
            case 1:
                fragment=new MyBooksFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle="";
        switch (position){
            case 0:
                pageTitle="Store";
                break;
            case 1:
                pageTitle="My Books";
                break;

        }

        return pageTitle;
    }

}
