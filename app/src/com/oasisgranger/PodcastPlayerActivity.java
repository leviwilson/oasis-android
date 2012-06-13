package com.oasisgranger;

import android.os.Bundle;

import com.google.inject.Inject;
import com.oasisgranger.R.layout;
import com.oasisgranger.media.PodcastPlayer;
import com.oasisgranger.models.Podcast;

public class PodcastPlayerActivity extends OasisActivity {
	
	@Inject private PodcastPlayer player;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_podcast_player);
		
		setTitle("");
		player.play(getIntent().<Podcast>getParcelableExtra(Podcast.class.getName()));
	}
}
