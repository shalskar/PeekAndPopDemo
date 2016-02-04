package com.shalskar.peekandpopdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.peekandpop.shalskar.peekandpop.PeekAndPop;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Vincent on 22/01/2016.
 */
public class PeekAdapter extends RecyclerView.Adapter<PeekAdapter.ViewHolder> {

    private Activity activity;

    private PeekAndPop peekAndPop;
    private View peekView;
    private ViewPager viewPager;
    private CustomPagerAdapter customPagerAdapter;
    private TextView titleTextView;

    private List<DemoObject> demoObjects;

    public PeekAdapter(Activity activity, int type, List<DemoObject> demoObjects, PeekAndPop peekAndPop) {
        this.activity = activity;
        this.peekAndPop = peekAndPop;
        this.demoObjects = demoObjects;

        this.peekView = peekAndPop.getPeekView();
        this.viewPager = (ViewPager) peekView.findViewById(R.id.pager);
        this.viewPager.setOffscreenPageLimit(2);
        this.customPagerAdapter = new CustomPagerAdapter(peekView.getContext(), null);
        this.viewPager.setAdapter(customPagerAdapter);
        this.titleTextView =(TextView) peekView.findViewById(R.id.title);

        switch (type) {
            case (Constants.TYPE_STANDARD):
                setupPeekAndPopStandard();
                break;
            case (Constants.TYPE_LONGHOLD):
                setupPeekAndPopLonghold();
                break;
            case (Constants.TYPE_FLING):
                setupPeekAndPopFling();
                break;
            case (Constants.TYPE_HOLD_AND_RELEASE):
                setupPeekAndPopHoldAndRelease();
                break;
            case (Constants.TYPE_EXAMPLE):
                setupPeekAndPopExample();
                break;
        }
    }

    private void setupPeekAndPopStandard() {
        this.peekAndPop.setOnGeneralActionListener(new PeekAndPop.OnGeneralActionListener() {
            @Override
            public void onPeek(View view, int position) {
                loadPeekAndPop(position);
            }

            @Override
            public void onPop(View view, int position) {
            }
        });
    }

    private void setupPeekAndPopLonghold() {
        setupPeekAndPopStandard();
        peekAndPop.addLongHoldView(R.id.arrow_left, true);
        peekAndPop.addLongHoldView(R.id.arrow_right, true);

        peekView.findViewById(R.id.arrow_left).setVisibility(View.VISIBLE);
        peekView.findViewById(R.id.arrow_right).setVisibility(View.VISIBLE);

        peekAndPop.setOnLongHoldListener(new PeekAndPop.OnLongHoldListener() {
            @Override
            public void onLongHold(View view, int position) {
                ((Vibrator) activity.getApplicationContext().getSystemService(Activity.VIBRATOR_SERVICE)).vibrate(10);
                if (view.getId() == R.id.arrow_left) {
                    scrollToPreviousImage(position);
                } else if (view.getId() == R.id.arrow_right) {
                    scrollToNextImage(position);
                }
            }
        });
    }

    private void setupPeekAndPopFling() {
        setupPeekAndPopLonghold();
        peekAndPop.setOnFlingToActionListener(new PeekAndPop.OnFlingToActionListener() {
            @Override
            public void onFlingToAction(View view, int position, int direction) {
                if (direction == PeekAndPop.FLING_UPWARDS) {
                    Snackbar.make(activity.findViewById(android.R.id.content), "Fling upwards on " + demoObjects.get(position).getText(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(activity.findViewById(android.R.id.content), "Fling downwards on " + demoObjects.get(position).getText(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setupPeekAndPopHoldAndRelease() {
        setupPeekAndPopFling();
        peekView.findViewById(R.id.arrow_down).setVisibility(View.VISIBLE);
        peekAndPop.addHoldAndReleaseView(R.id.arrow_down);
        peekAndPop.setOnHoldAndReleaseListener(new PeekAndPop.OnHoldAndReleaseListener() {
            @Override
            public void onHoldAndRelease(View view, int position) {
                Snackbar.make(activity.findViewById(android.R.id.content), "Hold and release on " + demoObjects.get(position).getText(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setupPeekAndPopExample() {
        setupPeekAndPopLonghold();
        peekAndPop.setOnFlingToActionListener(new PeekAndPop.OnFlingToActionListener() {
            @Override
            public void onFlingToAction(View view, int position, int direction) {
                if (direction == PeekAndPop.FLING_UPWARDS)
                    openExampleActivity(position);
            }
        });
    }

    private void loadPeekAndPop(int position) {
        titleTextView.setText(demoObjects.get(position).getText());
        customPagerAdapter.setDemoObject(demoObjects.get(position));
        customPagerAdapter.notifyDataSetChanged();
    }

    private void scrollToNextImage(int position) {
        demoObjects.get(position).nextImage();
        if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount() - 1) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    private void scrollToPreviousImage(int position) {
        demoObjects.get(position).previousImage();
        if (viewPager.getCurrentItem() > 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        } else {
            viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
        }
    }

    @Override
    public PeekAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.demo_object, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        peekAndPop.addLongClickView(viewHolder.view, position);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PeekAndPop", "Click");
            }
        });

        loadImage(viewHolder, position);
    }

    private void loadImage(ViewHolder viewHolder, int position) {
        DemoObject demoObject = demoObjects.get(position);
        Context context = viewHolder.view.getContext();
        Picasso.with(context)
                .load(demoObject.getImageUrls().get(0))
                .resize((int) context.getResources().getDimension(R.dimen.thumbnail_width), (int) context.getResources().getDimension(R.dimen.thumbnail_height))
                .centerCrop()
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return demoObjects.size();
    }

    private void openExampleActivity(int position) {
        Intent intent = new Intent(activity, ExampleActivity.class);
        String currentImageUrl = demoObjects.get(position).getImageUrls().get(viewPager.getCurrentItem());
        intent.putExtra(Constants.IMAGE_URL, currentImageUrl);

        if (Build.VERSION.SDK_INT >= 21) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, viewPager, "image");
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.imageView = (ImageView) view.findViewById(R.id.image);
        }
    }

}
