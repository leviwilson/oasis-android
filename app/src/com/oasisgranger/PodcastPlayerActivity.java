package com.oasisgranger;

import android.os.Bundle;

import com.google.inject.Inject;
import com.oasisgranger.R.layout;
import com.oasisgranger.media.PodcastServiceConnector;
import com.oasisgranger.models.Podcast;

public class PodcastPlayerActivity extends OasisActivity {
	
	@Inject private PodcastServiceConnector player;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_podcast_player);
		
		setTitle("");
		player.connectWith(getIntent().<Podcast>getParcelableExtra(Podcast.class.getName()));
	}
}
