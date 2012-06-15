package com.oasisgranger.media;

import android.app.Service;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class StopServiceWhenComplete implements OnCompletionListener {

	private final Service service;

	public StopServiceWhenComplete(final Service service) {
		this.service = service;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		service.stopSelf();
	}

}
