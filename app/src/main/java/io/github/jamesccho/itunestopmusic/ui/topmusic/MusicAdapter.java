package io.github.jamesccho.itunestopmusic.ui.topmusic;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.github.jamesccho.itunestopmusic.BaseGlideModule;
import io.github.jamesccho.itunestopmusic.GlideApp;
import io.github.jamesccho.itunestopmusic.R;
import io.github.jamesccho.itunestopmusic.data.model.Music;

/**
 * Created by james on 25/3/2018.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private List<Music> mDataSet;
    private Context mContext;

    public MusicAdapter() {
        mDataSet = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_music, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Music m = mDataSet.get(position);
        holder.getArtist().setText(m.artist);
        holder.getTitle().setText(m.title);

        GlideApp.with(mContext).load(m.imageUrl).
                placeholder(new ColorDrawable(Color.GRAY)).into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setDataSet(List<Music> dataSet) {
        mDataSet.clear();
        mDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private final ImageView mImageView;
        private final TextView mTextViewTitle;
        private final TextView mTextViewArtist;
        public ViewHolder(View v) {
            super(v);
            mTextViewTitle = v.findViewById(R.id.text_view_title);
            mTextViewArtist = v.findViewById(R.id.text_view_artist);
            mImageView = v.findViewById(R.id.image_view);

        }

        public TextView getTitle() {
            return mTextViewTitle;
        }

        public TextView getArtist() {
            return mTextViewArtist;
        }

        public ImageView getImageView() {
            return mImageView;
        }
    }
}
