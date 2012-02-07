package com.oasisgranger;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

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
