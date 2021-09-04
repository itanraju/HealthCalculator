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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.healthcalculator.adapter.HeightAdapter;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class DailyCaloriesActivity extends AppCompatActivity {
    private ImageView iv_back,male,female,kg,pound,targetWeightKg,targetWeightPound,calculate,chart,foodCalorieList,cms,inches,little,moderate,hard,veryHard,reset;
    private RecyclerView ageRecycleView,heightRecycleView;
    private ImageView gainWeight,lossWeight;
    private TextView weightTxt,targetWeightTxt,workoutTxt;
    private SeekBar weightSeekBar,targetWeightSeekBar,workoutSeekbar;
    private String zender="male",weightType="kg",targetWeightType="kg",heightType="centimeter",workoutType="little";
    private String gainOrLoss="gain";
    List<Integer> ageNumber=new ArrayList<>();
    List<Integer> heightNumber=new ArrayList<>();
    public static int selectedAge=0,selectedHeight=0;
    AgeAdapter ageAdapter;
    RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;
    Activity activity=DailyCaloriesActivity.this;

    //InterstitialAds

    private int id;
    public InterstitialAd mInterstitialAd;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calories);
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
            selectedHeight=154;
            setDefaultData();
        }

        targetWeightSeekBar.setProgress(20);
        targetWeightTxt.setText("20");
        workoutSeekbar.setProgress(1);
        workoutTxt.setText("1");

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
                HeightAdapter heightAdapter = new HeightAdapter(heightNumber, DailyCaloriesActivity.this,selectedHeight);
                layoutManager = new LinearLayoutManager(DailyCaloriesActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
                HeightAdapter heightAdapter = new HeightAdapter(heightNumber, DailyCaloriesActivity.this,selectedHeight);
                layoutManager = new LinearLayoutManager(DailyCaloriesActivity.this, LinearLayoutManager.HORIZONTAL, false);
                heightRecycleView.setLayoutManager(layoutManager);
                heightRecycleView.setAdapter(heightAdapter);
                heightRecycleView.smoothScrollToPosition(20);
                ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(30, 0);
                heightRecycleView.setHasFixedSize(true);
                snapHelper2.attachToRecyclerView(heightRecycleView);
            }
        });


        gainWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gainOrLoss="gain";
                gainWeight.setImageResource(R.drawable.gain_weight_p);
                lossWeight.setImageResource(R.drawable.loss_weight_u);
            }
        });

        lossWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gainOrLoss="loss";
                gainWeight.setImageResource(R.drawable.gain_weight_u);
                lossWeight.setImageResource(R.drawable.loss_weight_p);
            }
        });

        little.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutType="little";
                little.setImageResource(R.drawable.little_p);
                moderate.setImageResource(R.drawable.moderate_u);
                hard.setImageResource(R.drawable.hard_u);
                veryHard.setImageResource(R.drawable.very_hard_u);
            }
        });

        moderate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutType="moderate";
                little.setImageResource(R.drawable.little_u);
                moderate.setImageResource(R.drawable.moderate_p);
                hard.setImageResource(R.drawable.hard_u);
                veryHard.setImageResource(R.drawable.very_hard_u);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutType="hard";
                little.setImageResource(R.drawable.little_u);
                moderate.setImageResource(R.drawable.moderate_u);
                hard.setImageResource(R.drawable.hard_p);
                veryHard.setImageResource(R.drawable.very_hard_u);
            }
        });

        veryHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutType="veryHard";
                little.setImageResource(R.drawable.little_u);
                moderate.setImageResource(R.drawable.moderate_u);
                hard.setImageResource(R.drawable.hard_u);
                veryHard.setImageResource(R.drawable.very_hard_p);
            }
        });

        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightType = "kg";
                kg.setImageResource(R.drawable.kg_p);
                pound.setImageResource(R.drawable.pound_u);
                weightSeekBar.setMax(150);
                weightSeekBar.setProgress(20);
            }
        });

        pound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightType = "pound";
                kg.setImageResource(R.drawable.kg_u);
                pound.setImageResource(R.drawable.pound_p);
                weightSeekBar.setMax(80);
                weightSeekBar.setProgress(10);
            }
        });

        targetWeightKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetWeightType = "kg";
                targetWeightKg.setImageResource(R.drawable.kg_p);
                targetWeightPound.setImageResource(R.drawable.pound_u);
                targetWeightSeekBar.setMax(150);
                targetWeightSeekBar.setProgress(20);
            }
        });

        targetWeightPound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetWeightType = "pound";
                targetWeightKg.setImageResource(R.drawable.kg_u);
                targetWeightPound.setImageResource(R.drawable.pound_p);
                targetWeightSeekBar.setMax(80);
                targetWeightSeekBar.setProgress(10);
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


        targetWeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    targetWeightTxt.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        workoutSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                workoutTxt.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
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
                                id = 1;
                                mInterstitialAd.show();
                            }
                        }
                    }, 2000);
                }
                else {
                    startActivity(new Intent(DailyCaloriesActivity.this,DailyCalorieChartActivity.class));
                }
            }
        });

        foodCalorieList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DailyCaloriesActivity.this,FoodCaloriesListActivity.class));
            }
        });
    }

    private void resetData() {

        selectedAge=21;
        selectedHeight=154;
        ageNumber.clear();
        heightNumber.clear();
        targetWeightSeekBar.setProgress(20);
        targetWeightTxt.setText("20");
        workoutSeekbar.setProgress(1);
        workoutTxt.setText("1");

        weightSeekBar.setProgress(20);
        weightTxt.setText("20");

        gainOrLoss="gain";
        gainWeight.setImageResource(R.drawable.gain_weight_p);
        lossWeight.setImageResource(R.drawable.loss_weight_u);

        workoutType="little";
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

        for (int i = 1; i <= 100; i++) {
            ageNumber.add(i);
        }
        ageRecycleView.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        AgeAdapter ageAdapter;
        ageAdapter = new AgeAdapter(ageNumber, DailyCaloriesActivity.this,selectedAge);
        layoutManager = new LinearLayoutManager(DailyCaloriesActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ageRecycleView.setLayoutManager(layoutManager);
        ageRecycleView.setAdapter(ageAdapter);
        ageRecycleView.smoothScrollToPosition(selectedAge);
        ((LinearLayoutManager) ageRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedAge-3, 0);
        ageRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(ageRecycleView);


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
        HeightAdapter heightAdapter = new HeightAdapter(heightNumber, DailyCaloriesActivity.this,selectedHeight);
        layoutManager = new LinearLayoutManager(DailyCaloriesActivity.this, LinearLayoutManager.HORIZONTAL, false);
        heightRecycleView.setLayoutManager(layoutManager);
        heightRecycleView.setAdapter(heightAdapter);
        heightRecycleView.smoothScrollToPosition(selectedHeight);
        ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
        heightRecycleView.setHasFixedSize(true);
        snapHelper2.attachToRecyclerView(heightRecycleView);
    }

    private void setDefaultData() {

        weightSeekBar.setProgress(20);
        weightTxt.setText("20");

        for (int i = 1; i <= 100; i++) {
            ageNumber.add(i);
        }
        ageRecycleView.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        AgeAdapter ageAdapter;
        ageAdapter = new AgeAdapter(ageNumber, DailyCaloriesActivity.this,selectedAge);
        layoutManager = new LinearLayoutManager(DailyCaloriesActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ageRecycleView.setLayoutManager(layoutManager);
        ageRecycleView.setAdapter(ageAdapter);
        ageRecycleView.smoothScrollToPosition(selectedAge);
        ((LinearLayoutManager) ageRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedAge-3, 0);
        ageRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(ageRecycleView);


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
        HeightAdapter heightAdapter = new HeightAdapter(heightNumber, DailyCaloriesActivity.this,selectedHeight);
        layoutManager = new LinearLayoutManager(DailyCaloriesActivity.this, LinearLayoutManager.HORIZONTAL, false);
        heightRecycleView.setLayoutManager(layoutManager);
        heightRecycleView.setAdapter(heightAdapter);
        heightRecycleView.smoothScrollToPosition(selectedHeight);
        ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
        heightRecycleView.setHasFixedSize(true);
        snapHelper2.attachToRecyclerView(heightRecycleView);

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
            HeightAdapter heightAdapter = new HeightAdapter(heightNumber, DailyCaloriesActivity.this,selectedHeight);
            layoutManager = new LinearLayoutManager(DailyCaloriesActivity.this, LinearLayoutManager.HORIZONTAL, false);
            heightRecycleView.setLayoutManager(layoutManager);
            heightRecycleView.setAdapter(heightAdapter);
            heightRecycleView.smoothScrollToPosition(selectedHeight);
            ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
            heightRecycleView.setHasFixedSize(true);
            snapHelper2.attachToRecyclerView(heightRecycleView);
        }
        else
        {
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
            HeightAdapter heightAdapter = new HeightAdapter(heightNumber, DailyCaloriesActivity.this,selectedHeight);
            layoutManager = new LinearLayoutManager(DailyCaloriesActivity.this, LinearLayoutManager.HORIZONTAL, false);
            heightRecycleView.setLayoutManager(layoutManager);
            heightRecycleView.setAdapter(heightAdapter);
            heightRecycleView.smoothScrollToPosition(selectedHeight);
            ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
            heightRecycleView.setHasFixedSize(true);
            snapHelper2.attachToRecyclerView(heightRecycleView);
        }

        int tempWeight=sharedPreferences.getInt("weight",0);
        weightSeekBar.setMax(150);
        weightSeekBar.setProgress(sharedPreferences.getInt("weight",0));


        weightSeekBar.setProgress(sharedPreferences.getInt("weight",0));
        weightTxt.setText(String.valueOf(sharedPreferences.getInt("weight",0)));

    }

    private void getResult() {
        float fAge = Float.valueOf(selectedAge);
        float fHeight = Float.valueOf(selectedHeight);
        float fWeight = Float.valueOf(weightSeekBar.getProgress());
        float fTargetWeight = Float.valueOf(targetWeightSeekBar.getProgress());

        if(heightType.equals("inches"))
        {
            fHeight=fHeight*2.54f;
        }
        if(weightType.equals("pound"))
        {
            fWeight=fWeight/2.205f;
        }
        if(targetWeightType.equals("pound"))
        {
            fTargetWeight=fTargetWeight/2.205f;
        }

        float bmr=0;
        if (zender.equals("male")) {
            bmr = (float) (66.47 + (13.75 * fWeight) + (5.003 * fHeight) - (6.755 * fAge));
        } else {
            bmr = (float) (655.1 + (9.563 * fWeight) + (1.850 * fHeight) - (4.676 * fAge));
        }

        float amr=0;
        if (workoutType.equals("little")) {
            amr = (float) (bmr * 1.375);
        }
        if (workoutType.equals("moderate")) {
            amr = (float) (bmr * 1.55);
        }
        if (workoutType.equals("hard")) {
            amr = (float) (bmr * 1.725);
        }
        if (workoutType.equals("veryHard")) {
            amr = (float) (bmr * 1.9);
        }

        long diffWeight = (long) (fTargetWeight - fWeight);

        long extraCalories = diffWeight * 1200;

        long perDayCalories = 0;

        if (workoutSeekbar.getProgress() == 1) {
            perDayCalories = extraCalories / 7;
        }
        if (workoutSeekbar.getProgress() == 2) {
            perDayCalories = extraCalories / 14;
        }
        if (workoutSeekbar.getProgress() == 3) {
            perDayCalories = extraCalories / 21;
        }
        if (workoutSeekbar.getProgress() == 4) {
            perDayCalories = extraCalories / 28;
        }
        if (workoutSeekbar.getProgress() == 5) {
            perDayCalories = extraCalories / 35;
        }
        if (workoutSeekbar.getProgress() == 6) {
            perDayCalories = extraCalories / 42;
        }
        if (workoutSeekbar.getProgress() == 7) {
            perDayCalories = extraCalories / 49;
        }
        if (workoutSeekbar.getProgress() == 8) {
            perDayCalories = extraCalories / 56;
        }
        if (workoutSeekbar.getProgress() == 9) {
            perDayCalories = extraCalories / 63;
        }
        if (workoutSeekbar.getProgress() == 10) {
            perDayCalories = extraCalories / 70;
        }
        if (workoutSeekbar.getProgress() == 11) {
            perDayCalories = extraCalories / 77;
        }
        if (workoutSeekbar.getProgress() == 12) {
            perDayCalories = extraCalories / 84;
        }

        long dailyCalories = 0;

        if (gainOrLoss.equals("gain")) {
            dailyCalories = (long) (amr + perDayCalories);
        } else {
            dailyCalories = (long) (amr - perDayCalories);
        }

        Intent intent=new Intent(DailyCaloriesActivity.this,DailyCaloriesResultActivity.class);
        intent.putExtra("amr",String.valueOf(amr));
        intent.putExtra("dailyCalories",String.valueOf(dailyCalories));
        startActivity(intent);

    }

    private void bindView() {
        iv_back=findViewById(R.id.iv_back);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        kg=findViewById(R.id.kg);
        pound=findViewById(R.id.pound);
        targetWeightKg=findViewById(R.id.targetWeightKg);
        targetWeightPound=findViewById(R.id.targetWeightPound);
        calculate=findViewById(R.id.calculate);
        chart=findViewById(R.id.chart);
        foodCalorieList=findViewById(R.id.foodCalorieList);
        ageRecycleView=findViewById(R.id.ageRecycleView);
        heightRecycleView=findViewById(R.id.heightRecycleView);
        gainWeight=findViewById(R.id.gainWeight);
        lossWeight=findViewById(R.id.lossWeight);
        weightSeekBar=findViewById(R.id.weightSeekbar);
        targetWeightSeekBar=findViewById(R.id.targetWeightSeekbar);
        weightTxt=findViewById(R.id.weightTxt);
        targetWeightTxt=findViewById(R.id.targetWeightTxt);
        chart=findViewById(R.id.chart);
        cms=findViewById(R.id.cms);
        inches=findViewById(R.id.inches);
        little=findViewById(R.id.little);
        moderate=findViewById(R.id.moderate);
        hard=findViewById(R.id.hard);
        veryHard=findViewById(R.id.veryHard);
        workoutSeekbar=findViewById(R.id.workoutSeekbar);
        workoutTxt=findViewById(R.id.workoutTxt);
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
                        startActivity(new Intent(DailyCaloriesActivity.this,FoodCaloriesListActivity.class));
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