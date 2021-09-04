
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
public class BodyMassIndexActivity extends AppCompatActivity {
    ImageView male,female,feetImg,meterImg,chart,calculate,iv_back,kg,pound,reset;
    TextView weight;
    RecyclerView ageRecycleView,heightRecycleView;
    List<Integer> ageNumber=new ArrayList<>();
    List<Integer> heightNumber=new ArrayList<>();
    AgeAdapter ageAdapter;
    RecyclerView.LayoutManager layoutManager;
    SeekBar seekbar;
    public static int selectedAge=0,selectedHeight=0;
    public static float bmi=0f;
    private String zender="male";
    public static String heightType = "centimeter";
    private String kgPound="kg";
    private SharedPreferences sharedPreferences;
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;

    Activity activity=BodyMassIndexActivity.this;

    //InterstitialAds

    private int id;
    public InterstitialAd mInterstitialAd;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_mass_index);
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
            seekbar.setProgress(20);
            weight.setText("20");
            selectedHeight=154;
            selectedAge=21;
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

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultData();
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
                HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BodyMassIndexActivity.this,selectedHeight);
                layoutManager = new LinearLayoutManager(BodyMassIndexActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
                HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BodyMassIndexActivity.this,selectedHeight);
                layoutManager = new LinearLayoutManager(BodyMassIndexActivity.this, LinearLayoutManager.HORIZONTAL, false);
                heightRecycleView.setLayoutManager(layoutManager);
                heightRecycleView.setAdapter(heightAdapter);
                heightRecycleView.smoothScrollToPosition(20);
                ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(30, 0);
                heightRecycleView.setHasFixedSize(true);
                snapHelper2.attachToRecyclerView(heightRecycleView);
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
                    startActivity(new Intent(BodyMassIndexActivity.this,BodyMassIndexChartActivity.class));
                }
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
                    if(kgPound.equals("kg"))
                    {
                        if (heightType.equals("inches")) {
                            selectedHeight = (int) (selectedHeight * 2.54);
                        }
                        getData(selectedHeight,seekbar.getProgress());
                    }
                    else
                    {
                        int weight= (int) (seekbar.getProgress()*0.45);
                        if (heightType.equals("inches")) {
                            selectedHeight = (int) (selectedHeight * 2.54);
                        }
                        getData(selectedHeight,weight);
                    }
                }
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

        for (int i = 1; i <= 100; i++) {
            ageNumber.add(i);
        }
        SnapHelper snapHelper = new LinearSnapHelper();
        ageAdapter = new AgeAdapter(ageNumber, BodyMassIndexActivity.this,selectedAge);
        layoutManager = new LinearLayoutManager(BodyMassIndexActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
        HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BodyMassIndexActivity.this,selectedHeight);
        layoutManager = new LinearLayoutManager(BodyMassIndexActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
                        if(kgPound.equals("kg"))
                        {
                            if (heightType.equals("inches")) {
                                selectedHeight = (int) (selectedHeight * 2.54);
                            }
                            getData(selectedHeight,seekbar.getProgress());
                        }
                        else
                        {
                            int weight= (int) (seekbar.getProgress()*0.45);
                            if (heightType.equals("inches")) {
                                selectedHeight = (int) (selectedHeight * 2.54);
                            }
                            getData(selectedHeight,weight);
                        }
                        break;
                    case 2:
                        startActivity(new Intent(BodyMassIndexActivity.this,BodyMassIndexChartActivity.class));
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
        HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BodyMassIndexActivity.this,selectedHeight);
        layoutManager = new LinearLayoutManager(BodyMassIndexActivity.this, LinearLayoutManager.HORIZONTAL, false);
        heightRecycleView.setLayoutManager(layoutManager);
        heightRecycleView.setAdapter(heightAdapter);
        heightRecycleView.smoothScrollToPosition(selectedHeight);
        ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
        heightRecycleView.setHasFixedSize(true);
        snapHelper2.attachToRecyclerView(heightRecycleView);

        selectedAge=21;
        ageRecycleView.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        ageAdapter = new AgeAdapter(ageNumber, BodyMassIndexActivity.this,selectedAge);
        layoutManager = new LinearLayoutManager(BodyMassIndexActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ageRecycleView.setLayoutManager(layoutManager);
        ageRecycleView.setAdapter(ageAdapter);
        ageRecycleView.smoothScrollToPosition(selectedAge);
        ((LinearLayoutManager) ageRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedAge-3, 0);
        ageRecycleView.setHasFixedSize(true);
        snapHelper.attachToRecyclerView(ageRecycleView);

        seekbar.setProgress(20);
        weight.setText("20");
    }

    private void getDataFromSharedPreference() {
        zender=sharedPreferences.getString("zender","");
        selectedAge=sharedPreferences.getInt("age",0);
        selectedHeight=sharedPreferences.getInt("height",0);
        heightType=sharedPreferences.getString("heightType","");
        kgPound=sharedPreferences.getString("weightType","");

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
            HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BodyMassIndexActivity.this,selectedHeight);
            layoutManager = new LinearLayoutManager(BodyMassIndexActivity.this, LinearLayoutManager.HORIZONTAL, false);
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
            HeightAdapter heightAdapter = new HeightAdapter(heightNumber, BodyMassIndexActivity.this,selectedHeight);
            layoutManager = new LinearLayoutManager(BodyMassIndexActivity.this, LinearLayoutManager.HORIZONTAL, false);
            heightRecycleView.setLayoutManager(layoutManager);
            heightRecycleView.setAdapter(heightAdapter);
            heightRecycleView.smoothScrollToPosition(selectedHeight);
            ((LinearLayoutManager) heightRecycleView.getLayoutManager()).scrollToPositionWithOffset(selectedHeight-10, 0);
            heightRecycleView.setHasFixedSize(true);
            snapHelper2.attachToRecyclerView(heightRecycleView);
        }

        if(kgPound.equals("kg"))
        {
            kgPound="kg";
            seekbar.setProgress(sharedPreferences.getInt("weight",0));
            weight.setText(String.valueOf(seekbar.getProgress()));
            seekbar.setMax(150);
            kg.setImageResource(R.drawable.kg_p);
            pound.setImageResource(R.drawable.pound_u);
        }
        else
        {
            kgPound="pound";
            seekbar.setProgress(sharedPreferences.getInt("weight",0));
            weight.setText(String.valueOf(seekbar.getProgress()));
            seekbar.setMax(100);
            kg.setImageResource(R.drawable.kg_u);
            pound.setImageResource(R.drawable.pound_p);
        }
    }

    private void getData(int selectedHeight, int progress) {

        float height=Float.valueOf(selectedHeight);
        float pro=Float.valueOf(progress);

        float lastData=(pro/height);
        lastData=lastData/height;
        lastData=lastData*10000;
        bmi=lastData;

        startActivity(new Intent(BodyMassIndexActivity.this,BodyMassIndexResultActivity.class));
    }

    private void bindView() {
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        ageRecycleView=findViewById(R.id.ageRecycleView);
        heightRecycleView=findViewById(R.id.heightRecycleView);
        feetImg=findViewById(R.id.feetImg);
        meterImg=findViewById(R.id.meterImg);
        chart=findViewById(R.id.chart);
        calculate=findViewById(R.id.calculate);
        iv_back=findViewById(R.id.iv_back);
        seekbar=findViewById(R.id.seekbar);
        weight=findViewById(R.id.weight);
        kg=findViewById(R.id.kg);
        pound=findViewById(R.id.pound);
        reset=findViewById(R.id.reset);
    }
}