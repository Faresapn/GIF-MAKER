package com.gifmaker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.gifmaker.Activity.MyGifActivityss;
import com.gifmaker.ImageChooser.SelectImageActivity;
import com.gifmaker.Utility.BaseActivity;

/**
 * Created by Chirag on 05-01-2018.
 */

public class HomeActivity extends BaseActivity {


    private LinearLayout lnvCreateGIF;
    private LinearLayout lnvMyGIF;
    private LinearLayout lnvSerchGIF;
    private LinearLayout lnvSetting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initView();
        initData();
        initClickListenr();

    }

    @Override
    public void initView() {

        lnvCreateGIF = (LinearLayout) findViewById(R.id.lnvCreateGIF);
        lnvMyGIF = (LinearLayout) findViewById(R.id.lnvMyGIF);
        lnvSerchGIF = (LinearLayout) findViewById(R.id.lnvSerchGIF);
        lnvSetting = (LinearLayout) findViewById(R.id.lnvSetting);
       applicationManager.displayBanner((Activity) mContext);
    }

    @Override
    public void initData() {


        lnvCreateGIF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (getGalleryPermisipon() == true) {
                    Intent intent = new Intent(mContext, SelectImageActivity.class);
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                }


            }
        });

        lnvMyGIF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getGalleryPermisipon() == true) {
                    Intent intent = new Intent(mContext, MyGifActivityss.class);
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);

                }


            }
        });

        lnvSerchGIF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getGalleryPermisipon() == true) {
                    Intent intent = new Intent(mContext, GiphyActivity.class);
                    startActivity(intent);
                }else {
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);

                }


            }
        });

        lnvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, SettingActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void initClickListenr() {

    }



    public void getAllData(){



    }


    @Override
    protected void onResume() {
        super.onResume();

        applicationManager.showIntrestial();
    }
}
