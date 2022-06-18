package uz.ngs.lesson_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
   MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mediaPlayer = MediaPlayer.create(this,R.raw.music);
        AppCompatButton startButton = findViewById(R.id.start_);
        AppCompatButton exitButton = findViewById(R.id.exist);
        AppCompatButton aboutButton = findViewById(R.id.about);
        AppCompatButton recordButton = findViewById(R.id.record);
        TextView textView = findViewById(R.id.TextRecord);


        recordButton.setOnClickListener(view -> {
           View dialogView = LayoutInflater.from(StartActivity.this).inflate(R.layout.win,null);
          TextView textView1 = dialogView.findViewById(R.id.TextRecord);
            if (MySharepreference.getInt("RECORD",Integer.MAX_VALUE) != Integer.MAX_VALUE) {
                textView1.setText("Steps : " +MySharepreference.getInt("RECORD",Integer.MAX_VALUE));
            }

            AlertDialog alertDialog = new AlertDialog.Builder(StartActivity.this).setView(dialogView).create();
            dialogView.findViewById(R.id.buttonOk).setOnClickListener(view1 -> {
                alertDialog.dismiss();
                Log.d("TTT","OK");
            });
            alertDialog.show();

        });
        aboutButton.setOnClickListener(view -> startActivity(new Intent(this,AboutActivity.class)));

        exitButton.setOnClickListener(view -> {
           finish();
            mediaPlayer.start();
        });
        startButton.setOnClickListener(view -> {
            startGame();
            mediaPlayer.start();
        });
    }
    private void startGame(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
