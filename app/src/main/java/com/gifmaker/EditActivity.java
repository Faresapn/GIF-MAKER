package com.gifmaker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.gifmaker.Adapter.ViewPagerAdapter;
import com.gifmaker.CustomText.TextMedium;
import com.gifmaker.CustomText.TextMediumBold;
import com.gifmaker.Fragment.EditImageFragment;
import com.gifmaker.Interface.OnGifFinishListnerr;
import com.gifmaker.Model.Image;
import com.gifmaker.Utility.BaseActivity;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chirag on 05-01-2018.
 */

public class EditActivity extends BaseActivity {


    private ViewPager pager;
    private SeekBar seekBarAll;
    private TextMedium txtFaster;
    private TextMedium txtSlower;

    public TextMediumBold txtNext;

    private Intent intent;
    ArrayList<Image> mImages;
    ViewPagerAdapter viewPagerAdapter;
    ArrayList<Bitmap> mBitmaps = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        initToolBar("EDIT");
        initView();
        initData();
        initClickListenr();
    }

    @Override
    public void initView() {


        pager = (ViewPager) findViewById(R.id.pager);
        seekBarAll = (SeekBar) findViewById(R.id.seekBarAll);
        txtFaster = (TextMedium) findViewById(R.id.txtFaster);
        txtSlower = (TextMedium) findViewById(R.id.txtSlower);
        txtNext = (TextMediumBold) toolbar.findViewById(R.id.txtNext);
        txtNext.setVisibility(View.VISIBLE);

        // Disable clip to padding
        pager.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        pager.setPadding(120, 0, 120, 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        pager.setPageMargin(40);
        seekBarAll.setProgress(5000);
        txtSlower.setText(5 + ".Sec ");
        txtFaster.setText(20 + ".Sec ");
        txtNext.setText("SUBMIT");

        pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int pageWidth = pager.getMeasuredWidth() - pager.getPaddingLeft() - pager.getPaddingRight();
                int pageHeight = pager.getHeight();
                int paddingLeft = pager.getPaddingLeft();
                float transformPos = (float) (page.getLeft() - (pager.getScrollX() + paddingLeft)) / pageWidth;

                final float normalizedposition = Math.abs(Math.abs(transformPos) - 1);
                //  page.setAlpha(normalizedposition + 0.1f);

                int max = -pageHeight / 10;

                if (transformPos < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setTranslationY(0);
                } else if (transformPos <= 1) { // [-1,1]
                    page.setTranslationY(max * (1 - Math.abs(transformPos)));

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setTranslationY(0);
                }


            }
        });


        seekBarAll.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                txtSlower.setText(progress / 1000 + ".Sec");



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int i = 0;
                for (Image image : mImages) {
                    image.setDuration(seekBar.getProgress());
                    mImages.set(i, image);
                    i++;
                }
            }
        });

    }

    @Override
    public void initData() {


        mImages = new ArrayList<>();
        mImages.addAll(prefrences.getCourseList());
         viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        for (Image image : mImages) {
            viewPagerAdapter.addFragment(new EditImageFragment(image.getThumbPath()), "");
        }

        pager.setAdapter(viewPagerAdapter);
        pager.setOffscreenPageLimit(mImages.size());
    }

    @Override
    public void initClickListenr() {

        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Observable.just(getBitmap()).subscribe(new Observer<ArrayList<Bitmap>>() {
                    @Override
                    public void onSubscribe(Disposable d) {




                    }

                    @Override
                    public void onNext(ArrayList<Bitmap> value) {






                        CreateGIF createGIF = new CreateGIF((BaseActivity) mContext,value, mImages, pager, new OnGifFinishListnerr() {
                            @Override
                            public void onGifFinish() {

                                Intent intent = new Intent(mContext, ShowGifActivity.class);
                                finish();
                                startActivity(intent);
                            }
                        });
                        createGIF.startConverting();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

              applicationManager.showIntrestial();

            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {


                 applicationManager.showIntrestialWithWait();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public ArrayList<Bitmap> getBitmap(){


        ArrayList<Bitmap> mBitmaps = new ArrayList<>();

        for (int i=0;i<viewPagerAdapter.getCount();i++){

            EditImageFragment editImageFragment = (EditImageFragment) viewPagerAdapter.getItem(i);
            Bitmap bitmap = getBitmapFromView(editImageFragment.lnvFrame);
            mBitmaps.add(bitmap);

        }
        return mBitmaps;
    }


    private Bitmap getBitmapFromView(LinearLayout view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }


    @Override
    protected void onResume() {
        super.onResume();

        applicationManager.showIntrestial();
    }


}
