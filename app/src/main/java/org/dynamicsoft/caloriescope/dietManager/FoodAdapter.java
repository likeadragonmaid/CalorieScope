package org.dynamicsoft.caloriescope.dietManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.dynamicsoft.caloriescope.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<Food> mExampleList;

    public FoodAdapter(Context context, ArrayList<Food> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.diet_manager_food_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Food currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getmImage();
        String Name = currentItem.getmName();
        String indig = currentItem.getMindi();

        holder.mTextViewCreator.setText(Name);
        holder.mTextViewLikes.setText(indig);
        if (imageUrl != null) {
            if (imageUrl.length() > 0)
                Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mTextViewLikes = itemView.findViewById(R.id.text_view_likes);
        }
    }
}