package com.shalskar.peekandpopdemo;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by Vincent on 24/01/2016.
 */
class CustomPagerAdapter extends PagerAdapter {

    private DemoObject demoObject;
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomPagerAdapter(Context context, DemoObject demoObject) {
        this.context = context;
        this.demoObject = demoObject;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(demoObject == null)
            return 0;
        else
            return demoObject.getImageUrls().size();
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.item_pager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(com.peekandpop.shalskar.peekandpop.R.id.image);
        loadImage(imageView, position);

        container.addView(itemView);

        return itemView;
    }

    private void loadImage(ImageView imageView, int position){
        Picasso.with(context)
                .load(demoObject.getImageUrls().get(position))
                .resize((int) context.getResources().getDimension(R.dimen.image_width), (int) context.getResources().getDimension(R.dimen.image_height))
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setDemoObject(DemoObject demoObject) {
        this.demoObject = demoObject;
    }
}