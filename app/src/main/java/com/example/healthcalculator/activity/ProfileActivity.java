package com.example.healthcalculator.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    ImageView male, female, feetImg, meterImg, save,iv_back,kg,pound,mainScreen,profile;
    private TextView weight;
    RecyclerView ageRecycleView, heightRecycleView;
    List<Integer> ageNumber = new ArrayList<>();
    List<Integer> heightNumber = new ArrayList<>();
    AgeAdapter ageAdapter;
    SeekBar seekbar;
    RecyclerView.LayoutManager layoutManager;
    public static int selectedAge = 0, selectedHeight = 0;
    private String zender = "male";
    private String kgPound="kg";
    public static String heightType = "centimeter";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //InterstitialAds

    private int id;
    public InterstitialAd mInterstitialAd;
    private KProgressHUD hud;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
        finish();
        super.onBackPressed();
    }

    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;

    Activity activity=ProfileActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bindView();
        BannerAds();
        interstitialAd();

        sharedPreferences=getSharedPreferences("UserProfile",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        String data=sharedPreferences.getString("zender","");
        if(data!="")
        {
            getDataFromSharedPreference();
        }
        else
        {
            selectedAge=21;
            weight.setText("20");
        }
        mainScreen.setImageResource(R.drawable.health_cal_u);
        profile.setImageResource(R.drawable.profile_p);
        save.setOnClickListener(new View.OnClickListener() {
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
                    saveData();
                }
            }
        });

        mainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.setImageResource(R.drawable.health_cal_p);
                profile.setImageResource(R.drawable.profile_u);
                onBackPressed();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kgPound="kg";
                kg.setImageResource(R.drawable.kg_p);
                pound.setImageResource(R.drawable.pound_u);
                seekbar.setMax(150);
                seekbar.setProgress(20);
            }
        });

        pound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kgPound="pound";
                pound.setImageResource(R.drawable.pound_p);
                kg.setImageResource(R.drawable.kg_u);
                seekbar.setMax(80);
                seekbar.setProgress(10);
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weight.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
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
                HeightAdapter heightAdapter = new HeightAdapter(heightNumber, ProfileActivity.this,selectedHeight);
                layoutManager = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
                HeightAdapter heightAdapter = new HeightAdapter(heightNumber, ProfileActivity.this,selectedHeight);
                layoutManager = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false);
                heightRecycleView.setLayoutManager(layoutManager);
                heightRecycleView.setAdapter(heightAdapter);
                heightRecycleView.smoothScrollToPosition(selectedHeight);
                ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
                heightRecycleView.setHasFixedSize(true);
                snapHelper2.attachToRecyclerView(heightRecycleView);
            }
        });

        for (int i = 1; i <= 100; i++) {
            ageNumber.add(i);
        }
        SnapHelper snapHelper = new LinearSnapHelper();
        ageAdapter = new AgeAdapter(ageNumber, ProfileActivity.this,selectedAge);
        layoutManager = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
        HeightAdapter heightAdapter = new HeightAdapter(heightNumber, ProfileActivity.this,selectedHeight);
        layoutManager = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false);
        heightRecycleView.setLayoutManager(layoutManager);
        heightRecycleView.setAdapter(heightAdapter);
        heightRecycleView.smoothScrollToPosition(selectedHeight);
        ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
        heightRecycleView.setHasFixedSize(true);
        snapHelper2.attachToRecyclerView(heightRecycleView);
    }

    private void saveData() {
        editor.putString("zender",zender);
        editor.putInt("age",selectedAge);
        editor.putInt("height",selectedHeight);
        editor.putInt("weight",seekbar.getProgress());
        editor.putString("heightType",heightType);
        editor.putString("weightType",kgPound);
        editor.apply();
        Toast.makeText(ProfileActivity.this, "Profile Saved", Toast.LENGTH_SHORT).show();
    }

    private void getDataFromSharedPreference() {
        zender=sharedPreferences.getString("zender","");
        selectedAge=sharedPreferences.getInt("age",0);
        selectedHeight=sharedPreferences.getInt("height",0);
        kgPound=sharedPreferences.getString("weightType","");
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

        if(kgPound.equals("kg"))
        {
            kg.setImageResource(R.drawable.kg_p);
            pound.setImageResource(R.drawable.pound_u);
            seekbar.setMax(150);
            seekbar.setProgress(20);
        }
        else {
            kg.setImageResource(R.drawable.kg_u);
            pound.setImageResource(R.drawable.pound_p);
            seekbar.setMax(80);
            seekbar.setProgress(10);
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
            HeightAdapter heightAdapter = new HeightAdapter(heightNumber, ProfileActivity.this,selectedHeight);
            layoutManager = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
            HeightAdapter heightAdapter = new HeightAdapter(heightNumber, ProfileActivity.this,selectedHeight);
            layoutManager = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false);
            heightRecycleView.setLayoutManager(layoutManager);
            heightRecycleView.setAdapter(heightAdapter);
            heightRecycleView.smoothScrollToPosition(selectedHeight);
            ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
            heightRecycleView.setHasFixedSize(true);
            snapHelper2.attachToRecyclerView(heightRecycleView);
        }

        seekbar.setProgress(sharedPreferences.getInt("weight",0));
        weight.setText(String.valueOf(seekbar.getProgress()));
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
                        saveData();
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
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) adContainerView.getLayoutParams();
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
        seekbar=findViewById(R.id.seekbar);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        ageRecycleView = findViewById(R.id.ageRecycleView);
        heightRecycleView = findViewById(R.id.heightRecycleView);
        feetImg = findViewById(R.id.feetImg);
        meterImg = findViewById(R.id.meterImg);
        save=findViewById(R.id.save);
        iv_back = findViewById(R.id.iv_back);
        weight=findViewById(R.id.weight);
        kg=findViewById(R.id.kg);
        pound=findViewById(R.id.pound);
        mainScreen=findViewById(R.id.mainScreen);
        profile=findViewById(R.id.profile);
    }
}