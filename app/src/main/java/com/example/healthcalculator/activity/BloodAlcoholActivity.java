package com.example.healthcalculator.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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

public class BloodAlcoholActivity extends AppCompatActivity {
    private ImageView male,female,calculate,chart,ounce,ml,cup,hour,minute,day,kg,pound,iv_back,reset;
    private TextView drinkAlcoholLevelTxt,volumeTxt,timePassedTxt,weightTxt;
    private RecyclerView ageRecycleView;
    private SeekBar drinkAlcoholLevelSeekbar,volumeSeekBar,timePassedSeekBar,weightSeekBar;
    private String zender="male",volume="ounce",timePasses="hour",weightType="kg";
    private RecyclerView.LayoutManager layoutManager;
    private List<Integer> ageNumber = new ArrayList<>();
    private int selectedAge=0,selectedHeight=0;
    public static String finalAns;
    private SharedPreferences sharedPreferences;

    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;

    Activity activity=BloodAlcoholActivity.this;

    //InterstitialAds

    private int id;
    public InterstitialAd mInterstitialAd;
    private KProgressHUD hud;

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_alcohol);
        bindView();
        BannerAds();
        interstitialAd();
        sharedPreferences=getSharedPreferences("UserProfile",MODE_PRIVATE);
        String data=sharedPreferences.getString("zender","");
        if(data!="")
        {
            getDataFromSharedPreference();
            selectedAge=21;
        }

        drinkAlcoholLevelTxt.setText("50");
        volumeTxt.setText("10");
        timePassedTxt.setText("2");
        weightTxt.setText("20");

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

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        drinkAlcoholLevelSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drinkAlcoholLevelTxt.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeTxt.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        timePassedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timePassedTxt.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    startActivity(new Intent(BloodAlcoholActivity.this,BloodAlcoholChartActivity.class));
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
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

        ounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volume="ounce";
                volumeSeekBar.setProgress(10);
                volumeSeekBar.setMax(50);
                ounce.setImageResource(R.drawable.ounces_p);
                ml.setImageResource(R.drawable.ml_u);
                cup.setImageResource(R.drawable.cup_u);
            }
        });

        ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volume="ml";
                volumeSeekBar.setProgress(10);
                volumeSeekBar.setMax(50);
                ounce.setImageResource(R.drawable.ounces_u);
                ml.setImageResource(R.drawable.ml_p);
                cup.setImageResource(R.drawable.cup_u);
            }
        });

        cup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volume="cup";
                volumeSeekBar.setProgress(1);
                volumeSeekBar.setMax(10);
                ounce.setImageResource(R.drawable.ounces_u);
                ml.setImageResource(R.drawable.ml_u);
                cup.setImageResource(R.drawable.cup_p);
            }
        });

        hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePasses="hour";
                timePassedSeekBar.setProgress(2);
                timePassedSeekBar.setMax(24);
                hour.setImageResource(R.drawable.hour_p);
                minute.setImageResource(R.drawable.minute_u);
                day.setImageResource(R.drawable.day_u);
            }
        });

        minute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePasses="minute";
                timePassedSeekBar.setProgress(10);
                timePassedSeekBar.setMax(60);
                hour.setImageResource(R.drawable.hour_u);
                minute.setImageResource(R.drawable.minute_p);
                day.setImageResource(R.drawable.day_u);
            }
        });

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePasses="day";
                timePassedSeekBar.setProgress(1);
                timePassedSeekBar.setMax(5);
                hour.setImageResource(R.drawable.hour_u);
                minute.setImageResource(R.drawable.minute_u);
                day.setImageResource(R.drawable.day_p);
            }
        });

        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightType="kg";
                weightSeekBar.setProgress(20);
                weightSeekBar.setMax(150);
                kg.setImageResource(R.drawable.kg_p);
                pound.setImageResource(R.drawable.pound_u);
            }
        });

        pound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightType="pound";
                weightSeekBar.setProgress(10);
                weightSeekBar.setMax(100);
                kg.setImageResource(R.drawable.kg_u);
                pound.setImageResource(R.drawable.pound_p);
            }
        });

        for (int i = 1; i <= 100; i++) {
            ageNumber.add(i);
        }
        SnapHelper snapHelper = new LinearSnapHelper();
        AgeAdapter ageAdapter;
        ageAdapter = new AgeAdapter(ageNumber, BloodAlcoholActivity.this,selectedAge);
        layoutManager = new LinearLayoutManager(BloodAlcoholActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ageRecycleView.setLayoutManager(layoutManager);
        ageRecycleView.setAdapter(ageAdapter);
        ageRecycleView.smoothScrollToPosition(selectedAge);
        ((LinearLayoutManager) ageRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedAge-3, 0);
        ageRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(ageRecycleView);
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
                        startActivity(new Intent(BloodAlcoholActivity.this,BloodAlcoholChartActivity.class));
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

    private void resetData() {

        selectedAge=21;

        zender = "male";
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) female.getLayoutParams();
        marginParams.setMargins(1, 1, 1, 1);
        female.setPadding(0, 0, 40, 0);
        ViewGroup.MarginLayoutParams marginParams1 = (ViewGroup.MarginLayoutParams) male.getLayoutParams();
        marginParams1.setMargins(0, 0, 0, 0);
        male.setPadding(0, 0, 0, 0);
        male.setImageResource(R.drawable.male_p);
        female.setImageResource(R.drawable.female_u);

        volume="ounce";
        volumeSeekBar.setProgress(10);
        volumeSeekBar.setMax(50);
        ounce.setImageResource(R.drawable.ounces_p);
        ml.setImageResource(R.drawable.ml_u);
        cup.setImageResource(R.drawable.cup_u);

        timePasses="hour";
        timePassedSeekBar.setProgress(2);
        timePassedSeekBar.setMax(24);
        hour.setImageResource(R.drawable.hour_p);
        minute.setImageResource(R.drawable.minute_u);
        day.setImageResource(R.drawable.day_u);

        weightType="kg";
        weightSeekBar.setProgress(20);
        weightSeekBar.setMax(150);
        kg.setImageResource(R.drawable.kg_p);
        pound.setImageResource(R.drawable.pound_u);

        ageNumber.clear();
        for (int i = 1; i <= 100; i++) {
            ageNumber.add(i);
        }
        ageRecycleView.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        AgeAdapter ageAdapter;
        ageAdapter = new AgeAdapter(ageNumber, BloodAlcoholActivity.this,selectedAge);
        layoutManager = new LinearLayoutManager(BloodAlcoholActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ageRecycleView.setLayoutManager(layoutManager);
        ageRecycleView.setAdapter(ageAdapter);
        ageRecycleView.smoothScrollToPosition(selectedAge);
        ((LinearLayoutManager) ageRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedAge-3, 0);
        ageRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(ageRecycleView);


    }

    private void getDataFromSharedPreference() {
        zender=sharedPreferences.getString("zender","");
        selectedAge=sharedPreferences.getInt("age",0);
        selectedHeight=sharedPreferences.getInt("height",0);
        weightType=sharedPreferences.getString("weightType","");

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

        if(weightType.equals("kg"))
        {
            weightType="kg";
            weightSeekBar.setProgress(sharedPreferences.getInt("weight",0));
            weightTxt.setText(String.valueOf(weightSeekBar.getProgress()));
            weightSeekBar.setMax(150);
            kg.setImageResource(R.drawable.kg_p);
            pound.setImageResource(R.drawable.pound_u);
        }
        else
        {
            weightType="pound";
            weightSeekBar.setProgress(sharedPreferences.getInt("weight",0));
            weightTxt.setText(String.valueOf(weightSeekBar.getProgress()));
            weightSeekBar.setMax(100);
            kg.setImageResource(R.drawable.kg_u);
            pound.setImageResource(R.drawable.pound_p);
        }
    }

    private void getResult() {
        float fAlcoholLevel= Float.parseFloat(String.valueOf(drinkAlcoholLevelSeekbar.getProgress()));
        float fVolume=Float.parseFloat(String.valueOf(volumeSeekBar.getProgress()));
        float fTime=Float.parseFloat(String.valueOf(timePassedSeekBar.getProgress()));
        float fWeight=Float.parseFloat(String.valueOf(weightSeekBar.getProgress()));

        fWeight=fWeight*2.20462f;
        fWeight=5.14f/fWeight;
        fAlcoholLevel=fAlcoholLevel/100.0f;
        fVolume=fVolume*fAlcoholLevel;
        fTime=fTime*0.015f;
        float fBac=0f;

        if(volumeTxt.equals("ml"))
        {
            fVolume=fVolume*0.033814f;
        }
        if(volumeTxt.equals("cup"))
        {
            fVolume=fVolume*8.0f;
        }
        if(timePassedTxt.equals(minute))
        {
            fTime=fTime/60;
        }
        if(timePassedTxt.equals("day"))
        {
            fTime=fTime*24;
        }
        if(weightTxt.equals("pound"))
        {
            fWeight=fWeight*2.20462f;
        }

        if(zender.equals("male"))
        {
            fBac=((fVolume*fWeight)*0.73f)-fTime;
            String sBac=String.format("%.3f",fBac);
            finalAns=sBac;
            startActivity(new Intent(BloodAlcoholActivity.this,BloodAlcoholResultActivity.class));
        }
        else
        {
            fBac=((fVolume*fWeight)*0.66f)-fTime;
            String sBac=String.format("%.3f",fBac);
            finalAns=sBac;
            startActivity(new Intent(BloodAlcoholActivity.this,BloodAlcoholResultActivity.class));
        }
    }

    private void bindView() {
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        calculate=findViewById(R.id.calculate);
        chart=findViewById(R.id.chart);
        ounce=findViewById(R.id.ounce);
        ml=findViewById(R.id.ml);
        cup=findViewById(R.id.cup);
        hour=findViewById(R.id.hour);
        minute=findViewById(R.id.minute);
        day=findViewById(R.id.day);
        kg=findViewById(R.id.kg);
        pound=findViewById(R.id.pound);
        ageRecycleView=findViewById(R.id.ageRecycleView);
        drinkAlcoholLevelSeekbar=findViewById(R.id.drinkAlcoholLevelSeekbar);
        volumeSeekBar=findViewById(R.id.volumeSeekBar);
        timePassedSeekBar=findViewById(R.id.timePassedSeekBar);
        weightSeekBar=findViewById(R.id.weightSeekbar);
        drinkAlcoholLevelTxt=findViewById(R.id.drinkAlcoholLevelTxt);
        volumeTxt=findViewById(R.id.volumeTxt);
        timePassedTxt=findViewById(R.id.timePassedTxt);
        weightTxt=findViewById(R.id.weightTxt);
        iv_back=findViewById(R.id.iv_back);
        reset=findViewById(R.id.reset);
    }
}