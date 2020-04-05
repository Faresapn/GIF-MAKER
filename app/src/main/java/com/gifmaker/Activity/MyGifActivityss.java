package com.gifmaker.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.gifmaker.Adapter.MyGifAdapter;
import com.gifmaker.ImageChooser.SelectImageActivity;
import com.gifmaker.Model.Picture;
import com.gifmaker.R;
import com.gifmaker.Utility.BaseActivity;
import com.gifmaker.Utility.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MyGifActivityss extends BaseActivity {


    RecyclerView recyclerData;

    MyGifAdapter myGifAdapter;
    LinearLayout lnvValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_gif_activity);


        initToolBar("MY GIF ");
        imgAdd.setVisibility(View.GONE);
        imgMyGIF.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        initView();
      //  applicationManager.displayBanner((Activity) mContext);
        initData();
        initClickListenr();

    }


    @Override
    public void initView() {

        lnvValidation = (LinearLayout) findViewById(R.id.lnvValidation);
        recyclerData = (RecyclerView) findViewById(R.id.recyclerData);
        recyclerData.setLayoutManager(new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {

        if (getGalleryPermisipon() == true) {
            getFile();
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        }

    }

    @Override
    public void initClickListenr() {


        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getGalleryPermisipon() == true) {
                    Intent intent = new Intent(mContext, AlbumSelectActivity.class);
                    intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 30);
                    startActivityForResult(intent, Constants.REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                }

            }
        });


        lnvValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (getGalleryPermisipon() == true) {
                    Intent intent = new Intent(mContext, SelectImageActivity.class);
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                }



            }
        });

    }


    public void initPhotos(final ArrayList<Picture> mPictures) {
        myGifAdapter = new MyGifAdapter(mContext, mPictures);
        recyclerData.setAdapter(myGifAdapter);

        if (mPictures.size() == 0){
            lnvValidation.setVisibility(View.VISIBLE);
            recyclerData.setVisibility(View.GONE);
        }else {
            lnvValidation.setVisibility(View.GONE);
            recyclerData.setVisibility(View.VISIBLE);

        }

    }

    public void getFile() {

        ArrayList<Picture> mPicture = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().toString() + File.separator + Constant.APPNAME;
        Log.e("Files", "Path: " + path);
        File directory = new File(path);
        if (directory.exists()) {
            if (directory.listFiles() != null) {
                if (directory.listFiles().length > 0) {
                    File[] files = directory.listFiles();

                    for (int i = 0; i < files.length; i++) {
                        Picture picture = new Picture();
                        picture.setPath(files[i].getAbsolutePath());
                        mPicture.add(picture);

                    }
                    Collections.reverse(mPicture);
                }

            }
        }
        initPhotos(mPicture);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getFile();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFile();
    }
}
