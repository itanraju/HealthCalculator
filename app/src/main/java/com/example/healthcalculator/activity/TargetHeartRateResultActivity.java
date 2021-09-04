package com.example.healthcalculator.activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class TargetHeartRateResultActivity extends AppCompatActivity {
    private double result1=0,result2=0;
    private TextView maxRate,result;
    private ImageView iv_back;
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;
    Activity activity=TargetHeartRateResultActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_heart_rate_result);
        BannerAds();
        maxRate=findViewById(R.id.maxRate);
        result=findViewById(R.id.result);
        iv_back=findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DecimalFormat decimalFormat=new DecimalFormat("###.#");
        result1= Double.parseDouble(decimalFormat.format(TargetHeartRateActivity.result1));
        result2= Double.parseDouble(decimalFormat.format(TargetHeartRateActivity.result2));

        result.setText(result1+"-"+result2);

        if(TargetHeartRateActivity.selectedAge<=20)
        {
            maxRate.setText("200");
        }
        if(TargetHeartRateActivity.selectedAge>20 && TargetHeartRateActivity.selectedAge<=30)
        {
            maxRate.setText("190");
        }
        if(TargetHeartRateActivity.selectedAge>35 && TargetHeartRateActivity.selectedAge<=40)
        {
            maxRate.setText("185");
        }
        if(TargetHeartRateActivity.selectedAge>40 && TargetHeartRateActivity.selectedAge<=45)
        {
            maxRate.setText("180");
        }
        if(TargetHeartRateActivity.selectedAge>45 && TargetHeartRateActivity.selectedAge<=50)
        {
            maxRate.setText("175");
        }
        if(TargetHeartRateActivity.selectedAge>50 && TargetHeartRateActivity.selectedAge<=55)
        {
            maxRate.setText("170");
        }
        if(TargetHeartRateActivity.selectedAge>55 && TargetHeartRateActivity.selectedAge<=60)
        {
            maxRate.setText("165");
        }
        if(TargetHeartRateActivity.selectedAge>60 && TargetHeartRateActivity.selectedAge<=65)
        {
            maxRate.setText("160");
        }
        if(TargetHeartRateActivity.selectedAge>65 && TargetHeartRateActivity.selectedAge<=70)
        {
            maxRate.setText("155");
        }
        if(TargetHeartRateActivity.selectedAge>70 && TargetHeartRateActivity.selectedAge<=75)
        {
            maxRate.setText("150");
        }
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