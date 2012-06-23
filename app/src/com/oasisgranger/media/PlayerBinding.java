package com.oasisgranger.media;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.SystemClock;

import com.oasisgranger.PodcastService;

public class PlayerBinding extends Binder implements OnPreparedListener,
		OnAudioFocusChangeListener {

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
		if (isPlaying()) {
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
	
	public long getElapsedRealTime() {
		return SystemClock.elapsedRealtime() - mediaPlayer.getCurrentPosition();
	}

	public String formatTotalTime(String formatString) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(new Date(mediaPlayer.getDuration()));
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		isPrepared = true;
		play();
		signalOnInitialPlayback();
	}

	public void setOnInitialPlaybackListener(
			final OnInitialPlaybackListener listener) {
		onInitialPlayback = listener;

		if (isPrepared) {
			signalOnInitialPlayback();
		}
	}

	private void signalOnInitialPlayback() {
		if (null != onInitialPlayback) {
			onInitialPlayback.onInitialPlayback(this);
		}
	}

	@Override
	public void onAudioFocusChange(int focusChange) {
		switch (focusChange) {
		case AudioManager.AUDIOFOCUS_GAIN:
			if (!isPlaying()) {
				mediaPlayer.start();
			}
			mediaPlayer.setVolume(1.0f, 1.0f);
			break;
		case AudioManager.AUDIOFOCUS_LOSS:
			stop();
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
			mediaPlayer.pause();
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
			mediaPlayer.setVolume(0.1f, 0.1f);
			break;
		}
	}

}
