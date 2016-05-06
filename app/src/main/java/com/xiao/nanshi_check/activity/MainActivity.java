package com.xiao.nanshi_check.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.xiao.nanshi_check.R;
import com.xiao.nanshi_check.adapter.FragmentAdapter;
import com.xiao.nanshi_check.service.BluetoothService;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private String[] tab_names;


    // 调试以排除故障
    private static final String TAG = "Bluetooth";
    private static final boolean D = true;

    // 消息类型从蓝牙发送聊天服务处理程序
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // 键名收到蓝牙聊天服务处理程序
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // 意图请求代码
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
//    private TextView mTitle;
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSendButton;

    // 连接设备的名称
    private String mConnectedDeviceName = null;
    // 数组适配器对话线程
    private ArrayAdapter<String> mConversationArrayAdapter;
    // 字符串缓冲区传出消息
    private StringBuffer mOutStringBuffer;
    // 本地蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter = null;
    // 成员对象聊天服务
    private BluetoothService mChatService = null;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

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
        toolbar.setTitle("啊西吧");
//        toolbar.setSubtitle("sub");
//        toolbar.setLogo(R.drawable.ic_launcher);
//        toolbar.setNavigationIcon(R.drawable.ic_list_black_24dp);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回按钮
        initTabLayout();
    }

    private void initTabLayout() {

//        List<Fragment> fragmentList = new ArrayList<>();
//        for (int i = 0; i < tabList.size(); i++) {
//            Fragment f1 = new TabFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("content", "http://blog.csdn.net/feiduclear_up \n CSDN 废墟的树");
//            f1.setArguments(bundle);
//            fragmentList.add(f1);
//        }
        viewPager = findView(R.id.viewPager);
        tabLayout = findView(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式 TabLayout.MODE_SCROLLABLE
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), tab_names);
        viewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器
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
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tudent_management) {
            startActivity(new Intent(MainActivity.this, Bluetooth.class));

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
