package com.jlu.mzx.tiaoji.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.WindowManager;
import android.widget.Toast;

import com.jlu.mzx.tiaoji.Frag.SearchvolunteerFragment;
import com.jlu.mzx.tiaoji.R;

import java.util.ArrayList;
import java.util.List;

public class SearchVolunteer extends FragmentActivity {
    private SearchView searchView;
    private TabLayout tabLayout;
    private List<Fragment> fragmentlist;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_volunteer);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);

        initview();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                SearchvolunteerFragment f = (SearchvolunteerFragment) fragmentlist.get(0);
                f.updatelist(0, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void initview() {
        fragmentlist = new ArrayList<>();
        fragmentlist.add(new SearchvolunteerFragment());
        fragmentlist.add(new SearchvolunteerFragment());
        fragmentlist.add(new SearchvolunteerFragment());
        fragmentlist.add(new SearchvolunteerFragment());
        fragmentlist.add(new SearchvolunteerFragment());

        //    getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            private String[] titles = {"志愿", "专业", "地区", "老师", "学校"};

            @Override
            public Fragment getItem(int position) {
                return fragmentlist.get(position);
            }

            @Override
            public int getCount() {
                return fragmentlist.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(SearchVolunteer.this, "当前是第" + position + "个fragment", Toast.LENGTH_SHORT).show();
                if (searchView.getQuery() != null) {
                    SearchvolunteerFragment f = (SearchvolunteerFragment) fragmentlist.get(position);
                    f.updatelist(position, searchView.getQuery().toString());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.setStatusBarColor(Color.parseColor("#00fd43"));
//        }
        viewPager.setCurrentItem(0);
    }

}
