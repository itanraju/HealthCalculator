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
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.healthcalculator.R;
import com.example.healthcalculator.adapter.HeightAdapter;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;
public class BloodVolumeActivity extends AppCompatActivity {
    private ImageView kg,pound,calculate,iv_back,cms,inches,reset;
    private RecyclerView heightRecycleView;
    private SeekBar weightSeekBar;
    private String weightType="kg",heightType="centimeter";
    private TextView weighTxt;
    private List<Integer> heightNumber = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    public static int selectedHeight = 0;
    public static float finalAns=0;
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;

    Activity activity=BloodVolumeActivity.this;

//InterstitialAds

    private int id;
    public InterstitialAd mInterstitialAd;
    private KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_volume);
        BannerAds();
        interstitialAd();
        bindView();

        weightSeekBar.setProgress(20);
        weighTxt.setText("20");
        selectedHeight=154;

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightType = "centimeter";
                cms.setImageResource(R.drawable.cms_p);
                inches.setImageResource(R.drawable.inches_u);
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
                HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BloodVolumeActivity.this,selectedHeight);
                layoutManager = new LinearLayoutManager(BloodVolumeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                heightRecycleView.setLayoutManager(layoutManager);
                heightRecycleView.setAdapter(heightAdapter);
                heightRecycleView.smoothScrollToPosition(selectedHeight);
                ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
                heightRecycleView.setHasFixedSize(true);
                snapHelper2.attachToRecyclerView(heightRecycleView);
            }
        });

        inches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightType = "inches";
                cms.setImageResource(R.drawable.cms_u);
                inches.setImageResource(R.drawable.inches_p);
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
                HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BloodVolumeActivity.this,selectedHeight);
                layoutManager = new LinearLayoutManager(BloodVolumeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                heightRecycleView.setLayoutManager(layoutManager);
                heightRecycleView.setAdapter(heightAdapter);
                heightRecycleView.smoothScrollToPosition(selectedHeight);
                ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
                heightRecycleView.setHasFixedSize(true);
                snapHelper2.attachToRecyclerView(heightRecycleView);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });

        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightType="kg";
                kg.setImageResource(R.drawable.kg_p);
                pound.setImageResource(R.drawable.pound_u);
                weightSeekBar.setMax(150);
                weightSeekBar.setProgress(20);
            }
        });

        pound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightType="pound";
                pound.setImageResource(R.drawable.pound_p);
                kg.setImageResource(R.drawable.kg_u);
                weightSeekBar.setMax(80);
                weightSeekBar.setProgress(10);
            }
        });

        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weighTxt.setText(String.valueOf(progress));
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
            public void onClick(View v)
            {
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

        heightType = "centimeter";
        cms.setImageResource(R.drawable.cms_p);
        inches.setImageResource(R.drawable.inches_u);
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
        HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BloodVolumeActivity.this,selectedHeight);
        layoutManager = new LinearLayoutManager(BloodVolumeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        heightRecycleView.setLayoutManager(layoutManager);
        heightRecycleView.setAdapter(heightAdapter);
        heightRecycleView.smoothScrollToPosition(selectedHeight);
        ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
        heightRecycleView.setHasFixedSize(true);
        snapHelper2.attachToRecyclerView(heightRecycleView);
    }

    private void resetData() {
        heightType = "centimeter";
        cms.setImageResource(R.drawable.cms_p);
        inches.setImageResource(R.drawable.inches_u);
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
        HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BloodVolumeActivity.this,selectedHeight);
        layoutManager = new LinearLayoutManager(BloodVolumeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        heightRecycleView.setLayoutManager(layoutManager);
        heightRecycleView.setAdapter(heightAdapter);
        heightRecycleView.smoothScrollToPosition(selectedHeight);
        ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
        heightRecycleView.setHasFixedSize(true);
        snapHelper2.attachToRecyclerView(heightRecycleView);

        weightType="kg";
        kg.setImageResource(R.drawable.kg_p);
        pound.setImageResource(R.drawable.pound_u);
        weightSeekBar.setMax(150);
        weightSeekBar.setProgress(20);
    }

    private void getResult() {
        float heightFloat=Float.parseFloat(String.valueOf(selectedHeight))/100.0f;
        float weightFloat=Float.parseFloat(String.valueOf(weightSeekBar.getProgress()));

        if(weightType.equals("pound"))
        {
            weightFloat=weightFloat/2.205f;
        }

        float result=(0.3669f*(heightFloat*heightFloat*heightFloat))+(0.03219f*weightFloat)+0.6041f;
        finalAns=result;
        startActivity(new Intent(BloodVolumeActivity.this,BloodVolumeResultActivity.class));
    }

    private void bindView() {
        kg=findViewById(R.id.kg);
        pound=findViewById(R.id.pound);
        calculate=findViewById(R.id.calculate);
        heightRecycleView=findViewById(R.id.heightRecycleView);
        weightSeekBar=findViewById(R.id.seekbar);
        weighTxt=findViewById(R.id.weight);
        iv_back=findViewById(R.id.iv_back);
        cms=findViewById(R.id.cms);
        inches=findViewById(R.id.inches);
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