package uz.ngs.lesson_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class FistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fist);



        //reverseTimer(1);

        if(Build.VERSION.SDK_INT>23){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                LottieAnimationView animationView = findViewById(R.id.animation);
                animationView.playAnimation();
                animationView.setProgress(0);
                animationView.pauseAnimation();
                Intent intent=new Intent(FistActivity.this,StartActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        },3000);

    }



}