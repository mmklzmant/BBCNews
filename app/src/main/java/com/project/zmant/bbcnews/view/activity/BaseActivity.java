package com.project.zmant.bbcnews.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.utils.ACache;
import com.project.zmant.bbcnews.utils.ActivityManager;
import com.project.zmant.bbcnews.view.view.FeedBackDialog;
import com.project.zmant.bbcnews.view.view.LeftMenuCommonDialog;

import butterknife.ButterKnife;

/**
 * @classname: BaseActivity
 * @author  zmant 2016/11/28.
 * @description: Activity基类,为了简化代码,提供一些接口如initViews()
 * initListeners()等
 */

public abstract class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    private static boolean isExit = false;//判断是否退出的变量
    private NavigationView mNavView;
    /**
     * 应用程序退出
     */
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoTitle);
        initViews();
        ButterKnife.bind(this);
        setupComponent();
        initToolbar();
        initData();
        initListeners();
        mNavView = getNav();
        setNavListener();
        drawerToggle = getDrawerToggle();
        ActivityManager.addActivity(this);

    }


    private void setNavListener() {
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.bbc_news_menu:
                        startActivity(new Intent(getCurrentActivity(),
                                MainActivity.class));
                        getDrawer().closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ted_menu:
                        startActivity(new Intent(getCurrentActivity(),
                                TedActivity.class));
                        getDrawer().closeDrawer(GravityCompat.START);
                        break;
                    case R.id.daily_sentence_menu:
                        startActivity(new Intent(getCurrentActivity(),
                                DailySentenceActivity.class));
                        getDrawer().closeDrawer(GravityCompat.START);
                        break;
                    case R.id.feedback_menu:
                        alertFeedback();
                        break;
                    case R.id.exit_menu:
                        alertExit();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void alertFeedback() {
        final FeedBackDialog dialog = new FeedBackDialog(this);
        dialog.initUI("Email To:mmklzmant@163.com,Thank you!","OK");
        dialog.setOnDialogClickListener(new FeedBackDialog.onClickDialogListener() {
            @Override
            public void onClickOkButton() {
                dialog.closeDialog();
            }
        });
        dialog.showDialog();
    }

    private void alertExit() {
        final LeftMenuCommonDialog dialog = new LeftMenuCommonDialog(this);
        dialog.initUI("Is Exit？", "Yes", "No");
        dialog.setOnDialogClickListener(new LeftMenuCommonDialog.onClickDialogListener() {
            @Override
            public void onClickLeftButton() {
                ActivityManager.finish();
            }

            @Override
            public void onClickRightButton() {
                dialog.closeDialog();
            }
        });
        dialog.showDialog();
    }

    private void initToolbar() {
        toolbar = getToolbar();
        toolbar.setTitle(getToolBarTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
        toolbar.setNavigationIcon(R.mipmap.nav_icon);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(!isExit)
            {
                isExit = true;
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
            else
            {
                ActivityManager.finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        if(item.getItemId() == R.id.settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    abstract void initViews();
    abstract void initData();
    abstract void initListeners();
    abstract Toolbar getToolbar();
    abstract protected ActionBarDrawerToggle getDrawerToggle();
    abstract protected NavigationView getNav();
    abstract protected DrawerLayout getDrawer();
    abstract protected BaseActivity getCurrentActivity();
    abstract protected String getToolBarTitle();
    abstract protected void setupComponent();
}
