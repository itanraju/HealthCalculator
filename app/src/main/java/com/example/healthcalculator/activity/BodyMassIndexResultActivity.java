package com.example.healthcalculator.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcalculator.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;

public class BodyMassIndexResultActivity extends AppCompatActivity {
    TextView bmiTxt;
    TextView extremely1,extremely2,under1,under2,normal1,normal2,over1,over2,obsenseone1,obsenseone2,obsensetwo1,obsensetwo2,morbid1,morbid2;
    ImageView understand,iv_back;
    SeekBar seekBar;
    float bmiValue;
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;
    Activity activity=BodyMassIndexResultActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_mass_index_result);
        bindView();
        BannerAds();
        DecimalFormat decimalFormat=new DecimalFormat("##.##");
        bmiValue= Float.parseFloat(decimalFormat.format(BodyMassIndexActivity.bmi));
        bmiTxt.setText(String.valueOf(bmiValue)+"%");

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        understand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BodyMassIndexResultActivity.this,UnderstandBodyIndexActivity.class));
            }
        });
        seekBar.setProgress((int) bmiValue);
        if(bmiValue >=0 && bmiValue < 16)
        {
            extremely1.setTextColor(Color.parseColor("#170a59"));
            extremely2.setTextColor(Color.parseColor("#170a59"));
            extremely1.setTypeface(null, Typeface.BOLD);
            extremely2.setTypeface(null, Typeface.BOLD);
            under1.setTextColor(Color.parseColor("#707070"));
            under2.setTextColor(Color.parseColor("#707070"));
            normal1.setTextColor(Color.parseColor("#707070"));
            normal2.setTextColor(Color.parseColor("#707070"));
            over1.setTextColor(Color.parseColor("#707070"));
            over2.setTextColor(Color.parseColor("#707070"));
            obsenseone1.setTextColor(Color.parseColor("#707070"));
            obsenseone2.setTextColor(Color.parseColor("#707070"));
            obsensetwo1.setTextColor(Color.parseColor("#707070"));
            obsensetwo2.setTextColor(Color.parseColor("#707070"));
            morbid1.setTextColor(Color.parseColor("#707070"));
            morbid2.setTextColor(Color.parseColor("#707070"));
        }
        else if(bmiValue >=16 && bmiValue<18.6)
        {
            extremely1.setTextColor(Color.parseColor("#707070"));
            extremely2.setTextColor(Color.parseColor("#707070"));
            under1.setTextColor(Color.parseColor("#170a59"));
            under2.setTextColor(Color.parseColor("#170a59"));
            under1.setTypeface(null, Typeface.BOLD);
            under2.setTypeface(null, Typeface.BOLD);
            normal1.setTextColor(Color.parseColor("#707070"));
            normal2.setTextColor(Color.parseColor("#707070"));
            over1.setTextColor(Color.parseColor("#707070"));
            over2.setTextColor(Color.parseColor("#707070"));
            obsenseone1.setTextColor(Color.parseColor("#707070"));
            obsenseone2.setTextColor(Color.parseColor("#707070"));
            obsensetwo1.setTextColor(Color.parseColor("#707070"));
            obsensetwo2.setTextColor(Color.parseColor("#707070"));
            morbid1.setTextColor(Color.parseColor("#707070"));
            morbid2.setTextColor(Color.parseColor("#707070"));
        }
        else if(bmiValue>=18.6 && bmiValue<=25)
        {
            extremely1.setTextColor(Color.parseColor("#707070"));
            extremely2.setTextColor(Color.parseColor("#707070"));
            under1.setTextColor(Color.parseColor("#707070"));
            under2.setTextColor(Color.parseColor("#707070"));
            normal1.setTextColor(Color.parseColor("#170a59"));
            normal2.setTextColor(Color.parseColor("#170a59"));
            normal1.setTypeface(null, Typeface.BOLD);
            extremely1.setTypeface(null, Typeface.BOLD);
            normal2.setTextColor(Color.parseColor("#707070"));
            over2.setTextColor(Color.parseColor("#707070"));
            obsenseone1.setTextColor(Color.parseColor("#707070"));
            obsenseone2.setTextColor(Color.parseColor("#707070"));
            obsensetwo1.setTextColor(Color.parseColor("#707070"));
            obsensetwo2.setTextColor(Color.parseColor("#707070"));
            morbid1.setTextColor(Color.parseColor("#707070"));
            morbid2.setTextColor(Color.parseColor("#707070"));
        }
        else if(bmiValue>=25.1 && bmiValue<=30)
        {
            extremely1.setTextColor(Color.parseColor("#707070"));
            extremely2.setTextColor(Color.parseColor("#707070"));
            under1.setTextColor(Color.parseColor("#707070"));
            under2.setTextColor(Color.parseColor("#707070"));
            normal1.setTextColor(Color.parseColor("#707070"));
            normal2.setTextColor(Color.parseColor("#707070"));
            over1.setTextColor(Color.parseColor("#170a59"));
            over2.setTextColor(Color.parseColor("#170a59"));
            over1.setTypeface(null, Typeface.BOLD);
            over2.setTypeface(null, Typeface.BOLD);
            obsenseone1.setTextColor(Color.parseColor("#707070"));
            obsenseone2.setTextColor(Color.parseColor("#707070"));
            obsensetwo1.setTextColor(Color.parseColor("#707070"));
            obsensetwo2.setTextColor(Color.parseColor("#707070"));
            morbid1.setTextColor(Color.parseColor("#707070"));
            morbid2.setTextColor(Color.parseColor("#707070"));
        }
        else if(bmiValue>=30.1 && bmiValue<=35)
        {
            extremely1.setTextColor(Color.parseColor("#707070"));
            extremely2.setTextColor(Color.parseColor("#707070"));
            under1.setTextColor(Color.parseColor("#707070"));
            under2.setTextColor(Color.parseColor("#707070"));
            normal1.setTextColor(Color.parseColor("#707070"));
            normal2.setTextColor(Color.parseColor("#707070"));
            over1.setTextColor(Color.parseColor("#707070"));
            over2.setTextColor(Color.parseColor("#707070"));
            obsenseone1.setTextColor(Color.parseColor("#170a59"));
            obsenseone2.setTextColor(Color.parseColor("#170a59"));
            obsenseone1.setTypeface(null, Typeface.BOLD);
            obsenseone2.setTypeface(null, Typeface.BOLD);
            obsensetwo1.setTextColor(Color.parseColor("#707070"));
            obsensetwo2.setTextColor(Color.parseColor("#707070"));
            morbid1.setTextColor(Color.parseColor("#707070"));
            morbid2.setTextColor(Color.parseColor("#707070"));
        }
        else if(bmiValue>=35.1 && bmiValue<=40)
        {
            extremely1.setTextColor(Color.parseColor("#707070"));
            extremely2.setTextColor(Color.parseColor("#707070"));
            under1.setTextColor(Color.parseColor("#707070"));
            under2.setTextColor(Color.parseColor("#707070"));
            normal1.setTextColor(Color.parseColor("#707070"));
            normal2.setTextColor(Color.parseColor("#707070"));
            over1.setTextColor(Color.parseColor("#707070"));
            over2.setTextColor(Color.parseColor("#707070"));
            obsenseone1.setTextColor(Color.parseColor("#707070"));
            obsenseone2.setTextColor(Color.parseColor("#707070"));
            obsensetwo1.setTextColor(Color.parseColor("#170a59"));
            obsensetwo2.setTextColor(Color.parseColor("#170a59"));
            obsensetwo1.setTypeface(null, Typeface.BOLD);
            obsensetwo2.setTypeface(null, Typeface.BOLD);
            morbid1.setTextColor(Color.parseColor("#707070"));
            morbid2.setTextColor(Color.parseColor("#707070"));
        }
        else
        {
            extremely1.setTextColor(Color.parseColor("#707070"));
            extremely2.setTextColor(Color.parseColor("#707070"));
            under1.setTextColor(Color.parseColor("#707070"));
            under2.setTextColor(Color.parseColor("#707070"));
            normal1.setTextColor(Color.parseColor("#707070"));
            normal2.setTextColor(Color.parseColor("#707070"));
            over1.setTextColor(Color.parseColor("#707070"));
            over2.setTextColor(Color.parseColor("#707070"));
            obsenseone1.setTextColor(Color.parseColor("#707070"));
            obsenseone2.setTextColor(Color.parseColor("#707070"));
            obsensetwo1.setTextColor(Color.parseColor("#707070"));
            obsensetwo2.setTextColor(Color.parseColor("#707070"));
            morbid1.setTextColor(Color.parseColor("#170a59"));
            morbid2.setTextColor(Color.parseColor("#170a59"));
            morbid2.setTypeface(null, Typeface.BOLD);
            obsensetwo2.setTypeface(null, Typeface.BOLD);
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

    private void bindView() {
        bmiTxt=findViewById(R.id.bmiTxt);
        seekBar=findViewById(R.id.seekbar);
        extremely1=findViewById(R.id.extremely1);
        extremely2=findViewById(R.id.extremely2);
        under1=findViewById(R.id.under1);
        under2=findViewById(R.id.under2);
        normal1=findViewById(R.id.normal1);
        normal2=findViewById(R.id.normal2);
        over1=findViewById(R.id.over1);
        over2=findViewById(R.id.over2);
        obsenseone1=findViewById(R.id.obeseone1);
        obsenseone2=findViewById(R.id.obeseone2);
        obsensetwo1=findViewById(R.id.obesetwo1);
        obsensetwo2=findViewById(R.id.obesetwo2);
        morbid1=findViewById(R.id.morbid1);
        morbid2=findViewById(R.id.morbid2);
        understand=findViewById(R.id.understand);
        iv_back=findViewById(R.id.iv_back);
    }
}