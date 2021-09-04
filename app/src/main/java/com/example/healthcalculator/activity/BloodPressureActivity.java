package com.example.healthcalculator.activity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthcalculator.R;
import com.example.healthcalculator.adapter.AgeAdapter;
import com.example.healthcalculator.adapter.SystolicAdapter;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class BloodPressureActivity extends AppCompatActivity {
    private RecyclerView systolicRecycleView,diastolicRecycleView;
    private ImageView calculate;
    private ImageView iv_back,reset;
    private RecyclerView.LayoutManager layoutManager;
    private List<Integer> systolicNumber = new ArrayList<>();
    private List<Integer> diastolicNumber = new ArrayList<>();
    public static int selectedSystolic=121,selectedDiastolic=121;
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;
    Activity activity=BloodPressureActivity.this;

    //InterstitialAds

    private int id;
    public InterstitialAd mInterstitialAd;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        bindView();
        BannerAds();
        interstitialAd();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });

        for (int i = 1; i <= 150; i++) {
            systolicNumber.add(i);
        }
        SnapHelper snapHelper = new LinearSnapHelper();
        SystolicAdapter systolicAdapter;
        systolicAdapter = new SystolicAdapter(systolicNumber, BloodPressureActivity.this,1);
        layoutManager = new LinearLayoutManager(BloodPressureActivity.this, LinearLayoutManager.HORIZONTAL, false);
        systolicRecycleView.setLayoutManager(layoutManager);
        systolicRecycleView.setAdapter(systolicAdapter);
        systolicRecycleView.smoothScrollToPosition(121);
        ((LinearLayoutManager) systolicRecycleView.getLayoutManager()).scrollToPositionWithOffset(118, 0);
        systolicRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(systolicRecycleView);

        for (int i = 1; i <= 150; i++) {
            diastolicNumber.add(i);
        }
        systolicAdapter = new SystolicAdapter(diastolicNumber, BloodPressureActivity.this,2);
        layoutManager = new LinearLayoutManager(BloodPressureActivity.this, LinearLayoutManager.HORIZONTAL, false);
        diastolicRecycleView.setLayoutManager(layoutManager);
        diastolicRecycleView.setAdapter(systolicAdapter);
        diastolicRecycleView.smoothScrollToPosition(121);
        ((LinearLayoutManager) diastolicRecycleView.getLayoutManager()).scrollToPositionWithOffset(118, 0);
        diastolicRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(diastolicRecycleView);

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
                    getResult();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });

    }

    private void resetData() {
        systolicNumber.clear();
        for (int i = 1; i <= 150; i++) {
            systolicNumber.add(i);
        }
        systolicRecycleView.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        SystolicAdapter systolicAdapter;
        systolicAdapter = new SystolicAdapter(systolicNumber, BloodPressureActivity.this,1);
        layoutManager = new LinearLayoutManager(BloodPressureActivity.this, LinearLayoutManager.HORIZONTAL, false);
        systolicRecycleView.setLayoutManager(layoutManager);
        systolicRecycleView.setAdapter(systolicAdapter);
        systolicRecycleView.smoothScrollToPosition(selectedSystolic);
        ((LinearLayoutManager) systolicRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedSystolic-3, 0);
        systolicRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(systolicRecycleView);

        diastolicNumber.clear();
        for (int i = 1; i <= 150; i++) {
            diastolicNumber.add(i);
        }
        diastolicRecycleView.setOnFlingListener(null);
        systolicAdapter = new SystolicAdapter(diastolicNumber, BloodPressureActivity.this,2);
        layoutManager = new LinearLayoutManager(BloodPressureActivity.this, LinearLayoutManager.HORIZONTAL, false);
        diastolicRecycleView.setLayoutManager(layoutManager);
        diastolicRecycleView.setAdapter(systolicAdapter);
        diastolicRecycleView.smoothScrollToPosition(selectedDiastolic);
        ((LinearLayoutManager) diastolicRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedDiastolic-3, 0);
        diastolicRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(diastolicRecycleView);
    }

    private void getResult() {
        startActivity(new Intent(BloodPressureActivity.this,BloodPressureResultActivity.class));
    }

    private void bindView() {
        systolicRecycleView=findViewById(R.id.systolicRecycleView);
        diastolicRecycleView=findViewById(R.id.diastolicRecycleView);
        calculate=findViewById(R.id.calculate);
        iv_back=findViewById(R.id.iv_back);
        reset=findViewById(R.id.reset);
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