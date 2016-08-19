package deeplife.gcme.com.deeplife.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bengeos on 8/18/16.
 */

public class ViewPager_Adapter extends FragmentPagerAdapter {
    private final List<Fragment> FragmentPages = new ArrayList<Fragment>();
    private final List<String> Titles = new ArrayList<String>();

    public ViewPager_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentPages.get(position);
    }

    @Override
    public int getCount() {
        return FragmentPages.size();
    }

    public void AddFragment(Fragment fragment, String title){
        FragmentPages.add(fragment);
        Titles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles.get(position);
    }
}
