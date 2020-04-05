package com.gifmaker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gifmaker.Model.Picture;
import com.gifmaker.R;
import com.gifmaker.ShowGifActivity;
import com.gifmaker.Utility.BaseActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Chirag on 17-10-2017.
 */
public class MyGifAdapter extends RecyclerView.Adapter<MyGifAdapter.Holder> {


    private Context mContext;
    private ArrayList<Picture> mPictures;
    private BaseActivity baseActivity;


    public MyGifAdapter(Context mContext, ArrayList<Picture> mPictures) {
        this.mContext = mContext;
        this.baseActivity = (BaseActivity) mContext;
        this.mPictures = mPictures;
    }

    @Override
    public MyGifAdapter.Holder  onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.row_my_make_gif,parent,false));
    }

    @Override
    public void onBindViewHolder(MyGifAdapter.Holder  holder, int position) {

        final Picture picture = mPictures.get(position);
        File file = new File(picture.getPath());
      //  Glide.with(mContext).load(file).asBitmap().error(R.drawable.image_loader).placeholder(R.drawable.image_loader).override(250,250).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgGif);
        Glide.with(mContext).load(file).asBitmap().error(R.drawable.image_loader).placeholder(R.drawable.image_loader).into(holder.imgGif);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                baseActivity.prefrences.setPicture(picture);
                Intent intent = new Intent(mContext, ShowGifActivity.class);
                mContext.startActivity(intent);
            }
        });


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                file.delete();

                mPictures.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mPictures.size(), mPictures);

            }
        });
    }



    @Override
    public int getItemCount() {
        return mPictures.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

       public ImageView imgGif;
       public ImageButton imgDelete;

        public Holder(View itemView) {
            super(itemView);

            imgGif = (ImageView) itemView.findViewById(R.id.imgGif);
            imgDelete = (ImageButton) itemView.findViewById(R.id.imgDelete);
        }
    }
}
