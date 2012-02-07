package com.oasisgranger.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.oasisgranger.OasisPodcasts;

public class OasisModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(OasisPodcasts.class);
	}
	
	@Provides
	@OasisPodcastJsonUrl
	public String getOasisPodcastJsonUrl() {
		return "http://json-podcast.oasisgranger.com";
	}
	
}
