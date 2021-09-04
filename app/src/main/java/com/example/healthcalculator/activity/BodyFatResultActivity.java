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

public class BodyFatResultActivity extends AppCompatActivity {
    TextView result,bodyFatResultTxt;
    int resultInt;
    private ImageView iv_back;
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;
    Activity activity=BodyFatResultActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat_result);
        BannerAds();
        result=findViewById(R.id.result);
        iv_back=findViewById(R.id.iv_back);
        bodyFatResultTxt=findViewById(R.id.bodyFatResultTxt);
        resultInt= (int) BodyFatActivity.finalAns;
        result.setText(String.valueOf(BodyFatActivity.finalAns)+" %");

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(BodyFatActivity.zender.equals("male"))
        {
            if(resultInt>=2 && resultInt<=5)
            {
                bodyFatResultTxt.setText("Essential % of fat");
            }
            else if(resultInt>=6 && resultInt<=13)
            {
                bodyFatResultTxt.setText("Typical Athlete");
            }
            else if(resultInt>=14 && resultInt<=17)
            {
                bodyFatResultTxt.setText("Physically Fit");
            }
            else if(resultInt>=18 && resultInt<=24)
            {
                bodyFatResultTxt.setText("Acceptable");
            }
            else if(resultInt>=25)
            {
                bodyFatResultTxt.setText("Obese");
            }
            else
            {
                bodyFatResultTxt.setText("enter valid data");
            }
        }
        else
        {
            if(resultInt>=10 && resultInt<=13)
            {
                bodyFatResultTxt.setText("Essential % of fat");
            }
            else if(resultInt>=14 && resultInt<=20)
            {
                bodyFatResultTxt.setText("Typical Athlete");
            }
            else if(resultInt>=21 && resultInt<=24)
            {
                bodyFatResultTxt.setText("Physically Fit");
            }
            else if(resultInt>=25 && resultInt<=31)
            {
                bodyFatResultTxt.setText("Acceptable");
            }
            else if(resultInt>=32)
            {
                bodyFatResultTxt.setText("Obese");
            }
            else
            {
                bodyFatResultTxt.setText("enter valid data");
            }
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