package com.natali_pi.home_money;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natali_pi.home_money.utils.OneButtonDialog;
import com.natali_pi.home_money.utils.SwipeableViewHolder;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.enums.EPickType;

/**
 * Created by Natali-Pi on 18.11.2017.
 */

public class BaseActivity extends AppCompatActivity {
    public static final String DATA = "DATA";
    private DrawerLayout drawer;
    PickSetup pickSetup = null;
    public  PickSetup getPickSetup(){
        if (pickSetup == null){
            pickSetup = new PickSetup()
                    .setTitle(getString(R.string.download_image))
                    .setTitleColor(getResources().getColor(R.color.darkViolet))
                    .setBackgroundColor(getResources().getColor(R.color.white))
                    //.setProgressText("progresstext")
                    .setCancelText(getString(R.string.cancel))
                    .setCancelTextColor(getResources().getColor(R.color.darkViolet))
                    //.setButtonTextColor(yourColor)
                    //.setDimAmount(yourFloat)
                    //.setFlip(true)
                    //.setMaxSize(500)
                    .setPickTypes(EPickType.GALLERY, EPickType.CAMERA)
                    //.setCameraButtonText(yourText)
                    //.setGalleryButtonText(yourText)
                    .setIconGravity(Gravity.LEFT)
                    //.setButtonOrientation(LinearLayoutCompat.VERTICAL)
                    .setSystemDialog(false)
                    //.setGalleryIcon(yourIcon)
                    //.setCameraIcon(yourIcon)
                        ;
        }
        return pickSetup;
    }
    protected void hideHighlight(){
    findViewById(R.id.highlight).setVisibility(View.GONE);
}

    public void setupToolbar(int navigationDrawableId, String title) {
        RelativeLayout toolbar = (RelativeLayout) findViewById(R.id.toolbar);

        ((TextView)toolbar.findViewById(R.id.titleText)).setText(title);
        ((ImageView)toolbar.findViewById(R.id.navigationButton)).setImageResource(navigationDrawableId);
        toolbar.setVisibility(View.VISIBLE);
    }

    public void setupOption(int drawableId) {
        ((ImageView)findViewById(R.id.optionButton)).setImageResource(drawableId);
    }
    public void setupSecondOption(int drawableId) {
        showSecondOption();
        ((ImageView)findViewById(R.id.secondOptionButton)).setImageResource(drawableId);
    }
    public void showSecondOption() {
        ((ImageView)findViewById(R.id.secondOptionButton)).setVisibility(View.VISIBLE);
    }
    public void hideSecondOption() {
        ((ImageView)findViewById(R.id.secondOptionButton)).setVisibility(View.GONE);
    }
    protected void setSecondOptionButtonListener (View.OnClickListener listener){
        ((ImageView) findViewById(R.id.secondOptionButton)).setOnClickListener(listener);

    }
    protected void setOptionButtonListener (View.OnClickListener listener){
        ImageView optionButton = (ImageView) findViewById(R.id.optionButton);
        optionButton.setOnClickListener(listener);
    }
    public void setupLabel(String text){
        TextView label = (TextView) findViewById(R.id.label);
        if(text != null) {
            label.setText(text);
            label.setVisibility(View.VISIBLE);
        } else {
            label.setVisibility(View.GONE);
        }
    }

    public void setupSideDrawer (){

        if (getDrawer() != null){
            getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            findViewById(R.id.navigationButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDrawer().openDrawer(Gravity.LEFT);
                }
            });
        }
    }

    protected void setBaseContentView(@LayoutRes int layoutId){
        setContentView(R.layout.main_holder_layout);
        LinearLayout mainHolder = (LinearLayout) findViewById(R.id.mainHolder);
        mainHolder.addView(getLayoutInflater().inflate(layoutId, mainHolder, false));
        getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    protected DrawerLayout getDrawer(){
        if (drawer == null) {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        }
            return drawer;
    }
    protected void setupFont(TextView view){
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/YuGothB.ttc");
        view.setTypeface(custom_font);
    }
    protected void setNavigationButtonListener (View.OnClickListener listener){
        ImageView navigationButton = (ImageView) findViewById(R.id.navigationButton);
        navigationButton.setOnClickListener(listener);
    }
    protected void setHighlightedText(String text){
        TextView highlitedText = (TextView) findViewById(R.id.highlitedText);
        if(text != null){
            highlitedText.setVisibility(View.VISIBLE);
            highlitedText.setText(text);
        } else {
            highlitedText.setVisibility(View.GONE);
        }

    }
    protected BackOnPress getBackAction(){
        return new BackOnPress();
    }

    public void showMessage(String text){
        new OneButtonDialog(this, OneButtonDialog.DIALOG_TYPE.MESSAGE_ONLY)
                .setMessage(text)
                //.setCustomTextStyle(R.style.standard_margin)
                .setCustomTextStyle(R.style.dialog_title_style)
                .build();
    }
    public void onError() {
        onFinishLoading();
    }

    public void onFinishLoading() {
        ProgressBar progress = (ProgressBar) findViewById(R.id.progress);
        if(progress != null) {
            progress.setVisibility(View.GONE);
        }
    }

    public void onStartLoading() {
        ProgressBar progress = (ProgressBar) findViewById(R.id.progress);
        if(progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
    }

    protected class BackOnPress implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            finish();
        }
    }
    public String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }


    RecyclerView.ItemDecoration itemDecoration = null;
    public void removeAnimationDecoratorHelper(RecyclerView recyclerView) {
        if (itemDecoration != null) {
            recyclerView.removeItemDecoration(itemDecoration);
        }
    }
    /**
     * We're gonna setup another ItemDecorator that will draw the
     *
     * background in the empty space while the items are animating to thier new positions
     * after an item is removed.
     */
    public void setUpAnimationDecoratorHelper(RecyclerView recyclerView) {

        recyclerView.addItemDecoration(itemDecoration = new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(getResources().getColor(R.color.darkViolet));
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }

    ItemTouchHelper mItemTouchHelper = null;
    public void removeItemTouchHelper() {
        if (mItemTouchHelper != null){
            mItemTouchHelper.attachToRecyclerView(null);
        }
    }
    //mItemTouchHelper.attachToRecyclerView(null);
    /**
     * This is the standard support library way of implementing "swipe to delete" feature. You can do custom drawing in onChildDraw method
     * but whatever you draw will disappear once the swipe is over, and while the items are animating to their new position the recycler view
     * background will be visible. That is rarely an desired effect.
     */
    public void setUpItemTouchHelper(RecyclerView recyclerView) {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(getResources().getColor(R.color.darkViolet));
                xMark = ContextCompat.getDrawable(BaseActivity.this, R.drawable.ok);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) BaseActivity.this.getResources().getDimension(R.dimen.standard_margin);
                initiated = true;
            }

            // not important, we don't want drag & drop
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                BaseAdapter testAdapter = (BaseAdapter)recyclerView.getAdapter();
                if (testAdapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                BaseAdapter adapter = (BaseAdapter)recyclerView.getAdapter();

                    adapter.pendingRemoval(swipedPosition);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }

}

