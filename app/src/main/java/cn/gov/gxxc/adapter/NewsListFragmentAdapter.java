package cn.gov.gxxc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.gov.gxxc.fragment.TextNewsListFragment;
import cn.gov.gxxc.fragment.VideoNewsListFragment;

/**
 * Created by ChuyaoShi on 16/10/31.
 */

public class NewsListFragmentAdapter extends FragmentPagerAdapter {

    public NewsListFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TextNewsListFragment();
            case 1:
                return new VideoNewsListFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
