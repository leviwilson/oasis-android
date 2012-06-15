package com.oasisgranger;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;

import com.oasisgranger.media.PlayerBinding;
import com.oasisgranger.models.Podcast;

public class PodcastService extends Service implements OnPreparedListener, OnCompletionListener {

	private MediaPlayer mediaPlayer;

	@Override
	public IBinder onBind(Intent podcastIntent) {
		final Podcast podcast = getPodcast(podcastIntent);
		
		mediaPlayer = oasisApplication().instanceOf(MediaPlayer.class);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnCompletionListener(this);
		
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

	@Override
	public void onPrepared(MediaPlayer mp) {
		mediaPlayer.start();
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		stopSelf();
	}

}
