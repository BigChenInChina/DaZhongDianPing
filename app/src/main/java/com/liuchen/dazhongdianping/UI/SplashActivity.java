package com.liuchen.dazhongdianping.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.liuchen.dazhongdianping.R;
import com.liuchen.dazhongdianping.Util.SPUtil;

public class SplashActivity extends Activity {
    SPUtil spUtil;

    //private  static final String FIRST = "first";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        spUtil = new SPUtil(this);
        //界面停留几秒钟
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //读取偏好设置文件中的值
                //根据是否是第一次使用进行相应的界面跳转
                Intent intent;
                if (spUtil.isFirst()){
                    //向新生指导页跳转
                    intent = new Intent(SplashActivity.this,GuideActivity.class);
                    spUtil.setFirst(false);
                }else {
                    //向主页跳转
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
