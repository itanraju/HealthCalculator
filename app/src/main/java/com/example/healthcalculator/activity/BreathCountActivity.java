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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcalculator.R;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class BreathCountActivity extends AppCompatActivity {
    private TextView dateTxt;
    private DatePicker datePicker;
    private ImageView calculate,iv_back;
    private String sYear,sMonth,sDay;
    public static String finalAns;

    public BreathCountActivity() {
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private FrameLayout adContainerView;
    private AdView adView;
    private AdSize adSize;
    Activity activity=BreathCountActivity.this;

//InterstitialAds

    private int id;
    public InterstitialAd mInterstitialAd;
    private KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_count);
        bindView();
        BannerAds();
        interstitialAd();
        String sMonthOfYear = null;
        if (datePicker.getMonth() == 0) {
            sMonthOfYear = "Jan";
        }
        if (datePicker.getMonth() == 1) {
            sMonthOfYear = "Feb";
        }
        if (datePicker.getMonth() == 2) {
            sMonthOfYear = "March";
        }
        if (datePicker.getMonth() == 3) {
            sMonthOfYear = "April";
        }
        if (datePicker.getMonth() == 4) {
            sMonthOfYear = "May";
        }
        if (datePicker.getMonth() == 5) {
            sMonthOfYear = "June";
        }
        if (datePicker.getMonth() == 6) {
            sMonthOfYear = "July";
        }
        if (datePicker.getMonth() == 7) {
            sMonthOfYear = "August";
        }
        if (datePicker.getMonth() == 8) {
            sMonthOfYear = "Sep";
        }
        if (datePicker.getMonth() == 9) {
            sMonthOfYear = "Oct";
        }
        if (datePicker.getMonth() == 10) {
            sMonthOfYear = "Nov";
        }
        if (datePicker.getMonth() == 11) {
            sMonthOfYear = "Dec";
        }
        dateTxt.setText(datePicker.getDayOfMonth() + " " + sMonthOfYear + " " + datePicker.getYear());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    monthOfYear = monthOfYear + 1;
                    String sMonthOfYear = null;
                    if (monthOfYear == 1) {
                        sMonthOfYear = "Jan";
                    }
                    if (monthOfYear == 2) {
                        sMonthOfYear = "Feb";
                    }
                    if (monthOfYear == 3) {
                        sMonthOfYear = "March";
                    }
                    if (monthOfYear == 4) {
                        sMonthOfYear = "April";
                    }
                    if (monthOfYear == 5) {
                        sMonthOfYear = "May";
                    }
                    if (monthOfYear == 6) {
                        sMonthOfYear = "June";
                    }
                    if (monthOfYear == 7) {
                        sMonthOfYear = "July";
                    }
                    if (monthOfYear == 8) {
                        sMonthOfYear = "August";
                    }
                    if (monthOfYear == 9) {
                        sMonthOfYear = "Sep";
                    }
                    if (monthOfYear == 10) {
                        sMonthOfYear = "Oct";
                    }
                    if (monthOfYear == 11) {
                        sMonthOfYear = "Nov";
                    }
                    if (monthOfYear == 12) {
                        sMonthOfYear = "Dec";
                    }
                    dateTxt.setText(dayOfMonth + " - " + sMonthOfYear + " - " + year);
                    sYear = String.valueOf(year);
                    sMonth = String.valueOf(monthOfYear);
                    sDay = String.valueOf(dayOfMonth);
                }
            });
        }

        calculate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getResult() {
        String currDate = new SimpleDateFormat("yyyy,MM,dd").format(Calendar.getInstance().getTime());
        String[] cDate=currDate.split(",",3);
        int cYear=Integer.valueOf(cDate[0]);
        int cMonth=Integer.valueOf(cDate[1]);
        int cDay=Integer.valueOf(cDate[2]);

        LocalDate dob=LocalDate.of(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth());
        LocalDate currentDate=LocalDate.of(cYear,cMonth,cDay);

        long diffDays= ChronoUnit.DAYS.between(dob, currentDate);
        long breathCount=diffDays*20000;
        finalAns= String.valueOf(breathCount);
        if(finalAns.contains("-"))
        {
            Toast.makeText(this, "Please select valid date !", Toast.LENGTH_SHORT).show();
        }
        else
        {
            startActivity(new Intent(BreathCountActivity.this,BreathCountResultActivity.class));
        }
    }

    private void bindView() {
        dateTxt=findViewById(R.id.date);
        datePicker=findViewById(R.id.datePicker);
        calculate=findViewById(R.id.calculate);
        iv_back=findViewById(R.id.iv_back);
    }

    private void interstitialAd() {
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.InterstitialAd_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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