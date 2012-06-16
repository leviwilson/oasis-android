package com.oasisgranger;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.google.inject.Inject;
import com.oasisgranger.media.PlayerBinding;
import com.oasisgranger.media.StopServiceWhenComplete;
import com.oasisgranger.models.Podcast;

public class PodcastService extends OasisService {

	@Inject private MediaPlayer mediaPlayer;

	@Override
	public IBinder onBind(Intent podcastIntent) {
		final Podcast podcast = getPodcast(podcastIntent);

		final PlayerBinding player = initializePlayer();
		prepareAudioFor(podcast);

		return player;
	}
	
	public MediaPlayer getPlayer() {
		return mediaPlayer;
	}

	private PlayerBinding initializePlayer() {
		final PlayerBinding player = new PlayerBinding(this);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnPreparedListener(player);
		mediaPlayer.setOnCompletionListener(new StopServiceWhenComplete(this));
		return player;
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
