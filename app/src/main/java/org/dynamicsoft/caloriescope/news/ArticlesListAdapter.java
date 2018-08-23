package org.dynamicsoft.caloriescope.news;

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

public class ArticlesListAdapter extends ArrayAdapter<Article> {

    ArrayList<Article> articles;
    Context context;
    int resource;

    public ArticlesListAdapter(Context context, int resource, ArrayList<Article> articles) {
        super(context, resource, articles);
        this.articles = articles;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.news_inflate, null, true);

        }

        Article article = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage);
        Picasso.with(context).load(article.getImage()).error(R.drawable.ic_launcher_foreground).into(imageView);

        TextView txtTitle = (TextView) convertView.findViewById(R.id.tvName);

        txtTitle.setText(article.getTitle());

        TextView txtDescription = (TextView) convertView.findViewById(R.id.tvDescriptionn);

        if (article.getDescription().equals("null")) {      //Hack to fix null received via JSON
            txtDescription.setText("");
        } else {
            txtDescription.setText(article.getDescription());
        }

        return convertView;
    }
}