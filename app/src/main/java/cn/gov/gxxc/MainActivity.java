package cn.gov.gxxc;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.gov.gxxc.adapter.NewsListFragmentAdapter;
import cn.gov.gxxc.adapter.TextNewsListAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.vp_news);
        adapter = new NewsListFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
