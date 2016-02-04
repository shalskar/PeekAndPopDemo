package com.shalskar.peekandpopdemo;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.peekandpop.shalskar.peekandpop.PeekAndPop;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.List;

public class ExampleActivity extends AppCompatActivity {

    private String imageUrl;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        if(Build.VERSION.SDK_INT >= 21) {
            getWindow().setSharedElementEnterTransition(enterTransition());
            getWindow().setSharedElementExitTransition(null);
            getWindow().setSharedElementReturnTransition(null);
            getWindow().setExitTransition(null);
            getWindow().setReturnTransition(null);
        }

        imageUrl = getIntent().getExtras().getString(Constants.IMAGE_URL);

        loadImage();
    }

    private void loadImage(){
        imageView = (ImageView) findViewById(R.id.image);
        Picasso.with(this)
                .load(imageUrl)
                .resize((int) getResources().getDimension(R.dimen.image_width), (int) getResources().getDimension(R.dimen.image_height))
                .centerCrop()
                .into(imageView);
    }


    private Transition enterTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(150);
        return bounds;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_standard) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
