package com.oasisgranger;

import android.os.Bundle;

import com.oasisgranger.models.Podcast;


public class PodcastDetails extends OasisActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_podcast_details);
		
		Podcast podcast = getIntent().getParcelableExtra(Podcast.class.getName());
		
		initializeChildTitle(podcast.getTitle());
		setTextFor(R.id.podcast_title, podcast.getTitle());
		setTextFor(R.id.podcast_description, podcast.getDescription());
	}

}