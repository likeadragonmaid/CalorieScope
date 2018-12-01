/*******************************************************************************************************
 * org/dynamicsoft/caloriescope/dietManager/FoodAdapter.java: FoodAdapter java source for CalorieScope
 *******************************************************************************************************
 * Copyright (C) 2018 Karanvir Singh
 *
 * Modified by Sourav Kainth
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *******************************************************************************************************/

package org.dynamicsoft.caloriescope.dietManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.dynamicsoft.caloriescope.R;

import java.util.ArrayList;

class FoodAdapter extends ArrayAdapter<Food> {
    private final Context mContext;
    private final ArrayList<Food> mExampleList;

    FoodAdapter(Context context, int resource, ArrayList<Food> exampleList) {
        super(context, resource, exampleList);
        this.mContext = context;
        this.mExampleList = exampleList;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.diet_manager_food_item, null, true);
        }

        Food food = mExampleList.get(position);

        ImageView imageView = convertView.findViewById(R.id.ivImage_diet);

        String imageUrl = food.getmImage();

        if (imageUrl != null) {
            if (imageUrl.length() > 0)
                Picasso.with(mContext).load(imageUrl).fit().centerInside().into(imageView);
        }

        TextView txtTitle = convertView.findViewById(R.id.tvName_diet);
        txtTitle.setText(food.getmName());
        TextView txtDescription = convertView.findViewById(R.id.tvDescription_diet);
        txtDescription.setText(food.getMindi());
        return convertView;
    }
}