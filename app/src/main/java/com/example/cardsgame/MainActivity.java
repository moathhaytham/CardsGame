package com.example.cardsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cardsgame.R.drawable.*;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView t;

    ArrayList imageviews = new ArrayList<ImageView>();
    ArrayList cardOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //Get ImageViews From the LinearLayout and add Action Listener
        LinearLayout linearLayout = findViewById(R.id.imagesWrapper);



        int elementscount = linearLayout.getChildCount();

        // Loop the Vertical Linear Layout.
        for(int i=0;i<elementscount;i++)
        {
            // Get each Horizontal Linear Layout.
            View childView = linearLayout.getChildAt(i);

            if(childView instanceof LinearLayout) {
                LinearLayout horzontalLayout = (LinearLayout) childView;

                // Get table row child view count.
                int childCount = horzontalLayout.getChildCount();

                for(int j=0;j<childCount;j++) {

                    childView = horzontalLayout.getChildAt(j);

                    // If the table row's child is a ImageView then set the ImageView to use this activity as onclick listener.
                    if (childView instanceof ImageView) {
                        childView.setOnClickListener(this);
                        childView.setId((4*i)+j);
                        imageviews.add(childView);
                    }
                }
            }
        }


    }

    /* This method is invoked when activity screen is clicked. */
    @Override
    public void onClick(View view) {


        int v = view.getId();

        final ImageView image = (ImageView) view;
        //String T = Integer.toString(v);
        //Toast.makeText(getApplicationContext(), T, Toast.LENGTH_LONG).show();

        //Trial 1
        /*
        ObjectAnimator flip = ObjectAnimator.ofFloat(image, "rotationY", 0f, 180f);
        flip.setTarget(image);
        flip.setDuration(500);

        flip.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                image.setImageResource(R.drawable.fox);
            }
        });
        flip.start();
        */

        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(image, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(image, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                image.setImageResource(R.drawable.fox);
                oa2.start();
            }
        });
        oa1.start();


    }
}
