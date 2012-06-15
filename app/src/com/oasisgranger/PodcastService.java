package com.oasisgranger;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.google.inject.Inject;
import com.oasisgranger.media.PlayAfterPrepared;
import com.oasisgranger.media.PlayerBinding;
import com.oasisgranger.media.StopServiceWhenComplete;
import com.oasisgranger.models.Podcast;

public class PodcastService extends OasisService {

	@Inject private MediaPlayer mediaPlayer;

	@Override
	public IBinder onBind(Intent podcastIntent) {
		final Podcast podcast = getPodcast(podcastIntent);

		initializePlayer();
		prepareAudioFor(podcast);

		return new PlayerBinding(this);
	}
	
	public MediaPlayer getPlayer() {
		return mediaPlayer;
	}

	private void initializePlayer() {
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnPreparedListener(new PlayAfterPrepared());
		mediaPlayer.setOnCompletionListener(new StopServiceWhenComplete(this));
	}

	private void prepareAudioFor(final Podcast podcast) {
		try {
			mediaPlayer.setDataSource(podcast.getMediaUrl());
			mediaPlayer.prepareAsync();
		} catch (Exception e) {
			Log.v(getClass().getName(), e.getMessage());
		}
	}

	private Podcast getPodcast(Intent podcastIntent) {
		Podcast podcast = podcastIntent.getParcelableExtra(Podcast.class.getName());

		if (null == podcast) {
			throw new IllegalArgumentException(Podcast.class.getName());
		}

		return podcast;
	}

}
