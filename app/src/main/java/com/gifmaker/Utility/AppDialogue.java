package com.gifmaker.Utility;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gifmaker.CustomText.TextMediumBold;
import com.gifmaker.R;
import com.github.lzyzsd.circleprogress.CircleProgress;

/**
 * Created by Chirag on 17-10-2017.
 */
public class AppDialogue {

    public Context mContext;
    private Dialog progressDialogue;
    public CircleProgress circle_progress;
    public TextMediumBold txtStatus;

    public    TextMediumBold txtProgress;

    public AppDialogue(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("ResourceAsColor")
    public void showProgressDialogue() {

        ImageView imgLoader;
        if (progressDialogue != null) {
        } else {
            progressDialogue = new Dialog(mContext);
        }
        progressDialogue = new Dialog(mContext,android.R.style.DeviceDefault_Light_ButtonBar);
        progressDialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialogue.setCancelable(false);
        progressDialogue.setContentView(R.layout.dialogue_progress);
        progressDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        imgLoader = (ImageView) progressDialogue.findViewById(R.id.imgLoader);
        txtStatus = (TextMediumBold) progressDialogue.findViewById(R.id.txtStatus);
        circle_progress = (CircleProgress) progressDialogue.findViewById(R.id.circle_progress);
        txtProgress = (TextMediumBold) progressDialogue.findViewById(R.id.txtProgress);

        try {
            progressDialogue.show();
        }catch (Exception e){

        }

        Animation rotate = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
        imgLoader.startAnimation(rotate);
        rotate.reset();
        rotate.start();



    }

    public void dismisProgressDialogue() {

        if (progressDialogue != null) {
            if (progressDialogue.isShowing()) {
                progressDialogue.dismiss();
            }else {
                try {
                    progressDialogue.dismiss();
                }catch (Exception e){
                }
            }
        }
    }
}
