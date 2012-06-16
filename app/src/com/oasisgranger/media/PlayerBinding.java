package com.oasisgranger.media;

import com.oasisgranger.PodcastService;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;

public class PlayerBinding extends Binder implements OnPreparedListener {

	private final MediaPlayer mediaPlayer;
	private final PodcastService service;
	private boolean isPrepared;
	
	protected PlayerBinding() {
		mediaPlayer = null;
		service = null;
	}

	public PlayerBinding(final PodcastService service) {
		this.service = service;
		this.mediaPlayer = this.service.getPlayer();
	}
	
	public void toggleAudio() {
		if( isPlaying() ) {
			pause();
		} else {
			play();
		}
	}

	public void play() {
		mediaPlayer.start();
	}

	public void pause() {
		mediaPlayer.pause();
	}

	public void stop() {
		mediaPlayer.stop();
		service.stopSelf();
	}

	public boolean isPlaying() {
		return isPrepared && mediaPlayer.isPlaying();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		isPrepared = true;
		play();
	}
	
}
