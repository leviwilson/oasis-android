package com.oasisgranger.media;

import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;
import com.oasisgranger.PodcastService;
import com.oasisgranger.models.Podcast;

public class PodcastServiceConnector {
	
	private final Context context;
	private final PodcastServiceConnection serviceConnection;

	@Inject
	public PodcastServiceConnector(final Context context, final PodcastServiceConnection serviceConnection) {
		this.context = context;
		this.serviceConnection = serviceConnection;
	}

	public void connectWith(Podcast podcast) {
		final Intent service = serviceFor(podcast);
		
		context.startService(service);
		context.bindService(service, serviceConnection, 0);
	}

	public void disconnect() {
		context.unbindService(serviceConnection);
	}

	public PlayerBinding getPlayer() {
		return serviceConnection.getPlayer();
	}

	private Intent serviceFor(Podcast podcast) {
		final Intent service = new Intent(context, PodcastService.class);
		service.putExtra(Podcast.class.getName(), podcast);
		return service;
	}

}
