package uz.ngs.lesson_2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final int TIME_INTERVAL = 2000;
    private long back;
    private final TextView[][] cell = new TextView[4][4];
    private final ArrayList<Integer> numbers = new ArrayList<>(15);
    private final String[] nums = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    private int x = 3;
    private int y = 3;
    private TextView countTextView;
    private static int count = 0;

    public static int getCount() {
        return count;
    }

    MediaPlayer mediaPlayer;
    MediaPlayer mediaPlayer2;
    MediaPlayer mediaPlayer3;
    Chronometer chronometer;
    long oldTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countTextView = findViewById(R.id.counter);
        chronometer = findViewById(R.id.time);
        mediaPlayer = MediaPlayer.create(this, R.raw.zzzz);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.beckround_music);
        mediaPlayer3 = MediaPlayer.create(this,R.raw.aaaaa);

         chronometer.start();
        loadViews();
        loadData();
        loadDataToView();
        ImageView buttonBack = findViewById(R.id.buttonBack);
        ImageView buttonSound = findViewById(R.id.sound);
        ImageView buttonNoSound = findViewById(R.id.noSound);

        buttonSound.setOnClickListener(view -> {
            mediaPlayer2.pause();
            buttonSound.setVisibility(View.INVISIBLE);
            buttonNoSound.setVisibility(View.VISIBLE);
        });
        buttonNoSound.setOnClickListener(view -> {
            mediaPlayer2.start();
            buttonNoSound.setVisibility(View.INVISIBLE);
            buttonSound.setVisibility(View.VISIBLE);

        });
        buttonBack.setOnClickListener(view -> {
            mediaPlayer2.pause();
            finish();
        });
        findViewById(R.id.buttonShuffle).setOnClickListener(view -> {
            mediaPlayer3.start();
            chronometer.setBase(SystemClock.elapsedRealtime());
            shuffle();


        });
    }

    @SuppressLint("SetTextI18n")
    private void loadViews() {
        ConstraintLayout parent = findViewById(R.id.table);
        for (int i = 0; i < parent.getChildCount(); i++) {
            cell[i / 4][i % 4] = (TextView) parent.getChildAt(i);
            cell[i / 4][i % 4].setTag(i);
            cell[i / 4][i % 4].setOnClickListener(view -> {
                int amount = (Integer) view.getTag();
                move(amount / 4, amount % 4);
                if (check()) {
                    if (MySharepreference.getInt("RECORD",0)>count) {
                        MySharepreference.putInt("RECORD", count);
                    }
                    LottieAnimationView animationView = findViewById(R.id.finish__);
                    animationView.playAnimation();
                    animationView.setProgress(0);
                    animationView.pauseAnimation();
                    View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.finish,null);

                    TextView textView2 = dialogView.findViewById(R.id.counts_);

                    textView2.setText("steps" + count);
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setView(dialogView).create();
                     dialogView.findViewById(R.id.buttonfinish).setOnClickListener(view1 -> finish());
                     dialogView.setSelected(false);
                     dialogView.findViewById(R.id.buttonRestart).setOnClickListener(view1 -> {
                         shuffle();
                         alertDialog.dismiss();
                     });
                     alertDialog.show();
                }

            });
        }
    }

    private void loadData() {
        for (int i = 0; i < 15; i++) {
            numbers.add(i + 1);
        }
        shuffle();
    }

    private void  shuffle () {
        count = 0;
        countTextView.setText("Steps " + count);
        Collections.shuffle(numbers);
        int s = 0;
        for (int i = 0; i < numbers.size() - 1; i++) {
            for (int j = i + 1; j <numbers.size() ; j++) {
                if (numbers.get(i) > numbers.get(j)) s++;
            }
        }
        if (s % 2 != 0) shuffle();
        loadDataToView();
    }

    @Override
    public void onBackPressed() {

    }

    private void loadDataToView() {
        cell[x][y].setVisibility(View.VISIBLE);
        x = 3;
        y = 3;
        cell[x][y].setVisibility(View.INVISIBLE);
        for (int i = 0; i < 15; i++) {
            cell[i / 4][i % 4].setText(String.valueOf(numbers.get(i)));
        }
    }
    private void move(int i, int j) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.abc_grow_fade_in_from_bottom);
        if (i == x || j == y) {
            mediaPlayer.start();
            if (i == x && j < y) {
                cell[x][y].setVisibility(View.VISIBLE);
                for (int k = y; k > j; k--) {
                    cell[x][k].setText(cell[i][k - 1].getText());

                }
                cell[i][j].setVisibility(View.INVISIBLE);
                x = i;
                y = j;
                count++;
                countTextView.setText("Steps " + count);
            }
            if (i == x && j > y) {
                cell[x][y].setVisibility(View.VISIBLE);
                for (int k = y; k < j; k++) {
                    cell[x][k].setText(cell[x][k + 1].getText());
                }
                cell[i][j].setVisibility(View.INVISIBLE);
                x = i;
                y = j;
                count++;
                countTextView.setText("Steps " + count);
            }
            if (j == y && i > x) {
                cell[x][y].setVisibility(View.VISIBLE);
                for (int k = x; k < i; k++) {
                    cell[k][j].setText(cell[k + 1][j].getText());
                }
                cell[i][j].setVisibility(View.INVISIBLE);
                x = i;
                y = j;
                count++;
                countTextView.setText("Steps " + count);
            }
            if (j == y && i < x) {
                cell[x][y].setVisibility(View.VISIBLE);
                for (int k = x; k > i; k--) {
                    cell[k][j].setText(cell[k - 1][j].getText());
                }
                cell[i][j].setVisibility(View.INVISIBLE);
                x = i;
                y = j;
                count++;
                countTextView.setText("Steps " + count);

            }
        }
    }



    private boolean check() {
        for (int i = 0; i < 15; i++) {
            if (!cell[i / 4][i % 4].getText().equals(nums[i])) {
                return false;
            }
        }
        return true;

    }



    @Override
    protected void onStart() {
        super.onStart();
        chronometer.setBase(SystemClock.elapsedRealtime() + oldTime);
    }

    @Override
    protected void onPause() {
        ImageView buttonSound = findViewById(R.id.sound);
        ImageView buttonNoSound = findViewById(R.id.noSound);
        mediaPlayer2.pause();
        buttonSound.setVisibility(View.INVISIBLE);
        buttonNoSound.setVisibility(View.VISIBLE);
        super.onPause();
        oldTime = chronometer.getBase() - SystemClock.elapsedRealtime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView buttonSound = findViewById(R.id.sound);
        ImageView buttonNoSound = findViewById(R.id.noSound);
        buttonSound.setOnClickListener(view -> {
            mediaPlayer2.pause();
            buttonSound.setVisibility(View.INVISIBLE);
            buttonNoSound.setVisibility(View.VISIBLE);
        });
    }
}