package com.brosis.doubledate.Profile;

import com.brosis.doubledate.R;

public enum ModelObject {

    RED(R.string.one, R.layout.boost_slider_view),
    BLUE(R.string.two, R.layout.likes_slider_view),
    GREEN(R.string.three, R.layout.standout_slider_view);

    //Yellow(R.string.four, R.layout.boost_slider_view);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
