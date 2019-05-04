/***************************************************************************************************
 * org/dynamicsoft/caloriescope/news/ArticleListAdapter.java: Article java source for CalorieScope
 ***************************************************************************************************
 * Copyright (C) 2019 Shouko Komi
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
 ***************************************************************************************************/

package org.dynamicsoft.caloriescope.news;

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

public class ArticlesListAdapter extends ArrayAdapter<Article> {

    private final ArrayList<Article> articles;
    private final Context context;
    private final int resource;

    public ArticlesListAdapter(Context context, int resource, ArrayList<Article> articles) {
        super(context, resource, articles);
        this.articles = articles;
        this.context = context;
        this.resource = resource;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.calorie_scope_news_inflate, null, true);
        }

        Article article = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.ivImage);
        Picasso.with(context).load(article.getImage()).error(R.drawable.ic_default_image).into(imageView);

        TextView txtTitle = convertView.findViewById(R.id.tvName);

        txtTitle.setText(article.getTitle());

        TextView txtDescription = convertView.findViewById(R.id.tvDescription);

        if (article.getDescription().equals("null")) {      //Hack to fix null received via JSON
            txtDescription.setText("");
        } else {
            txtDescription.setText(article.getDescription());
        }

        return convertView;
    }
}