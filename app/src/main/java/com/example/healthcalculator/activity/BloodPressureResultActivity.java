package com.example.healthcalculator.activity;

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
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthcalculator.R;

public class BloodPressureResultActivity extends AppCompatActivity {
    private TextView result;
    private ImageView iv_back;
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;
    Activity activity=BloodPressureResultActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_result);
        bindView();
        BannerAds();


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        if(BloodPressureActivity.selectedSystolic>180.0f || BloodPressureActivity.selectedDiastolic>110.0f)
        {
            result.setText("Hypertensive Crisis");
        }
        else if(BloodPressureActivity.selectedSystolic>=160 ||  BloodPressureActivity.selectedDiastolic>=100.0f)
        {
            result.setText("High Blood Pressure (Stage 2)");
        }
        else if(BloodPressureActivity.selectedSystolic>140.0f ||  BloodPressureActivity.selectedDiastolic>90.0f)
        {
            result.setText("High Blood Pressure (Stage 2)");
        }
        else if(BloodPressureActivity.selectedSystolic>120.0f ||  BloodPressureActivity.selectedDiastolic>80.0f)
        {
            result.setText("Prehypertension");
        }
        else if(BloodPressureActivity.selectedSystolic<=80.0f ||  BloodPressureActivity.selectedDiastolic<=60.0f)
        {
            result.setText("Low");
        }
        else
        {
            result.setText("Normal");
        }
    }

    private void bindView() {
        result=findViewById(R.id.result);
        iv_back=findViewById(R.id.iv_back);
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