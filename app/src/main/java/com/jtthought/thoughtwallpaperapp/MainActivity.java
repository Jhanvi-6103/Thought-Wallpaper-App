package com.jtthought.thoughtwallpaperapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView imageView = findViewById(R.id.imageView);
        Button startButton = findViewById(R.id.btn_start);
        TextView textViewInteraction = findViewById(R.id.textView_interaction);


        ObjectAnimator fadeInImage = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f);
        fadeInImage.setDuration(1500);
        fadeInImage.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeInImage.start();

        // Fade-in animation for TextView
        ObjectAnimator fadeInText = ObjectAnimator.ofFloat(textViewInteraction, "alpha", 0f, 1f);
        fadeInText.setDuration(2000);
        fadeInText.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeInText.start();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ThoughtsActivity.class));
            }
        });
    }
}
