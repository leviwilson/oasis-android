package com.oasisgranger.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.inject.Inject;
import com.oasisgranger.PodcastService;
import com.oasisgranger.models.Podcast;

public class PodcastPlayer {
	
	private final Context context;

	@Inject
	public PodcastPlayer(final Context context) {
		this.context = context;
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
	
	private ServiceConnection serviceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
		}
	};

}
