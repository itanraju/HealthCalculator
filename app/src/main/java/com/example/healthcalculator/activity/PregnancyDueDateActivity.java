package com.example.healthcalculator.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import com.example.healthcalculator.R;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PregnancyDueDateActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private TextView date;
    private ImageView calculate,chart,iv_back;
    private String sYear,sMonth,sDay;
    public static String finalAns;
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;
    Activity activity=PregnancyDueDateActivity.this;

    //InterstitialAds

    private int id;
    public InterstitialAd mInterstitialAd;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_due_date);
        bindView();
        BannerAds();
        interstitialAd();

        String sMonthOfYear = null;
        if(datePicker.getMonth()==0)
        {
            sMonthOfYear="Jan";
        }
        if(datePicker.getMonth()==1)
        {
            sMonthOfYear="Feb";
        }
        if(datePicker.getMonth()==2)
        {
            sMonthOfYear="March";
        }
        if(datePicker.getMonth()==3)
        {
            sMonthOfYear="April";
        }
        if(datePicker.getMonth()==4)
        {
            sMonthOfYear="May";
        }
        if(datePicker.getMonth()==5)
        {
            sMonthOfYear="June";
        }
        if(datePicker.getMonth()==6)
        {
            sMonthOfYear="July";
        }
        if(datePicker.getMonth()==7)
        {
            sMonthOfYear="August";
        }
        if(datePicker.getMonth()==8)
        {
            sMonthOfYear="Sep";
        }
        if(datePicker.getMonth()==9)
        {
            sMonthOfYear="Oct";
        }
        if(datePicker.getMonth()==10)
        {
            sMonthOfYear="Nov";
        }
        if(datePicker.getMonth()==11)
        {
            sMonthOfYear="Dec";
        }
        date.setText(datePicker.getDayOfMonth()+" "+sMonthOfYear+" "+datePicker.getYear());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    monthOfYear=monthOfYear+1;
                    String sMonthOfYear = null;
                    if(monthOfYear==1)
                    {
                        sMonthOfYear="Jan";
                    }
                    if(monthOfYear==2)
                    {
                        sMonthOfYear="Feb";
                    }
                    if(monthOfYear==3)
                    {
                        sMonthOfYear="March";
                    }
                    if(monthOfYear==4)
                    {
                        sMonthOfYear="April";
                    }
                    if(monthOfYear==5)
                    {
                        sMonthOfYear="May";
                    }
                    if(monthOfYear==6)
                    {
                        sMonthOfYear="June";
                    }
                    if(monthOfYear==7)
                    {
                        sMonthOfYear="July";
                    }
                    if(monthOfYear==8)
                    {
                        sMonthOfYear="August";
                    }
                    if(monthOfYear==9)
                    {
                        sMonthOfYear="Sep";
                    }
                    if(monthOfYear==10)
                    {
                        sMonthOfYear="Oct";
                    }
                    if(monthOfYear==11)
                    {
                        sMonthOfYear="Nov";
                    }
                    if(monthOfYear==12)
                    {
                        sMonthOfYear="Dec";
                    }
                    date.setText(dayOfMonth+" - "+sMonthOfYear+" - "+year);
                    sYear= String.valueOf(year);
                    sMonth= String.valueOf(monthOfYear);
                    sDay= String.valueOf(dayOfMonth);
                }
            });
        }

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
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                    Calendar c=Calendar.getInstance();
                    String currentDate=sDay+"-"+sMonth+"-"+sYear;
                    try {
                        c.setTime(sdf.parse(currentDate));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    c.add(Calendar.DAY_OF_YEAR,293);
                    String sLastDate=sdf.format(c.getTime());
                    finalAns=sLastDate;
                    startActivity(new Intent(PregnancyDueDateActivity.this,PregnancyDuaDateResultActivity.class));
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
                    startActivity(new Intent(PregnancyDueDateActivity.this,PregnancyDueDateChartActivity.class));
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void bindView() {
        datePicker=findViewById(R.id.datePicker);
        date=findViewById(R.id.date);
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
                        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                        Calendar c=Calendar.getInstance();
                        String currentDate=sDay+"-"+sMonth+"-"+sYear;
                        try {
                            c.setTime(sdf.parse(currentDate));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        c.add(Calendar.DAY_OF_YEAR,293);
                        String sLastDate=sdf.format(c.getTime());
                        finalAns=sLastDate;
                        startActivity(new Intent(PregnancyDueDateActivity.this,PregnancyDuaDateResultActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(PregnancyDueDateActivity.this,PregnancyDueDateChartActivity.class));
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