package com.example.aryapk.myalarm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aryapk.myalarm.countdown.CountDownTimerFragment;
import com.example.aryapk.myalarm.stopwatch.StopwatchFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlarmHomeActivity extends AppCompatActivity {
    @Bind(R.id.vpAlarmHome) ViewPager viewPager;
    @Bind(R.id.tabAlarmHome) TabLayout tabAlarmHome;
    protected FragmentManager fragmentManager;
    private static final int PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_home);
        ButterKnife.bind(this);
        requestStoragePermission();
        fragmentManager = getSupportFragmentManager();
        setupViewPager(viewPager);
        tabAlarmHome.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        StopwatchFragment sf = new StopwatchFragment();
        CountDownTimerFragment ctf = new CountDownTimerFragment();
        ListAlarmFragment laf = new ListAlarmFragment();
        adapter.addFragment(laf, "Alarm");
        adapter.addFragment(ctf, "CountDown");
        adapter.addFragment(sf, "StopWatch");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
    }


}
