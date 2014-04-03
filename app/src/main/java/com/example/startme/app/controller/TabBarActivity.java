package com.example.startme.app.controller;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.graphics.Color;
import com.example.startme.app.R;

public class TabBarActivity extends TabActivity implements OnTabChangeListener {

    TabHost tabHost;

    private void setupTabHost()
    {
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbar);

        setupTabHost();

        // Set TabChangeListener called when tab changed
        tabHost.setOnTabChangedListener(this);

        setupTab(0);
        setupTab(1);
        setupTab(2);
        setupTab(3);
        setupTab(4);

        tabHost.setCurrentTab(0);
    }

    @Override
    public void onTabChanged(String tabId) {

        if (tabHost.getCurrentTab() == 2){

            Intent intent = new Intent(this, ShareActivity.class);
            startActivity(intent);
        }
    }

    private void setupTab(int index)
    {
        View tabview = createTabView(tabHost.getContext(), index);

        TabHost.TabSpec setContent = tabHost.newTabSpec(String.valueOf(index)).setIndicator(tabview);

        switch (index){
            case 0:

                setContent.setContent(new Intent(this, HomeActivity.class));
                break;
            case 1:
                setContent.setContent(new Intent(this, ConnectActivity.class));
                break;
            case 2:
                setContent.setContent(new Intent(this, ShareBlankActivity.class));
                break;
            case 3:
                setContent.setContent(new Intent(this, AlertActivity.class));
                break;
            case 4:
                setContent.setContent(new Intent(this, SettingActivity.class));
                break;
        }


        tabHost.addTab(setContent);
    }

    private View createTabView(final Context context, int index)
    {
        View view = null;

        switch (index){
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.home_tabwidget, null);

                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.connect_tabwidget, null);

                break;
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.share_tabwidget, null);

                break;
            case 3:
                view = LayoutInflater.from(context).inflate(R.layout.alert_tabwidget, null);

                break;
            case 4:
                view = LayoutInflater.from(context).inflate(R.layout.setting_tabwidget, null);
                break;
        }


        return view;
    }


}
