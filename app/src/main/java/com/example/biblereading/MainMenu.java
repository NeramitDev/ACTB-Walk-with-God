package com.example.biblereading;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TabHost;

import com.example.biblereading.Adapter.PagerAdapter;
import com.example.biblereading.Fragment.AnnouceFragment;
import com.example.biblereading.Fragment.DevotionFragment;
import com.example.biblereading.Fragment.MoralFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

//import android.support.v4.view.PagerAdapter;

public class MainMenu extends AppCompatActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private TabHost mTabHost;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, MainMenu.TabInfo>();
    private int Position;

    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;

        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }
    }

    private class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        /**
         * @param context
         */
        TabFactory(Context context) {
            mContext = context;
        }

        /**
         * (non-Javadoc)
         *
         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
         */
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(R.string.Announce);
        // Initialise the TabHost
        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
        // Intialise ViewPager
        this.intialiseViewPager();
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }

    /**
     * Initialise ViewPager
     */
    private void intialiseViewPager() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, AnnouceFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, DevotionFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, MoralFragment.class.getName()));
        PagerAdapter mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        ViewPager mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initialiseTabHost(Bundle args) {
//        TabWidget tabWidget = (TabWidget) findViewById(R.id.tabs);
        mTabHost = (TabHost) findViewById(R.id.tabHost);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sp.getString("Theme", "Green");
        mTabHost.setup();
//        switch (theme) {
//            case "Blue":
//                setTheme(R.style.Blue_NoActionBar);
//                mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.colorPrimary_blue, null));
//                break;
//            case "Pink":
//                setTheme(R.style.Pink_NoActionBar);
//                mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.colorPrimary_pink, null));
//                break;
//            case "Orange":
//                setTheme(R.style.Orange_NoActionBar);
//                mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.colorPrimary_orange, null));
//                break;
//            default:
//                setTheme(R.style.AppTheme_NoActionBar);
//                mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
//                break;
//        }

        TabInfo tabInfo = null;
        MainMenu.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab1").setIndicator("", getDrawable(R.drawable.annouce_selector)), (tabInfo = new TabInfo("Tab1", AnnouceFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainMenu.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab2").setIndicator("", getDrawable(R.drawable.annouce_selector)), (tabInfo = new TabInfo("Tab2", DevotionFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainMenu.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab3").setIndicator("", getDrawable(R.drawable.annouce_selector)), (tabInfo = new TabInfo("Tab3", MoralFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        // Default to first tab
//        this.onTabChanged("Tab1");
        //
        mTabHost.setOnTabChangedListener(this);
    }

    private static void AddTab(MainMenu activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Position = position;
        this.mTabHost.setCurrentTab(position);
        if (position == 0) {
            getSupportActionBar().setTitle(R.string.Announce);
        } else if (position == 1) {
            getSupportActionBar().setTitle(R.string.Devotion);
        } else if (position == 2) {
            getSupportActionBar().setTitle(R.string.Moral);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onTabChanged(String s) {
//        Toast.makeText(this,"Test\nonTabChanged",Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        switch (s) {
            case "Tab1":
                getSupportActionBar().setTitle(R.string.Announce);
                break;
            case "Tab2":
                getSupportActionBar().setTitle(R.string.Devotion);
                break;
            case "Tab3":
                getSupportActionBar().setTitle(R.string.Moral);
                break;
        }

//        int pos = this.mTabHost.getCurrentTab();
//        this.mViewPager.setCurrentItem(pos);
//        this.menu.clear();
//        if (Position == 0) {
//            getMenuInflater().inflate(R.menu.friend_menu, menu);
//        } else if (Position == 1) {
//            getMenuInflater().inflate(R.menu.group_menu, menu);
//        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_exit);
        builder.setIcon(R.drawable.icon_app);
        builder.setMessage(R.string.message_exit);

        builder.setPositiveButton(R.string.Yes_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                finishAndRemoveTask();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
                System.exit(0);
                int pid = Process.myPid();
                Process.killProcess(pid);
            }
        });
        builder.setNegativeButton(R.string.No_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}