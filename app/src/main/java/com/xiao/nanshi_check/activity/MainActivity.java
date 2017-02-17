package com.xiao.nanshi_check.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.adapter.FragmentAdapter;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private String[] tab_names;

    private FloatingActionButton fab;//

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//    SplashActivity.

        Resources res = getResources();
        tab_names = res.getStringArray(R.array.tab_names);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("智能考核");
//        toolbar.setSubtitle("sub");
//        toolbar.setLogo(R.drawable.ic_launcher);
//        toolbar.setNavigationIcon(R.drawable.ic_list_black_24dp);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回按钮
        initTabLayout();

        fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "hahah " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_SHORT).show();
                if (tabLayout.getSelectedTabPosition() == 1) {
                    startActivity(new Intent(MainActivity.this, AddEquipmentActivity.class));
                } else if (tabLayout.getSelectedTabPosition() == 2) {
                    Toast.makeText(MainActivity.this, "成绩 ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


//    //回调隐藏fab
//    public void invisible() {
//        fab.setVisibility(View.INVISIBLE);
//    }
//
//    //回调显示fab
//    public void visible() {
//        fab.setVisibility(View.VISIBLE);
//    }

    private void initTabLayout() {
        viewPager = findView(R.id.viewPager);
        tabLayout = findView(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式 TabLayout.MODE_SCROLLABLE
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), tab_names);
        viewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
               /* BaseFragment createFragment = FragmentFactory.createFragment(position);
                createFragment.show();//  当切换界面的时候 重新请求服务器 */
//                Toast.makeText(MainActivity.this, position + "hahah " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    fab.setVisibility(View.GONE);
                } else if (position == 1) {
                    fab.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    fab.setVisibility(View.VISIBLE);
                }
            }

        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_edit) {
            Toast.makeText(MainActivity.this, "查找", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_student_management) {
            startActivity(new Intent(MainActivity.this, StudentManagementActivity.class));
        } else if (id == R.id.nav_rights_management) {

        } else if (id == R.id.nav_test_question_management) {

        } else if (id == R.id.nav_other) {

        } else if (id == R.id.nav_About_me) {

        } else if (id == R.id.nav_theme) {

        } else if (id == R.id.nav_exit) {
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
