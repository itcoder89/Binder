package com.brosis.doubledate.Profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brosis.doubledate.Profile.ModelObject;
import com.brosis.doubledate.R;

import androidx.viewpager.widget.PagerAdapter;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    int layoutes[]={R.layout.boost_slider_view,R.layout.likes_slider_view,R.layout.standout_slider_view,R.layout.swipe_right_slider_view};
    public CustomPagerAdapter(Context context) {
        mContext = context;
    }

    /*@Override
    public Object instantiateItem(ViewGroup collection, int position) {
        ModelObject modelObject = ModelObject.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
        collection.addView(layout);
        return layout;
    }*/

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        LayoutInflater layoutInflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View one=layoutInflater.inflate(R.layout.boost_slider_view,container,false);
        View two=layoutInflater.inflate(R.layout.likes_slider_view,container,false);
        View three=layoutInflater.inflate(R.layout.standout_slider_view,container,false);
        View fourth=layoutInflater.inflate(R.layout.swipe_right_slider_view,container,false);
        View viewarr[]={one,two,three,fourth};
        container.addView(viewarr[position]);
        return viewarr[position];
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
       // return ModelObject.values().length;
        return layoutes.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /*@Override
    public CharSequence getPageTitle(int position) {
        ModelObject customPagerEnum = ModelObject.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }*/

}
