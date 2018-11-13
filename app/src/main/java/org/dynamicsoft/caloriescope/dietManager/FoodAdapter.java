package org.dynamicsoft.caloriescope.dietManager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.dynamicsoft.caloriescope.R;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<Food> {
    int resource;
    private Context mContext;
    private ArrayList<Food> mExampleList;

    public FoodAdapter(Context context, int resource, ArrayList<Food> exampleList) {
        super(context, resource, exampleList);
        this.mContext = context;
        this.mExampleList = exampleList;
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.diet_manager_food_item, null, true);

        }

        Food food = mExampleList.get(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage_diet);


        String imageUrl = food.getmImage();

        if (imageUrl != null) {
            if (imageUrl.length() > 0)
                Picasso.with(mContext).load(imageUrl).fit().centerInside().into(imageView);
        }


        TextView txtTitle = (TextView) convertView.findViewById(R.id.tvName_diet);

        txtTitle.setText(food.getmName());

        TextView txtDescription = (TextView) convertView.findViewById(R.id.tvDescriptionn_diet);

        txtDescription.setText(food.getMindi());


        return convertView;
    }
}