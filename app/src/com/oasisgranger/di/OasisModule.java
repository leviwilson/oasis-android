package com.oasisgranger.di;

import android.media.MediaPlayer;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.oasisgranger.DialogFacade;
import com.oasisgranger.OasisPodcasts;
import com.oasisgranger.Requestor;
import com.oasisgranger.media.PodcastPlayer;
import com.oasisgranger.media.PodcastServiceConnection;
import com.oasisgranger.task.AsyncTaskRunner;
import com.oasisgranger.task.TaskRunner;

public class OasisModule extends AbstractModule {

	@Override
	protected void configure() {
		bindCommon();
		bindProduction();
	}

	private void bindProduction() {
		bind(DialogFacade.class).to(DialogFactory.class);
		bind(MediaPlayer.class);
		bind(PodcastPlayer.class);
		bind(PodcastServiceConnection.class);
		bind(Requestor.class).to(HttpRequestor.class);
		bind(TaskRunner.class).to(AsyncTaskRunner.class);
	}

	protected void bindCommon() {
		bind(OasisPodcasts.class);
	}
	
	@Provides
	@OasisPodcastJsonUrl
	public String getOasisPodcastJsonUrl() {
		return "http://json-podcast.oasisgranger.com";
	}
	
}
