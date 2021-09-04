package com.example.healthcalculator.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import com.example.healthcalculator.R;

import java.text.DecimalFormat;

public class DailyCaloriesResultActivity extends AppCompatActivity {
    TextView dailyCaloriesTxt,targetCaloriesTxt;
    ImageView iv_back;
    String dailyCalories,targetCalories;
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;

    Activity activity=DailyCaloriesResultActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calories_result);
        BannerAds();
        dailyCaloriesTxt=findViewById(R.id.dailyCalories);
        targetCaloriesTxt=findViewById(R.id.targetCalories);
        iv_back=findViewById(R.id.iv_back);

        dailyCalories=getIntent().getStringExtra("amr");
        targetCalories=getIntent().getStringExtra("dailyCalories");

        float f=Float.valueOf(dailyCalories);

        dailyCaloriesTxt.setText(new DecimalFormat("####.##").format(f));
        targetCaloriesTxt.setText(targetCalories);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DailyCaloriesResultActivity.this,DailyCaloriesActivity.class));
                finish();
            }
        });

    }
    private void BannerAds() {
        try {
            adContainerView = findViewById(R.id.banner_ad_view_container);
            Display defaultDisplay = getWindowManager().getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics);
            float f = displayMetrics.density;
            float width = (float) adContainerView.getWidth();
            if (width == 0.0f) {
                width = (float) displayMetrics.widthPixels;
            }
            adSize = AdSize.getPortraitAnchoredAdaptiveBannerAdSize(this, (int) (width / f));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) adContainerView.getLayoutParams();
            layoutParams.height = adSize.getHeightInPixels(this);
            adContainerView.setLayoutParams(layoutParams);
            adContainerView.post(new Runnable() {
                public final void run() {
                    ShowAds();
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void ShowAds() {
        try {
            adView = new AdView(activity);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                }
            });
            adView.setAdUnitId(getString(R.string.Banner_ad_id));
            adContainerView.removeAllViews();
            adContainerView.addView(adView);
            adView.setAdSize(adSize);
            adView.loadAd(new AdRequest.Builder().build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}