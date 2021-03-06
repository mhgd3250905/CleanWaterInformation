package skkk.gogogo.cwinformation;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import Fragment.BaijiaSpiderFragment;
<<<<<<< HEAD
import Fragment.FenghuangSpiderFragment;
=======
>>>>>>> origin/master
import Fragment.HuxiuSpiderFragment;
import Fragment.ITHomeSpiderFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.tb_home)
    Toolbar tbHome;
    @Bind(R.id.fab_home)
    FloatingActionButton fabHome;
    @Bind(R.id.fl_home)
    FrameLayout flHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initEvent();
        initDefaultFragment();
    }


    /*
    ***************************************************
    * @方法 初始化UI
    * @参数
    * @返回值
    */
    private void initUI() {
        /* @描述 设置状态栏透明 */
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        /* @描述 toolbar */
        setSupportActionBar(tbHome);

        /* @描述 侧滑菜单 */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, tbHome, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    /*
    ***************************************************
    * @方法 设置监听事件
    * @参数
    * @返回值
    */
    private void initEvent() {
         /* @描述 fab点击事件 */
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    /*
    ***************************************************
    * @方法 加载默认加载的Fragment页面
    * @参数
    * @返回值
    */
    private void initDefaultFragment() {
        HuxiuSpiderFragment firstFragment=new HuxiuSpiderFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_home,firstFragment)
                .commit();
    }


    /*
    ***************************************************
    * @方法 菜单、返回键响应
    * @参数
    * @返回值
    */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                exitBy2Click(); //调用双击退出函数
            }
        }
        return false;
    }

    /* @描述 双击返回函数 */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
        switch (id) {
            case R.id.menu_huxiu:
                HuxiuSpiderFragment firstFragment = new HuxiuSpiderFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_home, firstFragment)
                        .commit();

                break;
            case R.id.menu_ithome:
                ITHomeSpiderFragment itHomeSpiderFragment = new ITHomeSpiderFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_home, itHomeSpiderFragment)
                        .commit();
                break;
            case R.id.menu_baijia:
                BaijiaSpiderFragment baijiaSpiderFragment = new BaijiaSpiderFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_home, baijiaSpiderFragment)
                        .commit();
                break;
            case R.id.menu_fenghuang:
<<<<<<< HEAD
                FenghuangSpiderFragment fenghuangSpiderFragment = new FenghuangSpiderFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_home, fenghuangSpiderFragment)
                        .commit();
=======

>>>>>>> origin/master
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
