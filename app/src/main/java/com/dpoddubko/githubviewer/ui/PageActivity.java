package com.dpoddubko.githubviewer.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.dpoddubko.githubviewer.R;

public class PageActivity extends FragmentActivity {

    static final int PAGE_COUNT = 2;
    static final String EXTRA_USER_NAME = "extra_page_number";
    ViewPager pager;
    PagerAdapter pagerAdapter;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity);
        name = getIntent().getStringExtra(EXTRA_USER_NAME);
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }

    public static Intent newIntent(Context packageContext, String user) {
        Intent intent = new Intent(packageContext, PageActivity.class);
        intent.putExtra(EXTRA_USER_NAME, user);
        return intent;
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position, name);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title;
            switch (position) {
                case 0:
                    title = "Repositories";
                    break;
                case 1:
                    title = "Stars";
                    break;
                default:
                    title = "Invalid title";
                    break;
            }
            return title;
        }
    }
}
