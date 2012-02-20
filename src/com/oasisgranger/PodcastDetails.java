package com.oasisgranger;

import android.os.Bundle;
import android.widget.TextView;

import com.oasisgranger.helpers.ViewHelper;
import com.oasisgranger.models.Podcast;


public class PodcastDetails extends OasisFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_podcast_details);
		
		Podcast podcast = getIntent().getParcelableExtra(Podcast.class.getName());
		TextView titleView = ViewHelper.findFor(this, R.id.podcast_title);
		titleView.setText(podcast.title);
	}

}
