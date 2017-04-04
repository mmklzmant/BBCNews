package com.project.zmant.bbcnews.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.project.zmant.bbcnews.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zmant 2016/11/28 15:37
 * @classname GuidePageActivity
 * @description 引导页面
 */

public class GuidePageActivity extends AppCompatActivity {
    @BindView(R.id.iv_guide_skip)
    ImageView mSkipIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        ButterKnife.bind(this);
        startMainActivity();
    }

    /**
     * 初始化视图
     */
    void initViews() {
        setContentView(R.layout.guild_layout);
    }

    //右上角skip监听事件
    @OnClick(R.id.iv_guide_skip)
    public void onClick()
    {
        startMainActivity();
    }

    //开始主activity
    private void startMainActivity() {
        /*Timer time = new Timer();
        TimerTask tk = new TimerTask() {
            Intent intent = new Intent(GuidePageActivity.this,MainActivity.class);
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };time.schedule(tk, 50);
*/
        Intent intent = new Intent(GuidePageActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
