package com.example.gaidamak.mathcatalog;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class MainActivity extends Activity implements FragmentManagingActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayShowHomeEnabled(false);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MathListFragment_())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.container).setOnTouchListener(new OnSwipeTouchListener(this));
    }

    @Override
    public void viewMathTerm(long id) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
                        R.animator.slide_in_right, R.animator.slide_out_right)
                .replace(R.id.container, ViewTermFragment_.builder().termId(id).build())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void editMathTerm(long id) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
                        R.animator.slide_in_right, R.animator.slide_out_right)
                .replace(R.id.container, EditTermFragment_.builder().termId(id).build())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addNewTerm() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
                        R.animator.slide_in_right, R.animator.slide_out_right)
                .replace(R.id.container, EditTermFragment_.builder().build())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancel() {
        getFragmentManager().popBackStack();
    }

    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx){
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                    } else {
                        if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                onSwipeBottom();
                            } else {
                                onSwipeTop();
                            }
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
            cancel();
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }
}
