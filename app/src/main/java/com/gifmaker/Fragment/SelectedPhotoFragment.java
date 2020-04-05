package com.gifmaker.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gifmaker.CustomText.TextMediumBold;
import com.gifmaker.Interface.OnDurationChangeListner;
import com.gifmaker.MainActivity;
import com.gifmaker.Model.Picture;
import com.gifmaker.R;
import com.gifmaker.Utility.BaseFragment;

import java.io.File;


/**
 * Created by Chirag on 23-04-2017.
 */
public class SelectedPhotoFragment extends BaseFragment {

    Picture picture;
    ImageView imgSelectedPIC;
    MainActivity mainActivity;
    int position;

    public SelectedPhotoFragment(Picture picture, int position) {
        this.picture = picture;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selected_photo_fragment, container, false);
        mainActivity = (MainActivity) getActivity();
        initViews(view);
        initData();
        initClickListner();

        return view;

    }


    @Override
    public void initViews(View view) {
        imgSelectedPIC = (ImageView) view.findViewById(R.id.imgSelectedPIC);
    }

    @Override
    public void initData() {

        File file = new File(picture.getPath());
        Glide.clear(imgSelectedPIC);
        Glide.with(mContext).load(file).asBitmap().placeholder(R.drawable.image_loader).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgSelectedPIC);


    }

    @Override
    public void initClickListner() {
    }


}
