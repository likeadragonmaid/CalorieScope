package org.dynamicsoft.caloriescope.videos;

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

public class VideosListAdapter extends ArrayAdapter<Video> {

    ArrayList<Video> videos;
    Context context;
    int resource;

    public VideosListAdapter(Context context, int resource, ArrayList<Video> videos) {
        super(context, resource, videos);
        this.videos = videos;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.calorie_scope_videos_inflate, null, true);
        }

        Video video = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage);
        Picasso.with(context).load(video.getImage()).error(R.drawable.ic_default_image).into(imageView);

        TextView txtTitle = (TextView) convertView.findViewById(R.id.tvName);

        txtTitle.setText(video.getTitle());

        TextView txtDescription = (TextView) convertView.findViewById(R.id.tvDescriptionn);

        if (video.getDescription().equals("null")) {      //Hack to eliminate null received via JSON
            txtDescription.setText("");
        } else {
            txtDescription.setText(video.getDescription());
        }

        return convertView;
    }
}