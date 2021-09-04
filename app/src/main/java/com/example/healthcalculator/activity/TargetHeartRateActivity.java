package com.example.healthcalculator.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcalculator.MainActivity;
import com.example.healthcalculator.R;
import com.example.healthcalculator.adapter.AgeAdapter;
import com.example.healthcalculator.adapter.HeightAdapter;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class TargetHeartRateActivity extends AppCompatActivity {
    private ImageView iv_back,male,female,little,moderate,hard,veryHard,calculate,chart,reset;
    private RecyclerView ageRecycleView;
    private EditText heartRateEd;
    private String heartRate="little";
    private List<Integer> ageNumber=new ArrayList<>();
    public static int selectedAge=0;
    private float factor1,factor2;
    RecyclerView.LayoutManager layoutManager;
    private String zender="male";
    public static double result1,result2;
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
    Activity activity=TargetHeartRateActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_heart_rate);
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
            selectedAge=21;
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                    getResult();
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
                    startActivity(new Intent(TargetHeartRateActivity.this,TargetHeartRateChartActivity.class));
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });

        little.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartRate="little";
                little.setImageResource(R.drawable.little_p);
                moderate.setImageResource(R.drawable.moderate_u);
                hard.setImageResource(R.drawable.hard_u);
                veryHard.setImageResource(R.drawable.very_hard_u);
            }
        });

        moderate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartRate="moderate";
                little.setImageResource(R.drawable.little_u);
                moderate.setImageResource(R.drawable.moderate_p);
                hard.setImageResource(R.drawable.hard_u);
                veryHard.setImageResource(R.drawable.very_hard_u);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartRate="hard";
                little.setImageResource(R.drawable.little_u);
                moderate.setImageResource(R.drawable.moderate_u);
                hard.setImageResource(R.drawable.hard_p);
                veryHard.setImageResource(R.drawable.very_hard_u);
            }
        });

        veryHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartRate="veryHard";
                little.setImageResource(R.drawable.little_u);
                moderate.setImageResource(R.drawable.moderate_u);
                hard.setImageResource(R.drawable.hard_u);
                veryHard.setImageResource(R.drawable.very_hard_p);
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

        for (int i=1;i<=100;i++)
        {
            ageNumber.add(i);
        }
        AgeAdapter ageAdapter;
        SnapHelper snapHelper = new LinearSnapHelper();
        ageAdapter= new AgeAdapter(ageNumber,TargetHeartRateActivity.this,selectedAge);
        layoutManager=new LinearLayoutManager(TargetHeartRateActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ageRecycleView.setLayoutManager(layoutManager);
        ageRecycleView.setAdapter(ageAdapter);
        ageRecycleView.smoothScrollToPosition(selectedAge);
        ((LinearLayoutManager)ageRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedAge-3,0);
        ageRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(ageRecycleView);

    }

    private void getDataFromSharedPreference() {
        zender=sharedPreferences.getString("zender","");
        selectedAge=sharedPreferences.getInt("age",0);

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

    }

    private void resetData() {
        selectedAge=21;
        heartRate="little";
        little.setImageResource(R.drawable.little_p);
        moderate.setImageResource(R.drawable.moderate_u);
        hard.setImageResource(R.drawable.hard_u);
        veryHard.setImageResource(R.drawable.very_hard_u);

        zender = "male";
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) female.getLayoutParams();
        marginParams.setMargins(1, 1, 1, 1);
        female.setPadding(0, 0, 40, 0);
        ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) male.getLayoutParams();
        marginParams1.setMargins(0, 0, 0, 0);
        male.setPadding(0, 0, 0, 0);
        male.setImageResource(R.drawable.male_p);
        female.setImageResource(R.drawable.female_u);

        ageNumber.clear();
        for (int i=1;i<=100;i++)
        {
            ageNumber.add(i);
        }
        ageRecycleView.setOnFlingListener(null);
        AgeAdapter ageAdapter;
        SnapHelper snapHelper = new LinearSnapHelper();
        ageAdapter= new AgeAdapter(ageNumber,TargetHeartRateActivity.this,selectedAge);
        layoutManager=new LinearLayoutManager(TargetHeartRateActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ageRecycleView.setLayoutManager(layoutManager);
        ageRecycleView.setAdapter(ageAdapter);
        ageRecycleView.smoothScrollToPosition(selectedAge);
        ((LinearLayoutManager)ageRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedAge-3,0);
        ageRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(ageRecycleView);

        heartRateEd.setText("");

    }

    private void getResult() {
        if(!TextUtils.isEmpty(heartRateEd.getText().toString()))
        {
            if(heartRate.equals("little"))
            {
                factor1=0.6f;
                factor2=0.65f;
            }
            if(heartRate.equals("moderate"))
            {
                factor1=0.65f;
                factor2=0.7f;
            }
            if(heartRate.equals("hard"))
            {
                factor1=0.7f;
                factor2=0.75f;
            }
            if(heartRate.equals("veryHard"))
            {
                factor1=0.75f;
                factor2=0.8f;
            }
            int restingHeartRateInt=Integer.parseInt(heartRateEd.getText().toString());
            result1=((220-selectedAge-restingHeartRateInt)*factor1)+restingHeartRateInt;
            result2=((220-selectedAge-restingHeartRateInt)*factor2)+restingHeartRateInt;
            startActivity(new Intent(TargetHeartRateActivity.this,TargetHeartRateResultActivity.class));
        }
        else
        {
            heartRateEd.setError("Please enter heart rate here");
        }
    }

    private void bindView() {
        iv_back=findViewById(R.id.iv_back);
        ageRecycleView=findViewById(R.id.ageRecycleView);
        heartRateEd=findViewById(R.id.heartRateEd);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        little=findViewById(R.id.little);
        moderate=findViewById(R.id.moderate);
        hard=findViewById(R.id.hard);
        veryHard=findViewById(R.id.veryHard);
        calculate=findViewById(R.id.calculate);
        chart=findViewById(R.id.chart);
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
                    case 2:
                        startActivity(new Intent(TargetHeartRateActivity.this,TargetHeartRateChartActivity.class));
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