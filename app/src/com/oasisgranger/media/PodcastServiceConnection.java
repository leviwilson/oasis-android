package com.oasisgranger.media;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class PodcastServiceConnection implements ServiceConnection {

	private PlayerBinding player;

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		player = (PlayerBinding)service;
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
	}

	public PlayerBinding getPlayer() {
		return player;
	}
	
}