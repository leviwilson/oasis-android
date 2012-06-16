package com.oasisgranger.media;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class PodcastServiceConnection implements ServiceConnection {

	private PlayerBinding player = new NullPlayerBinding();
	private OnPlayerConnectedListener onPlayerConnected;

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		player = (PlayerBinding) service;
		notifyListeners();
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
	}

	public PlayerBinding getPlayer() {
		return player;
	}

	public void setOnPlayerConnected(final OnPlayerConnectedListener listener) {
		onPlayerConnected = listener;
	}

	private void notifyListeners() {
		if (null != onPlayerConnected) {
			onPlayerConnected.onConnected(player);
		}
	}

}