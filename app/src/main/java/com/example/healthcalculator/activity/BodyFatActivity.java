package com.example.healthcalculator.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcalculator.MainActivity;
import com.example.healthcalculator.R;
import com.example.healthcalculator.adapter.AgeAdapter;
import com.example.healthcalculator.adapter.HeightAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.healthcalculator.kprogresshud.KProgressHUD;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class BodyFatActivity extends AppCompatActivity {
    private ImageView iv_back,maleImg, femaleImg, feetImg, meterImg, weight_kgImg, weight_poundImg, waist_centimeterImg, waist_inchImg, neck_centimeterImg, neck_inchImg, calculate, forearm_centimeter, forearm_inches, wrist_centimeter, wrist_inches, hip_centimeter, hip_inches,chart,reset;
    private RecyclerView ageRecycleView, heightRecycleView;
    private EditText waistEd, neckEd, forearmEd, wristEd, hipEd;
    private SeekBar weightSeekbar;
    private TextView weightTxt;
    public static String zender = "male";
    private String weightType = "kg";
    private String heightType="centimeter";
    private String waistType = "centimeter";
    private String neckType="centimeter";
    private String forearmType = "centimeter";
    private String wristType = "centimeter";
    private String hipType = "centimeter";
    private RecyclerView.LayoutManager layoutManager;
    private List<Integer> ageNumber = new ArrayList<>();
    private List<Integer> heightNumber = new ArrayList<>();
    public static int selectedAge = 0, selectedHeight = 0;
    private LinearLayout forearmLayout, forearmSeekbarLayout, wristLayout, wristSeekbarLayout, hipLayout, hipSeekbarLayout;
    private double dForearm, dWrist, dHip;
    public static float finalAns=0;
    private SharedPreferences sharedPreferences;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(BodyFatActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }

    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;

    Activity activity=BodyFatActivity.this;

    //InterstitialAds

    private int id;
    public InterstitialAd mInterstitialAd;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat);
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
            weightSeekbar.setProgress(20);
            weightTxt.setText("20");
            selectedAge=21;
            selectedHeight=154;
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BodyFatActivity.this,selectedHeight);
                layoutManager = new LinearLayoutManager(BodyFatActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
                HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BodyFatActivity.this,selectedHeight);
                layoutManager = new LinearLayoutManager(BodyFatActivity.this, LinearLayoutManager.HORIZONTAL, false);
                heightRecycleView.setLayoutManager(layoutManager);
                heightRecycleView.setAdapter(heightAdapter);
                heightRecycleView.smoothScrollToPosition(20);
                ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(30, 0);
                heightRecycleView.setHasFixedSize(true);
                snapHelper2.attachToRecyclerView(heightRecycleView);
            }
        });

        weight_kgImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightType = "kg";
                weight_kgImg.setImageResource(R.drawable.kg_p);
                weight_poundImg.setImageResource(R.drawable.pound_u);
                weightSeekbar.setMax(150);
                weightSeekbar.setProgress(20);
            }
        });

        weight_poundImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightType = "pound";
                weight_poundImg.setImageResource(R.drawable.pound_p);
                weight_kgImg.setImageResource(R.drawable.kg_u);
                weightSeekbar.setMax(80);
                weightSeekbar.setProgress(10);
            }
        });

        maleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zender = "male";
                forearmLayout.setVisibility(View.GONE);
                forearmSeekbarLayout.setVisibility(View.GONE);
                wristLayout.setVisibility(View.GONE);
                wristSeekbarLayout.setVisibility(View.GONE);
                hipLayout.setVisibility(View.GONE);
                hipSeekbarLayout.setVisibility(View.GONE);
                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) femaleImg.getLayoutParams();
                marginParams.setMargins(1, 1, 1, 1);
                femaleImg.setPadding(0, 0, 40, 0);
                ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) maleImg.getLayoutParams();
                marginParams1.setMargins(0, 0, 0, 0);
                maleImg.setPadding(0, 0, 0, 0);
                maleImg.setImageResource(R.drawable.male_p);
                femaleImg.setImageResource(R.drawable.female_u);
            }
        });

        femaleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zender = "female";
                forearmLayout.setVisibility(View.VISIBLE);
                forearmSeekbarLayout.setVisibility(View.VISIBLE);
                wristLayout.setVisibility(View.VISIBLE);
                wristSeekbarLayout.setVisibility(View.VISIBLE);
                hipLayout.setVisibility(View.VISIBLE);
                hipSeekbarLayout.setVisibility(View.VISIBLE);
                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) maleImg.getLayoutParams();
                marginParams.setMargins(1, 1, 1, 1);
                maleImg.setPadding(40, 0, 0, 0);
                ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) femaleImg.getLayoutParams();
                marginParams1.setMargins(0, 0, 0, 0);
                femaleImg.setPadding(0, 0, 0, 0);
                maleImg.setImageResource(R.drawable.male_u);
                femaleImg.setImageResource(R.drawable.female_p);
            }
        });


        waist_centimeterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waistType = "centimeter";
                waist_centimeterImg.setImageResource(R.drawable.cms_p);
                waist_inchImg.setImageResource(R.drawable.inches_u);
            }
        });

        waist_inchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waistType = "inches";
                waist_centimeterImg.setImageResource(R.drawable.cms_u);
                waist_inchImg.setImageResource(R.drawable.inches_p);
            }
        });

        wrist_centimeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wristType = "centimeter";
                wrist_centimeter.setImageResource(R.drawable.cms_p);
                wrist_inches.setImageResource(R.drawable.inches_u);
            }
        });

        wrist_inches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wristType = "inches";
                wrist_centimeter.setImageResource(R.drawable.cms_u);
                wrist_inches.setImageResource(R.drawable.inches_p);
            }
        });

        neck_centimeterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neckType="centimeter";
                neck_centimeterImg.setImageResource(R.drawable.cms_p);
                neck_inchImg.setImageResource(R.drawable.inches_u);
            }
        });

        neck_inchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neckType="inches";
                neck_centimeterImg.setImageResource(R.drawable.cms_u);
                neck_inchImg.setImageResource(R.drawable.inches_p);
            }
        });

        forearm_centimeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forearmType = "centimeter";
                forearm_centimeter.setImageResource(R.drawable.cms_p);
                forearm_inches.setImageResource(R.drawable.inches_u);
            }
        });

        forearm_inches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forearmType = "inches";
                forearm_centimeter.setImageResource(R.drawable.cms_u);
                forearm_inches.setImageResource(R.drawable.inches_p);
            }
        });

        wrist_centimeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wristType = "centimeter";
                wrist_centimeter.setImageResource(R.drawable.cms_p);
                wrist_inches.setImageResource(R.drawable.inches_u);
            }
        });

        forearm_inches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forearmType = "inches";
                forearm_centimeter.setImageResource(R.drawable.cms_u);
                forearm_inches.setImageResource(R.drawable.inches_p);
            }
        });

        hip_centimeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hipType = "centimeter";
                hip_centimeter.setImageResource(R.drawable.cms_p);
                hip_inches.setImageResource(R.drawable.inches_u);
            }
        });

        hip_inches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hipType = "inches";
                hip_centimeter.setImageResource(R.drawable.cms_u);
                hip_inches.setImageResource(R.drawable.inches_p);
            }
        });

        weightSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightTxt.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(waistEd.getText().toString())) {
                    waistEd.setError("Please enter waist size here ! ");
                    waistEd.requestFocus();
                } else if (TextUtils.isEmpty(neckEd.getText().toString())) {
                    neckEd.setError("Please enter neck size here ! ");
                    neckEd.requestFocus();
                } else {
                    if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
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
                    } else {
                        getResult();
                    }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDefaultData();
            }
        });

        chart.setOnClickListener(new View.OnClickListener() {
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
                    startActivity(new Intent(BodyFatActivity.this,BodyFatChartActivity.class));
                }
            }
        });

        for (int i = 1; i <= 100; i++) {
            ageNumber.add(i);
        }
        ageRecycleView.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        AgeAdapter ageAdapter;
        ageAdapter = new AgeAdapter(ageNumber, BodyFatActivity.this,selectedAge);
        layoutManager = new LinearLayoutManager(BodyFatActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ageRecycleView.setLayoutManager(layoutManager);
        ageRecycleView.setAdapter(ageAdapter);
        ageRecycleView.smoothScrollToPosition(selectedAge);
        ((LinearLayoutManager) ageRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedAge-3, 0);
        ageRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(ageRecycleView);

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
        HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BodyFatActivity.this,selectedHeight);
        layoutManager = new LinearLayoutManager(BodyFatActivity.this, LinearLayoutManager.HORIZONTAL, false);
        heightRecycleView.setLayoutManager(layoutManager);
        heightRecycleView.setAdapter(heightAdapter);
        heightRecycleView.smoothScrollToPosition(selectedHeight);
        ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
        heightRecycleView.setHasFixedSize(true);
        snapHelper2.attachToRecyclerView(heightRecycleView);
    }

    private void interstitialAd() {
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.InterstitialAd_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestInterstitial();
                switch (id) {
                    case 1:
                        getResult();
                        break;
                    case 2:
                        startActivity(new Intent(BodyFatActivity.this,BodyFatChartActivity.class));
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


    private void getDefaultData() {
        zender = "male";
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) femaleImg.getLayoutParams();
        marginParams.setMargins(1, 1, 1, 1);
        femaleImg.setPadding(0, 0, 40, 0);
        ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) maleImg.getLayoutParams();
        marginParams1.setMargins(0, 0, 0, 0);
        maleImg.setPadding(0, 0, 0, 0);
        maleImg.setImageResource(R.drawable.male_p);
        femaleImg.setImageResource(R.drawable.female_u);

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
        HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BodyFatActivity.this,selectedHeight);
        layoutManager = new LinearLayoutManager(BodyFatActivity.this, LinearLayoutManager.HORIZONTAL, false);
        heightRecycleView.setLayoutManager(layoutManager);
        heightRecycleView.setAdapter(heightAdapter);
        heightRecycleView.smoothScrollToPosition(selectedHeight);
        ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
        heightRecycleView.setHasFixedSize(true);
        snapHelper2.attachToRecyclerView(heightRecycleView);

        selectedAge=21;
        ageRecycleView.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        AgeAdapter ageAdapter;
        ageAdapter = new AgeAdapter(ageNumber, BodyFatActivity.this,selectedAge);
        layoutManager = new LinearLayoutManager(BodyFatActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ageRecycleView.setLayoutManager(layoutManager);
        ageRecycleView.setAdapter(ageAdapter);
        ageRecycleView.smoothScrollToPosition(selectedAge);
        ((LinearLayoutManager) ageRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedAge-3, 0);
        ageRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(ageRecycleView);

        weightType="kg";
        weight_kgImg.setImageResource(R.drawable.kg_p);
        weight_poundImg.setImageResource(R.drawable.pound_u);
        weightSeekbar.setMax(150);
        weightSeekbar.setProgress(20);

        waistEd.setText("");
        neckEd.setText("");
        forearmEd.setText("");
        wristEd.setText("");
        hipEd.setText("");
    }

    private void getDataFromSharedPreference() {
        zender=sharedPreferences.getString("zender","");
        selectedAge=sharedPreferences.getInt("age",0);
        selectedHeight=sharedPreferences.getInt("height",0);
        heightType=sharedPreferences.getString("heightType","");
        weightType=sharedPreferences.getString("weightType","");

        if(zender.equals("male"))
        {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) femaleImg.getLayoutParams();
            marginParams.setMargins(1, 1, 1, 1);
            femaleImg.setPadding(0, 0, 40, 0);
            ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) maleImg.getLayoutParams();
            marginParams1.setMargins(0, 0, 0, 0);
            maleImg.setPadding(0, 0, 0, 0);
            maleImg.setImageResource(R.drawable.male_p);
            femaleImg.setImageResource(R.drawable.female_u);
        }
        else
        {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) maleImg.getLayoutParams();
            marginParams.setMargins(1, 1, 1, 1);
            maleImg.setPadding(40, 0, 0, 0);
            ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) femaleImg.getLayoutParams();
            marginParams1.setMargins(0, 0, 0, 0);
            femaleImg.setPadding(0, 0, 0, 0);
            maleImg.setImageResource(R.drawable.male_u);
            femaleImg.setImageResource(R.drawable.female_p);
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
            HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BodyFatActivity.this,selectedHeight);
            layoutManager = new LinearLayoutManager(BodyFatActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
            HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BodyFatActivity.this,selectedHeight);
            layoutManager = new LinearLayoutManager(BodyFatActivity.this, LinearLayoutManager.HORIZONTAL, false);
            heightRecycleView.setLayoutManager(layoutManager);
            heightRecycleView.setAdapter(heightAdapter);
            heightRecycleView.smoothScrollToPosition(selectedHeight);
            ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
            heightRecycleView.setHasFixedSize(true);
            snapHelper2.attachToRecyclerView(heightRecycleView);
        }

        if(weightType.equals("kg"))
        {
            weightType="kg";
            weightSeekbar.setProgress(sharedPreferences.getInt("weight",0));
            weightTxt.setText(String.valueOf(weightSeekbar.getProgress()));
            weightSeekbar.setMax(150);
            weight_kgImg.setImageResource(R.drawable.kg_p);
            weight_poundImg.setImageResource(R.drawable.pound_u);
        }
        else
        {
            weightType="pound";
            weightSeekbar.setProgress(sharedPreferences.getInt("weight",0));
            weightTxt.setText(String.valueOf(weightSeekbar.getProgress()));
            weightSeekbar.setMax(100);
            weight_kgImg.setImageResource(R.drawable.kg_u);
            weight_poundImg.setImageResource(R.drawable.pound_p);
        }
    }

    private void getResult() {

            double dHight = Double.parseDouble(String.valueOf(selectedHeight));
            double dWeight = Double.parseDouble(String.valueOf(weightSeekbar.getProgress()));
            double dWaist = Double.parseDouble(waistEd.getText().toString());
            double dNeck = Double.parseDouble(neckEd.getText().toString());

            if (zender.equals("male")) {

                dHight = dHight * 0.393701d;

                if (weightType.equals("pound")) {
                    dWeight = dWeight / 2.205d;
                } else {
                    dWeight = dWeight * 2.20462d;
                }

                if (waistType.equals("inches")) {
                    dWaist = dWaist * 2.54d;
                } else {
                    dWaist = dWaist * 0.393701d;
                }

                if(neckType.equals("inches"))
                {
                    dNeck=dNeck*2.54d;
                }
                else
                {
                    dNeck=dNeck*0.393701d;
                }

                double result1 = (dWeight * 1.082d) + 94.42d;
                double result2 = result1 - (dWaist * 4.15d);
                double bf = ((dWeight - result2) * 100.0d) / dWeight;
                DecimalFormat decimalFormat=new DecimalFormat("##.##");
                finalAns= Float.parseFloat(decimalFormat.format(bf));
                startActivity(new Intent(BodyFatActivity.this,BodyFatResultActivity.class));

            } else {

                dHight = dHight * 0.393701d;

                if (weightType.equals("pound")) {
                    dWeight = dWeight / 2.205d;
                } else {
                    dWeight = dWeight * 2.20462d;
                }

                if (waistType.equals("inches")) {
                    dWaist = dWaist * 2.54d;
                } else {
                    dWaist = dWaist * 0.393701d;
                }
                dForearm = dForearm * 0.393701d;
                if (forearmType.equals("inches")) {
                    dForearm = dForearm * 2.54d;
                }
                dWrist = dWrist * 0.393701d;
                if (wristType.equals("inches")) {
                    dWrist = dWrist * 2.54d;
                }
                dHip = dHip * 0.393701d;
                if (hipType.equals("inches")) {
                    dHip = dHip * 2.54d;
                }
                dNeck = dNeck * 0.393701d;

                if (TextUtils.isEmpty(forearmEd.getText().toString())) {
                    forearmEd.setError("Please enter forearm size here !");
                    forearmEd.requestFocus();
                } else if (TextUtils.isEmpty(wristEd.getText().toString())) {
                    wristEd.setError("Please enter wrist size here !");
                    wristEd.requestFocus();
                } else if (TextUtils.isEmpty(hipEd.getText().toString())) {
                    hipEd.setError("Please enter hip size here !");
                    hipEd.requestFocus();
                } else {
                    double result = (((((dWeight * 0.0732d) + 8.987d) + (dWrist / 3.14d)) - (dWaist * 0.157d)) - (dHip * 0.249d)) + (dForearm * 0.434d);
                    double bf = ((dWeight - result) * 100.0d) / dWeight;
                    DecimalFormat decimalFormat=new DecimalFormat("##.##");
                    finalAns= Float.parseFloat(decimalFormat.format(bf));
                    startActivity(new Intent(BodyFatActivity.this,BodyFatResultActivity.class));
                }
            }
        }
    private void bindView() {
        maleImg = findViewById(R.id.male);
        femaleImg = findViewById(R.id.female);
        feetImg = findViewById(R.id.feetImg);
        meterImg = findViewById(R.id.meterImg);
        weight_kgImg = findViewById(R.id.weightKg);
        weight_poundImg = findViewById(R.id.weightPound);
        waist_centimeterImg = findViewById(R.id.waist_centemeter);
        waist_inchImg = findViewById(R.id.waist_inch);
        neck_centimeterImg = findViewById(R.id.neck_centemeter);
        neck_inchImg = findViewById(R.id.neck_inch);
        ageRecycleView = findViewById(R.id.ageRecycleView);
        heightRecycleView = findViewById(R.id.heightRecycleView);
        weightSeekbar = findViewById(R.id.weightSeekbar);
        weightTxt = findViewById(R.id.weightTxt);
        forearmLayout = findViewById(R.id.foreArmLayout);
        forearmSeekbarLayout = findViewById(R.id.forearmSeekbarLayout);
        wristLayout = findViewById(R.id.wristLayout);
        wristSeekbarLayout = findViewById(R.id.wristSeekbarLayout);
        hipLayout = findViewById(R.id.hipLayout);
        hipSeekbarLayout = findViewById(R.id.hipSeekbarLayout);
        waistEd = findViewById(R.id.waist_ed);
        neckEd = findViewById(R.id.neck_ed);
        forearmEd = findViewById(R.id.forearm_ed);
        wristEd = findViewById(R.id.wrist_ed);
        hipEd = findViewById(R.id.hip_ed);
        calculate = findViewById(R.id.calculate);
        forearm_centimeter = findViewById(R.id.forearm_centimeter);
        forearm_inches = findViewById(R.id.forearm_inch);
        wrist_centimeter = findViewById(R.id.wrist_centimeter);
        wrist_inches = findViewById(R.id.wrist_inch);
        hip_centimeter = findViewById(R.id.hip_centimeter);
        hip_inches = findViewById(R.id.hip_inch);
        chart=findViewById(R.id.chart);
        iv_back=findViewById(R.id.iv_back);
        reset=findViewById(R.id.reset);
    }
}