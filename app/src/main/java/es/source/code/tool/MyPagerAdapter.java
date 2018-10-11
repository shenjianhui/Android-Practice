package es.source.code.tool;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private List<String> titles;
    private List<View> viewList;

    public MyPagerAdapter( List<View> viewList,List<String> titles) {

        this.viewList = viewList;
        this.titles = titles;
    }



    @Override
    public int getCount() {
        return viewList.size();
    }

    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
