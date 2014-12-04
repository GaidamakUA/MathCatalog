package com.example.gaidamak.mathcatalog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Provides np UI itself, but manages {@link android.app.Fragment}
 */
public class MainActivity extends Activity implements FragmentManagingActivity {

    private static final String TAG = "MainActivity";

    /**
     * Initializing UI with first fragment {@link com.example.gaidamak.mathcatalog.MathListFragment}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Hide android icon
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
        // Swipe works because of it
        findViewById(R.id.container).setOnTouchListener(new OnSwipeTouchListener(this));
    }

    /**
     * Start {@link com.example.gaidamak.mathcatalog.ViewTermFragment_}
     *
     * @param id The id of MathTerm
     */
    @Override
    public void viewMathTerm(long id) {
        getFragmentManager().beginTransaction()
                // Animation
                .setCustomAnimations(R.animator.slide_in_left,  // Appear
                        R.animator.slide_out_left,              // Disappear
                        R.animator.slide_in_right,              // Appear on cancel
                        R.animator.slide_out_right)             // Disappear on cancel
                        // Sweat features of @AndroidAnnotations
                .replace(R.id.container, ViewTermFragment_.builder().termId(id).build())
                        // Make operation reversible
                .addToBackStack(null)
                .commit();
    }

    /**
     * Start {@link com.example.gaidamak.mathcatalog.EditTermFragment_}
     *
     * @param id The id of MathTerm
     */
    @Override
    public void editMathTerm(long id) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
                        R.animator.slide_in_right, R.animator.slide_out_right)
                .replace(R.id.container, EditTermFragment_.builder().termId(id).build())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Like editMathTerm, but meant to create new Math term
     */
    @Override
    public void addNewTerm() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
                        R.animator.slide_in_right, R.animator.slide_out_right)
                .replace(R.id.container, EditTermFragment_.builder().build())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Cancel previous transaction
     */
    @Override
    public void cancel() {
        getFragmentManager().popBackStack();
    }

    /**
     * Listener to make swipe work
     */
    public class OnSwipeTouchListener implements View.OnTouchListener {

        /**
         * This is the thing which can detect gesture including Swipe
         */
        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context ctx) {
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
    }
}
