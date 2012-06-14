package com.oasisgranger;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.oasisgranger.media.PlayerBinding;
import com.oasisgranger.models.Podcast;

public class PodcastService extends Service {

	private MediaPlayer mediaPlayer;

	@Override
	public IBinder onBind(Intent podcastIntent) {
		final Podcast podcast = getPodcast(podcastIntent);
		
		mediaPlayer = oasisApplication().instanceOf(MediaPlayer.class);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		try {
			mediaPlayer.setDataSource(podcast.getMediaUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mediaPlayer.prepareAsync();
		
		return new PlayerBinding();
	}

	private Podcast getPodcast(Intent podcastIntent) {
		Podcast podcast = podcastIntent.getParcelableExtra(Podcast.class.getName());
		
		if( null == podcast ) {
			throw new IllegalArgumentException("Expected a Podcast");
		}
		
		return podcast;
	}

	private OasisGrangerApp oasisApplication() {
		return (OasisGrangerApp) getApplication();
	}

}
