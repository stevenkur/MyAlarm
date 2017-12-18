package com.example.aryapk.myalarm.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.aryapk.myalarm.MyService;
import com.example.aryapk.myalarm.R;
import com.example.aryapk.myalarm.adapters.AlarmOverviewModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Andy on 12/15/2017.
 */

public class GameActivity extends AppCompatActivity implements Observer<AlarmOverviewModel>{

    ImageView ivPic1, ivPic2, ivPic3, ivPic4, ivPic5;
    LinearLayout llGame1, llGame2;
    int point=0;
    Context context;

    //array for the images
    Integer[] cardsArray = {1, 2, 3, 4};

    Button btnNext, btnBack;
    TextClock tcTime;
    TextView tvPoint;

    //images
    int image1, image2, image3, image4;

    int tagPicture;

    public GameActivity(){}

    public GameActivity(Context context){
        this.context = context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        context = this;

        /*Intent intent = new Intent(this, MyService.class);
        intent.putExtra("uri",)*/

        //startService(new Intent(this, MyService.class));
        tcTime = (TextClock) findViewById(R.id.tcTime);
        tvPoint = (TextView) findViewById(R.id.tvPoint);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnBack = (Button) findViewById(R.id.btnBack);

        llGame1 = (LinearLayout) findViewById(R.id.llGame1);
        llGame2 = (LinearLayout) findViewById(R.id.llGame2);

        ivPic1 = (ImageView) findViewById(R.id.ivPic1);
        ivPic2 = (ImageView) findViewById(R.id.ivPic2);
        ivPic3 = (ImageView) findViewById(R.id.ivPic3);
        ivPic4 = (ImageView) findViewById(R.id.ivPic4);
        ivPic5 = (ImageView) findViewById(R.id.ivPic5);

        ivPic1.setTag("0");
        ivPic2.setTag("1");
        ivPic3.setTag("2");
        ivPic4.setTag("3");

        tagPicture = new Random().nextInt(4);
        ivPic5.setTag(tagPicture);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llGame1.setVisibility(View.GONE);
                llGame2.setVisibility(View.VISIBLE);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llGame1.setVisibility(View.VISIBLE);
                llGame2.setVisibility(View.GONE);
            }
        });

        tvPoint.setText(point + " POINT");

        loadImages();

        shuffle();

        ivPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                checkCorrect(theCard);
            }
        });

        ivPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                checkCorrect(theCard);
            }
        });

        ivPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                checkCorrect(theCard);
            }
        });

        ivPic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                checkCorrect(theCard);
            }
        });
    }

    private void shuffle(){
        Collections.shuffle(Arrays.asList(cardsArray));
        shuffleCard(ivPic1,0);
        shuffleCard(ivPic2,1);
        shuffleCard(ivPic3,2);
        shuffleCard(ivPic4,3);
        shuffleCard(ivPic5,tagPicture);
    }

    private void shuffleCard(ImageView iv, int card){
        if(cardsArray[card] == 1){
            iv.setImageResource(image1);
        } else if(cardsArray[card] == 2){
            iv.setImageResource(image2);
        } else if(cardsArray[card] == 3){
            iv.setImageResource(image3);
        } else if(cardsArray[card] == 4){
            iv.setImageResource(image4);
        }
    }

    private void checkCorrect(int theCard){
        if(tagPicture == theCard){
            point++;
            tvPoint.setText(point+" POINT");
            if(point==3) {
                stopService(new Intent(this, MyService.class));
                finish();
            }
        }
        shuffle();
        llGame1.setVisibility(View.VISIBLE);
        llGame2.setVisibility(View.GONE);
    }

    private void loadImages(){
        image1 = R.drawable.pic1;
        image2 = R.drawable.pic2;
        image3 = R.drawable.pic3;
        image4 = R.drawable.pic4;
    }

    @Override
    public void notify(AlarmOverviewModel alarmOverviewModel) {
        playMusic(alarmOverviewModel.getPath());
    }

    public void playMusic(String pathMusic){
        Intent intent = new Intent(context,MyService.class);
        intent.putExtra("extra",pathMusic);
        context.startService(intent);
    }
}