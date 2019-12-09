package com.example.cardsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView t;

    int click=0;

    int lastclicked;

    ArrayList<ImageView> imageviews = new ArrayList<>();
    ArrayList<String> cardOrientation = new ArrayList<>();

    ArrayList<Integer> animals  = new ArrayList<>(Arrays.asList(R.drawable.bird , R.drawable.cat , R.drawable.dog ,
            R.drawable.frog , R.drawable.monkey , R.drawable.mouse , R.drawable.penguin , R.drawable.bear ,
            R.drawable.bird , R.drawable.cat , R.drawable.dog , R.drawable.frog , R.drawable.monkey , R.drawable.mouse ,
            R.drawable.penguin , R.drawable.bear));

    boolean canClick = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        //Randomize the animals images
//        Collections.shuffle(animals);

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
                        imageviews.add((ImageView)childView);
                        cardOrientation.add("cardback");
                    }
                }
            }
        }


    }

    /* This method is invoked when activity screen is clicked. */
    @Override
    public void onClick(View view) {

        if(!canClick)
            return;
        
        final int i = view.getId();

        final ImageView image = (ImageView) view;

        if("cardback".equalsIgnoreCase(cardOrientation.get(i)) && click < 2){
            canClick = false;

            final ObjectAnimator oa1 = ObjectAnimator.ofFloat(image, "scaleX", 1f, 0f);
            final ObjectAnimator oa2 = ObjectAnimator.ofFloat(image, "scaleX", 0f, 1f);
            oa1.setInterpolator(new DecelerateInterpolator());
            oa2.setInterpolator(new AccelerateDecelerateInterpolator());
            oa1.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    image.setImageResource((Integer) animals.get(i));
                    oa2.start();
                }
            });

            oa2.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    click++;

                    if(click == 1) {
                        lastclicked = i;
                        canClick = true;
                    }

                    if(click == 2)
                    {
                        click = 0;
                        int x = animals.get(i);
                        int y = animals.get(lastclicked);

                        if(x == y) {
//                            Toast.makeText(getApplicationContext(), "Match", Toast.LENGTH_LONG).show();
                            image.setClickable(false);
                            imageviews.get(lastclicked).setClickable(false);
                            String audioName = getResources().getResourceName(x);
                            audioName = audioName.split("/")[1];
                            PlayAudio(audioName);
                            //Toast.makeText(getApplicationContext(), getResources().getResourceName(x), Toast.LENGTH_LONG).show();
                            canClick = true;
                    }
                        else
                        {
                            final ObjectAnimator oa1 = ObjectAnimator.ofFloat(image, "scaleX", 1f, 0f);
                            final ObjectAnimator oa2 = ObjectAnimator.ofFloat(image, "scaleX", 0f, 1f);
                            oa1.setInterpolator(new DecelerateInterpolator());
                            oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                            oa1.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    image.setImageResource(R.drawable.pawprint);
                                    oa2.start();
                                }
                            });
                            oa1.start();

                            final ObjectAnimator oa3 = ObjectAnimator.ofFloat(imageviews.get(lastclicked), "scaleX", 1f, 0f);
                            final ObjectAnimator oa4 = ObjectAnimator.ofFloat(imageviews.get(lastclicked), "scaleX", 0f, 1f);
                            oa3.setInterpolator(new DecelerateInterpolator());
                            oa4.setInterpolator(new AccelerateDecelerateInterpolator());
                            oa3.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    imageviews.get(lastclicked).setImageResource(R.drawable.pawprint);
                                    oa4.start();
                                }
                            });

                            oa4.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    canClick = true;
                                }
                            });

                            oa3.start();
                        }
                    }
                }
            });

            oa1.start();

//            click++;

//            if(click ==1)
//                lastclicked = i;
        }

        else if(!"cardback".equalsIgnoreCase(cardOrientation.get(i))){

        }

        if(click == 2)
        {
//            if(image.getId() == imageviews.get(lastclicked).getId())
//                Toast.makeText(getApplicationContext(), "Match", Toast.LENGTH_LONG).show();
//
//            else
//            {
//                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(image, "scaleX", 1f, 0f);
//                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(image, "scaleX", 0f, 1f);
//                oa1.setInterpolator(new DecelerateInterpolator());
//                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
//                oa1.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        image.setImageResource(R.drawable.pawprint);
//                        oa2.start();
//                    }
//                });
//                oa1.start();
//
//                final ObjectAnimator oa3 = ObjectAnimator.ofFloat(imageviews.get(lastclicked), "scaleX", 1f, 0f);
//                final ObjectAnimator oa4 = ObjectAnimator.ofFloat(imageviews.get(lastclicked), "scaleX", 0f, 1f);
//                oa1.setInterpolator(new DecelerateInterpolator());
//                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
//                oa1.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        imageviews.get(lastclicked).setImageResource(R.drawable.pawprint);
//                        oa2.start();
//                    }
//                });
//                oa1.start();
//            }

        }
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




    }

    public void PlayAudio(String name){
        int ID = getResources().getIdentifier(name, "raw", getPackageName());
        MediaPlayer audio = MediaPlayer.create(this ,ID);
        audio.setLooping(false);
        audio.setVolume(100, 100);
        audio.start();
    }
}
