package com.oasisgranger.media;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;

import com.oasisgranger.PodcastService;

public class PlayerBinding extends Binder implements OnPreparedListener {

	private final MediaPlayer mediaPlayer;
	private final PodcastService service;
	private boolean isPrepared;
	private OnInitialPlaybackListener onInitialPlayback;
	
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
		signalOnInitialPlayback();
	}
	
	public void setOnInitialPlaybackListener(final OnInitialPlaybackListener listener) {
		onInitialPlayback = listener;
		
		if( isPrepared ) {
			signalOnInitialPlayback();
		}
	}

	private void signalOnInitialPlayback() {
		if( null != onInitialPlayback ) {
			onInitialPlayback.onInitialPlayback(this);
		}
	}
	
}
