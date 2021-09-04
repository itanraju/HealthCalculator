package com.example.healthcalculator.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.healthcalculator.MainActivity;
import com.example.healthcalculator.R;
import com.example.healthcalculator.adapter.AgeAdapter;
import com.example.healthcalculator.adapter.HeightAdapter;
import com.example.healthcalculator.kprogresshud.KProgressHUD;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class IdealWeightActivity extends AppCompatActivity {
    ImageView male, female, feetImg, meterImg, idealWeightChart, calculate,reset,iv_back;
    RecyclerView ageRecycleView, heightRecycleView;
    List<Integer> ageNumber = new ArrayList<>();
    List<Integer> heightNumber = new ArrayList<>();
    AgeAdapter ageAdapter;
    RecyclerView.LayoutManager layoutManager;
    public static int selectedAge = 0, selectedHeight = 0;
    public static float weightKgMale, weightIbsMale, weightKgFemale, weightIbsFemale;
    private String zender = "male";
    public static String heightType = "centimeter";
    private SharedPreferences sharedPreferences;
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    //InterstitialAds

    private int id;
    public InterstitialAd mInterstitialAd;
    private KProgressHUD hud;

    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;

    Activity activity=IdealWeightActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideal_weight);
        bindView();
        BannerAds();
        interstitialAd();
        sharedPreferences=getSharedPreferences("UserProfile",MODE_PRIVATE);
        String data=sharedPreferences.getString("zender","");
        if(data!="")
        {
            getDataFromSharedPreference();
        }
        else
        {
            selectedAge=22;
            selectedHeight=154;
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zender = "male";
                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) female.getLayoutParams();
                marginParams.setMargins(1, 1, 1, 1);
                female.setPadding(0, 0, 40, 0);
                ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) male.getLayoutParams();
                marginParams1.setMargins(0, 0, 0, 0);
                male.setPadding(0, 0, 0, 0);
                male.setImageResource(R.drawable.male_p);
                female.setImageResource(R.drawable.female_u);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zender = "female";
                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) male.getLayoutParams();
                marginParams.setMargins(1, 1, 1, 1);
                male.setPadding(40, 0, 0, 0);
                ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) female.getLayoutParams();
                marginParams1.setMargins(0, 0, 0, 0);
                female.setPadding(0, 0, 0, 0);
                male.setImageResource(R.drawable.male_u);
                female.setImageResource(R.drawable.female_p);
            }
        });

        feetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightType = "centimeter";
                feetImg.setImageResource(R.drawable.cms_p);
                meterImg.setImageResource(R.drawable.inches_u);
                if (heightType.equals("centimeter")) {
                    heightNumber.clear();
                    for (int i = 0; i <= 200; i++) {
                        heightNumber.add(i);
                    }
                } else {
                    heightNumber.clear();
                    for (int i = 0; i <= 100; i++) {
                        heightNumber.add(i);
                    }
                }
                heightRecycleView.setOnFlingListener(null);
                SnapHelper snapHelper2 = new LinearSnapHelper();
                HeightAdapter heightAdapter = new HeightAdapter(heightNumber, IdealWeightActivity.this,selectedHeight);
                layoutManager = new LinearLayoutManager(IdealWeightActivity.this, LinearLayoutManager.HORIZONTAL, false);
                heightRecycleView.setLayoutManager(layoutManager);
                heightRecycleView.setAdapter(heightAdapter);
                heightRecycleView.smoothScrollToPosition(selectedHeight);
                ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
                heightRecycleView.setHasFixedSize(true);
                snapHelper2.attachToRecyclerView(heightRecycleView);
            }
        });

        meterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightType = "inches";
                feetImg.setImageResource(R.drawable.cms_u);
                meterImg.setImageResource(R.drawable.inches_p);
                if (heightType.equals("centimeter")) {
                    heightNumber.clear();
                    for (int i = 0; i <= 200; i++) {
                        heightNumber.add(i);
                    }
                } else {
                    heightNumber.clear();
                    for (int i = 0; i <= 100; i++) {
                        heightNumber.add(i);
                    }
                }
                heightRecycleView.setOnFlingListener(null);
                SnapHelper snapHelper2 = new LinearSnapHelper();
                HeightAdapter heightAdapter = new HeightAdapter(heightNumber, IdealWeightActivity.this,selectedHeight);
                layoutManager = new LinearLayoutManager(IdealWeightActivity.this, LinearLayoutManager.HORIZONTAL, false);
                heightRecycleView.setLayoutManager(layoutManager);
                heightRecycleView.setAdapter(heightAdapter);
                heightRecycleView.smoothScrollToPosition(20);
                ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(30, 0);
                heightRecycleView.setHasFixedSize(true);
                snapHelper2.attachToRecyclerView(heightRecycleView);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultData();
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if (mInterstitialAd!=null&&mInterstitialAd.isLoaded()){
                    try {
                        hud = KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Showing Ads").setDetailsLabel("Please Wait...");
                        hud.show();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e2) {
                        e2.printStackTrace();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                hud.dismiss();
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();

                            } catch (NullPointerException e2) {
                                e2.printStackTrace();
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                                id = 1;
                                mInterstitialAd.show();
                            }
                        }
                    }, 2000);
                }
                else {
                    getResult();
                }
            }
        });

        idealWeightChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd!=null&&mInterstitialAd.isLoaded()){
                    try {
                        hud = KProgressHUD.create(activity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Showing Ads").setDetailsLabel("Please Wait...");
                        hud.show();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e2) {
                        e2.printStackTrace();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                hud.dismiss();
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();

                            } catch (NullPointerException e2) {
                                e2.printStackTrace();
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                                id = 2;
                                mInterstitialAd.show();
                            }
                        }
                    }, 2000);
                }
                else {
                    startActivity(new Intent(IdealWeightActivity.this, IdealWeightChartActivity.class));
                }
            }
        });

        for (int i = 1; i <= 100; i++) {
            ageNumber.add(i);
        }
        SnapHelper snapHelper = new LinearSnapHelper();
        ageAdapter = new AgeAdapter(ageNumber, IdealWeightActivity.this,selectedAge);
        layoutManager = new LinearLayoutManager(IdealWeightActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ageRecycleView.setLayoutManager(layoutManager);
        ageRecycleView.setAdapter(ageAdapter);
        ageRecycleView.smoothScrollToPosition(selectedAge);
        ((LinearLayoutManager) ageRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedAge-3, 0);
        ageRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(ageRecycleView);

        if (heightType.equals("centimeter")) {
            heightNumber.clear();
            for (int i = 0; i <= 200; i++) {
                heightNumber.add(i);
            }
        } else {
            heightNumber.clear();
            for (int i = 0; i <= 100; i++) {
                heightNumber.add(i);
            }
        }
        heightRecycleView.setOnFlingListener(null);
        SnapHelper snapHelper2 = new LinearSnapHelper();
        HeightAdapter heightAdapter = new HeightAdapter(heightNumber, IdealWeightActivity.this,selectedHeight);
        layoutManager = new LinearLayoutManager(IdealWeightActivity.this, LinearLayoutManager.HORIZONTAL, false);
        heightRecycleView.setLayoutManager(layoutManager);
        heightRecycleView.setAdapter(heightAdapter);
        heightRecycleView.smoothScrollToPosition(selectedHeight);
        ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
        heightRecycleView.setHasFixedSize(true);
        snapHelper2.attachToRecyclerView(heightRecycleView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getResult() {
        if (selectedAge == 0) {
            selectedAge = 21;
        }
        if (selectedHeight == 0) {
            selectedHeight = 155;
        }

        weightKgMale = (float) 52.4;
        weightIbsMale = (float) 115.6;
        weightKgFemale = (float) 49.4;
        weightIbsFemale = (float) 108.9;
        int tempHeight=selectedHeight;
        if (heightType.equals("inches")) {
            selectedHeight = (int) (selectedHeight * 2.54);
        }
        int distance = selectedHeight - 153;

        if(heightType.equals("centimeter"))
        {
            if(selectedHeight>=153)
            {
                if(zender.equals("male"))
                {
                    finalResultMale(distance);
                }
                else
                {
                    finalResultFemale(distance);
                }
            }
            else
            {
                Toast.makeText(IdealWeightActivity.this, "Height not allowed less than 153 !", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            if(tempHeight>=60)
            {
                if(zender.equals("male"))
                {
                    finalResultMale(distance);
                }
                else
                {
                    finalResultFemale(distance);
                }
            }
            else
            {
                Toast.makeText(IdealWeightActivity.this, "Height not allowed less than 60 !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void interstitialAd() {
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.InterstitialAd_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onAdClosed() {
                requestInterstitial();
                switch (id) {
                    case 1:
                        getResult();
                        break;
                    case 2:
                        startActivity(new Intent(IdealWeightActivity.this, IdealWeightChartActivity.class));
                        break;
                }
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

            }
        });
    }

    private void requestInterstitial() {
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
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

    private void defaultData() {
        zender = "male";
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) female.getLayoutParams();
        marginParams.setMargins(1, 1, 1, 1);
        female.setPadding(0, 0, 40, 0);
        ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) male.getLayoutParams();
        marginParams1.setMargins(0, 0, 0, 0);
        male.setPadding(0, 0, 0, 0);
        male.setImageResource(R.drawable.male_p);
        female.setImageResource(R.drawable.female_u);

        selectedHeight=154;

        heightType = "centimeter";
        feetImg.setImageResource(R.drawable.cms_p);
        meterImg.setImageResource(R.drawable.inches_u);
        if (heightType.equals("centimeter")) {
            heightNumber.clear();
            for (int i = 0; i <= 200; i++) {
                heightNumber.add(i);
            }
        } else {
            heightNumber.clear();
            for (int i = 0; i <= 100; i++) {
                heightNumber.add(i);
            }
        }
        heightRecycleView.setOnFlingListener(null);
        SnapHelper snapHelper2 = new LinearSnapHelper();
        HeightAdapter heightAdapter = new HeightAdapter(heightNumber, IdealWeightActivity.this,selectedHeight);
        layoutManager = new LinearLayoutManager(IdealWeightActivity.this, LinearLayoutManager.HORIZONTAL, false);
        heightRecycleView.setLayoutManager(layoutManager);
        heightRecycleView.setAdapter(heightAdapter);
        heightRecycleView.smoothScrollToPosition(selectedHeight);
        ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
        heightRecycleView.setHasFixedSize(true);
        snapHelper2.attachToRecyclerView(heightRecycleView);

        selectedAge=21;
        ageRecycleView.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        ageAdapter = new AgeAdapter(ageNumber, IdealWeightActivity.this,selectedAge);
        layoutManager = new LinearLayoutManager(IdealWeightActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ageRecycleView.setLayoutManager(layoutManager);
        ageRecycleView.setAdapter(ageAdapter);
        ageRecycleView.smoothScrollToPosition(selectedAge);
        ((LinearLayoutManager) ageRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedAge-3, 0);
        ageRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(ageRecycleView);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void finalResultFemale(int distance) {
        for (int i = 0; i < distance; i++) {
            weightKgFemale = (float) (weightKgFemale + 0.7);
            weightIbsFemale = (float) (weightIbsFemale + 1.5);
        }
        DecimalFormat df = new DecimalFormat("##.##");
        weightKgFemale = Float.parseFloat(df.format(weightKgFemale));
        weightIbsFemale = Float.parseFloat(df.format(weightIbsFemale));
        Intent intent = new Intent(IdealWeightActivity.this, IdealWeightResultActivity.class);
        intent.putExtra("kg",String.valueOf(weightKgFemale));
        intent.putExtra("ibs",String.valueOf(weightIbsFemale));
        startActivity(intent);
    }

    private void getDataFromSharedPreference() {
        zender=sharedPreferences.getString("zender","");
        selectedAge=sharedPreferences.getInt("age",0);
        selectedHeight=sharedPreferences.getInt("height",0);
        heightType=sharedPreferences.getString("heightType","");

        if(zender.equals("male"))
        {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) female.getLayoutParams();
            marginParams.setMargins(1, 1, 1, 1);
            female.setPadding(0, 0, 40, 0);
            ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) male.getLayoutParams();
            marginParams1.setMargins(0, 0, 0, 0);
            male.setPadding(0, 0, 0, 0);
            male.setImageResource(R.drawable.male_p);
            female.setImageResource(R.drawable.female_u);
        }
        else
        {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) male.getLayoutParams();
            marginParams.setMargins(1, 1, 1, 1);
            male.setPadding(40, 0, 0, 0);
            ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) female.getLayoutParams();
            marginParams1.setMargins(0, 0, 0, 0);
            female.setPadding(0, 0, 0, 0);
            male.setImageResource(R.drawable.male_u);
            female.setImageResource(R.drawable.female_p);
        }

        if(heightType.equals("centimeter"))
        {
            feetImg.setImageResource(R.drawable.cms_p);
            meterImg.setImageResource(R.drawable.inches_u);
            if (heightType.equals("centimeter")) {
                heightNumber.clear();
                for (int i = 0; i <= 200; i++) {
                    heightNumber.add(i);
                }
            } else {
                heightNumber.clear();
                for (int i = 0; i <= 100; i++) {
                    heightNumber.add(i);
                }
            }
            heightRecycleView.setOnFlingListener(null);
            SnapHelper snapHelper2 = new LinearSnapHelper();
            HeightAdapter heightAdapter = new HeightAdapter(heightNumber, IdealWeightActivity.this,selectedHeight);
            layoutManager = new LinearLayoutManager(IdealWeightActivity.this, LinearLayoutManager.HORIZONTAL, false);
            heightRecycleView.setLayoutManager(layoutManager);
            heightRecycleView.setAdapter(heightAdapter);
            heightRecycleView.smoothScrollToPosition(selectedHeight);
            ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
            heightRecycleView.setHasFixedSize(true);
            snapHelper2.attachToRecyclerView(heightRecycleView);
        }
        else
        {
            feetImg.setImageResource(R.drawable.cms_u);
            meterImg.setImageResource(R.drawable.inches_p);
            if (heightType.equals("centimeter")) {
                heightNumber.clear();
                for (int i = 0; i <= 200; i++) {
                    heightNumber.add(i);
                }
            } else {
                heightNumber.clear();
                for (int i = 0; i <= 100; i++) {
                    heightNumber.add(i);
                }
            }
            heightRecycleView.setOnFlingListener(null);
            SnapHelper snapHelper2 = new LinearSnapHelper();
            HeightAdapter heightAdapter = new HeightAdapter(heightNumber, IdealWeightActivity.this,selectedHeight);
            layoutManager = new LinearLayoutManager(IdealWeightActivity.this, LinearLayoutManager.HORIZONTAL, false);
            heightRecycleView.setLayoutManager(layoutManager);
            heightRecycleView.setAdapter(heightAdapter);
            heightRecycleView.smoothScrollToPosition(selectedHeight);
            ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
            heightRecycleView.setHasFixedSize(true);
            snapHelper2.attachToRecyclerView(heightRecycleView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void finalResultMale(int distance) {
        if (zender.equals("male")) {
            for (int i = 0; i < distance; i++) {
                weightKgMale = (float) (weightKgMale + 0.76);
                weightIbsMale = (float) (weightIbsMale + 1.65);
            }
            DecimalFormat df = new DecimalFormat("##.##");
            weightKgMale = Float.parseFloat(df.format(weightKgMale));
            weightIbsMale = Float.parseFloat(df.format(weightIbsMale));
            Intent intent = new Intent(IdealWeightActivity.this, IdealWeightResultActivity.class);
            intent.putExtra("kg",String.valueOf(weightKgMale));
            intent.putExtra("ibs",String.valueOf(weightIbsMale));
            startActivity(intent);
        }
    }

    private void bindView() {
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        ageRecycleView = findViewById(R.id.ageRecycleView);
        heightRecycleView = findViewById(R.id.heightRecycleView);
        feetImg = findViewById(R.id.feetImg);
        meterImg = findViewById(R.id.meterImg);
        idealWeightChart = findViewById(R.id.idealWeightChart);
        calculate = findViewById(R.id.calculate);
        iv_back = findViewById(R.id.iv_back);
        reset=findViewById(R.id.reset);
    }
}