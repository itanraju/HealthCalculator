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
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

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

import com.example.healthcalculator.MainActivity;
import com.example.healthcalculator.R;
import com.example.healthcalculator.adapter.AgeAdapter;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class WaterIntakeActivity extends AppCompatActivity {
    private RecyclerView ageRecycleView;
    private ImageView kg,pound,calculate,chart,iv_back;
    private SeekBar weightSeekbar;
    private TextView weightTxt;
    private String weightType = "kg";
    private RecyclerView.LayoutManager layoutManager;
    private List<Integer> ageNumber = new ArrayList<>();
    public static int selectedAge=0,glass=0;
    public static float ltr=0;
    private SharedPreferences sharedPreferences;
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;
    Activity activity=WaterIntakeActivity.this;

    //InterstitialAds

    private int id;
    public InterstitialAd mInterstitialAd;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake);
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
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                    float weightInPound;
                    if(weightType.equals("kg"))
                    {
                        weightInPound = weightSeekbar.getProgress() * 2.20462f;
                    }
                    else
                    {
                        weightInPound =  weightSeekbar.getProgress();
                    }

                    ltr = (weightInPound * 0.66f) / 33.840f;
                    glass = Integer.valueOf((int) (ltr * 4.18));

                    startActivity(new Intent(WaterIntakeActivity.this,WaterIntakeResultActivity.class));
                }
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
                    startActivity(new Intent(WaterIntakeActivity.this,WaterIntakeChartActivity.class));
                }
            }
        });

        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightType = "kg";
                kg.setImageResource(R.drawable.kg_p);
                pound.setImageResource(R.drawable.pound_u);
            }
        });

        pound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightType = "pound";
                kg.setImageResource(R.drawable.kg_u);
                pound.setImageResource(R.drawable.pound_p);
            }
        });

        for (int i = 1; i <= 100; i++) {
            ageNumber.add(i);
        }
        SnapHelper snapHelper = new LinearSnapHelper();
        AgeAdapter ageAdapter;
        ageAdapter = new AgeAdapter(ageNumber, WaterIntakeActivity.this,selectedAge);
        layoutManager = new LinearLayoutManager(WaterIntakeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ageRecycleView.setLayoutManager(layoutManager);
        ageRecycleView.setAdapter(ageAdapter);
        ageRecycleView.smoothScrollToPosition(selectedAge);
        ((LinearLayoutManager) ageRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedAge-3, 0);
        ageRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(ageRecycleView);

    }

    private void getDataFromSharedPreference() {
        selectedAge=sharedPreferences.getInt("age",0);
        weightType=sharedPreferences.getString("weightType","");

        if(weightType.equals("kg"))
        {
            weightType="kg";
            weightSeekbar.setProgress(sharedPreferences.getInt("weight",0));
            weightTxt.setText(String.valueOf(weightSeekbar.getProgress()));
            weightSeekbar.setMax(150);
            kg.setImageResource(R.drawable.kg_p);
            pound.setImageResource(R.drawable.pound_u);
        }
        else
        {
            weightType="pound";
            weightSeekbar.setProgress(sharedPreferences.getInt("weight",0));
            weightTxt.setText(String.valueOf(weightSeekbar.getProgress()));
            weightSeekbar.setMax(100);
            kg.setImageResource(R.drawable.kg_u);
            pound.setImageResource(R.drawable.pound_p);
        }
    }

    private void bindView() {
        ageRecycleView=findViewById(R.id.ageRecycleView);
        kg=findViewById(R.id.kg);
        pound=findViewById(R.id.pound);
        weightSeekbar=findViewById(R.id.weightSeekbar);
        weightTxt=findViewById(R.id.weightTxt);
        calculate=findViewById(R.id.calculate);
        chart=findViewById(R.id.chart);
        iv_back=findViewById(R.id.iv_back);
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
                        float weightInPound;
                        if(weightType.equals("kg"))
                        {
                            weightInPound = weightSeekbar.getProgress() * 2.20462f;
                        }
                        else
                        {
                            weightInPound =  weightSeekbar.getProgress();
                        }

                        ltr = (weightInPound * 0.66f) / 33.840f;
                        glass = Integer.valueOf((int) (ltr * 4.18));

                        startActivity(new Intent(WaterIntakeActivity.this,WaterIntakeResultActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(WaterIntakeActivity.this,WaterIntakeChartActivity.class));
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

}