package com.oasisgranger.media;

import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;
import com.oasisgranger.PodcastService;
import com.oasisgranger.models.Podcast;

public class PodcastPlayer {
	
	private final Context context;
	private final PodcastServiceConnection serviceConnection;

	@Inject
	public PodcastPlayer(final Context context, final PodcastServiceConnection serviceConnection) {
		this.context = context;
		this.serviceConnection = serviceConnection;
	}

	public void play(Podcast podcast) {
		final Intent service = new Intent(context, PodcastService.class);
		service.putExtra(Podcast.class.getName(), podcast);
		
		context.startService(service);
		context.bindService(service, serviceConnection, 0);
	}

	public void disconnect() {
		context.unbindService(serviceConnection);
	}

}
