package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.setTextFor;
import android.os.Bundle;

import com.oasisgranger.R.id;
import com.oasisgranger.models.Podcast;


public class PodcastDetails extends OasisActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_podcast_details);
		
		Podcast podcast = getIntent().getParcelableExtra(Podcast.class.getName());
		
		setTitle(podcast.getTitle());
		setTextFor(this, id.podcast_title, podcast.getTitle());
		setTextFor(this, id.podcast_description, podcast.getDescription());
	}

}
