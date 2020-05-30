package com.akshit.ontime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.akshit.ontime.R;
import com.akshit.ontime.models.ScreenItem;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    private final List<ScreenItem> mScreenItemList;
    private final Context mContext;

    public IntroViewPagerAdapter(final Context context, final List<ScreenItem> screenItemList) {
        mContext = context;
        mScreenItemList = screenItemList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layoutScreen = inflater.inflate(R.layout.layout_screen, null);

        final ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);
        final TextView title = layoutScreen.findViewById(R.id.intro_title);
        final TextView description = layoutScreen.findViewById(R.id.intro_description);

        title.setText(mScreenItemList.get(position).getTitle());
        description.setText(mScreenItemList.get(position).getDescription());
        imgSlide.setImageResource(mScreenItemList.get(position).getScreenImg());

        container.addView(layoutScreen);

        return layoutScreen;

    }

    @Override
    public int getCount() {
        return mScreenItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull final View view, @NonNull final Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull final ViewGroup container, final int position, @NonNull final Object object) {
        container.removeView((View) object);
    }
}
