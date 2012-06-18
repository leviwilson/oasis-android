package com.oasisgranger;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.google.inject.Inject;
import com.oasisgranger.R.drawable;
import com.oasisgranger.R.id;
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
		
		startForeground(id.background_podcast, buildNotificationFor(podcast));
		
		return player;
	}
	
	public MediaPlayer getPlayer() {
		return mediaPlayer;
	}

	@SuppressWarnings("deprecation")
	private Notification buildNotificationFor(final Podcast podcast) {
		final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, sendTo(PodcastPlayerActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
		
		Notification notification = new Notification();
		notification.tickerText = podcast.getTitle();
		notification.icon = drawable.ic_home;
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		notification.setLatestEventInfo(getApplicationContext(), "Oasis Granger Podcast", podcast.getTitle(), pendingIntent);
		return notification;
	}

	private Intent sendTo(Class<? extends Activity> theActivity) {
		Intent intent = new Intent(getApplicationContext(), theActivity);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		return intent;
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
