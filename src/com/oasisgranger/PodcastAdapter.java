package com.oasisgranger;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oasisgranger.helpers.ViewHelper;

public class PodcastAdapter extends ArrayAdapter<Podcast> {

	private ArrayList<Podcast> podcasts;

	public PodcastAdapter(Context context, int textViewResourceId, ArrayList<Podcast> podcasts) {
		super(context, textViewResourceId, podcasts);
		
		this.podcasts = new ArrayList<Podcast>(podcasts);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if( null == view ) {
			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.podcast_item, null);
		}
		
		Podcast podcast = podcasts.get(position);
		
		TextView titleText = ViewHelper.findFor(view, R.id.podcast_title);
		titleText.setText(podcast.title);
		
		return view;
	}

}
