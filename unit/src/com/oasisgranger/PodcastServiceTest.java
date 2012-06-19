package com.oasisgranger;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;

import android.app.Notification;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;

import com.oasisgranger.R.drawable;
import com.oasisgranger.media.AudioManagement;
import com.oasisgranger.media.PlayerBinding;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.test.OasisTestRunner;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowNotification.LatestEventInfo;

@RunWith(OasisTestRunner.class)
public class PodcastServiceTest {
	
	private PodcastService podcastService;
	
	@Spy MediaPlayer mediaPlayer;
	@Mock AudioManagement audioManagement;

	private OasisGrangerApp application;

	@Before
	public void setUp() {
		application = (OasisGrangerApp) Robolectric.application;
		application.configure()
			.withBinding(AudioManagement.class, audioManagement)
			.withBinding(MediaPlayer.class, mediaPlayer);
		podcastService = new PodcastService();
		podcastService.onCreate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void itExpectsAPodcast() {
		podcastService.onBind(new Intent());
	}
	
	@Test
	public void itDishesAPlayerOutToThoseWhoBind() {
		assertThat(podcastService.onBind(podcastIntent()), is(PlayerBinding.class));
	}
	
	@Test
	public void itTellsTheMediaPlayerWeWillStream() {
		podcastService.onBind(podcastIntent());
		
		verify(mediaPlayer).setAudioStreamType(AudioManager.STREAM_MUSIC);
	}
	
	@Test
	public void politelyRequestTheAudioFocus() {
		podcastService.onBind(podcastIntent());
		verify(audioManagement).requestAudioFocus(any(PlayerBinding.class), eq(AudioManager.STREAM_MUSIC), eq(AudioManager.AUDIOFOCUS_GAIN));
	}
	
	@Test
	public void itIndicatesWhatWeWillStream() throws Exception {
		final Podcast podcast = new Podcast();
		podcast.setMediaUrl("some.mp3");
		
		podcastService.onBind(podcastIntentWith(podcast));
		
		verify(mediaPlayer).setDataSource("some.mp3");
	}
	
	@Test
	public void itPreparesTheAudioInTheBackground() {
		podcastService.onBind(podcastIntent());
		
		verify(mediaPlayer).prepareAsync();
	}
	
	@Test
	public void oncePreparedItWillPlayThePodcast() {
		podcastService.onBind(podcastIntent());
		
		ArgumentCaptor<OnPreparedListener> listenerArg = ArgumentCaptor.forClass(OnPreparedListener.class);
		verify(mediaPlayer).setOnPreparedListener(listenerArg.capture());
		
		listenerArg.getValue().onPrepared(mediaPlayer);
		verify(mediaPlayer).start();
	}
	
	@Test
	public void itStopsItselfWhenThePodcastIsOver() {
		podcastService.onBind(podcastIntent());
		
		ArgumentCaptor<OnCompletionListener> completedArg = ArgumentCaptor.forClass(OnCompletionListener.class);
		verify(mediaPlayer).setOnCompletionListener(completedArg.capture());
		
		completedArg.getValue().onCompletion(mediaPlayer);
		assertThat(shadowOf(podcastService).isStoppedBySelf(), is(true));
	}
	
	@Test
	public void itPlaysInTheForeground() {
		podcastService.onBind(podcastIntent());
		assertThat(shadowOf(podcastService).getLastForegroundNotification(), is(notNullValue()));
	}
	
	@Test
	public void itFlashesWhatIsPlayingInitially() {
		final Podcast podcast = new Podcast("Some Title", new Date());
		podcastService.onBind(podcastIntentWith(podcast));
		
		final Notification notification = shadowOf(podcastService).getLastForegroundNotification();
		assertThat(notification.tickerText.toString(), is("Some Title"));
	}
	
	@Test
	public void itShowsTheOasisLogoForTheNotification() {
		podcastService.onBind(podcastIntent());
		
		final Notification notification = shadowOf(podcastService).getLastForegroundNotification();
		assertThat(notification.icon, is(drawable.ic_home));
	}
	
	@Test
	public void itIndicatesThatItIsAnOngoingEvent() {
		podcastService.onBind(podcastIntent());
		
		final Notification notification = shadowOf(podcastService).getLastForegroundNotification();
		assertThat(notification.flags, is(Notification.FLAG_ONGOING_EVENT));
	}
	
	@Test
	public void theNotificationShowsItIsAnOasisPodcastPlaying() {
		podcastService.onBind(podcastIntent());
		
		final LatestEventInfo eventInfo = getLastEventInfo();
		
		assertThat(eventInfo.getContentTitle().toString(), is("Oasis Granger Podcast"));
	}
	
	@Test
	public void theNotificationShowsTheCurrentPodcastTitle() {
		final Podcast podcast = new Podcast("The Podcast Title", new Date());
		podcastService.onBind(podcastIntentWith(podcast));
		
		final LatestEventInfo eventInfo = getLastEventInfo();
		
		assertThat(eventInfo.getContentText().toString(), is("The Podcast Title"));
	}
	
	@Test
	public void theNotificationSendsYouToThePodcastPlayer() {
		podcastService.onBind(podcastIntent());
		
		final Intent savedIntent = getPendingActivity();
		assertThat(savedIntent.getComponent().getClassName(), is(PodcastPlayerActivity.class.getName()));
	}
	
	@Test
	public void theNotificationWillReuseThePodcasePlayerIfItCan() {
		podcastService.onBind(podcastIntent());
		
		final Intent savedIntent = getPendingActivity();
		assertThat(savedIntent.getFlags() & Intent.FLAG_ACTIVITY_REORDER_TO_FRONT, is(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
	}
	
	@Test
	public void theNotificationCanSendYouFromOutsideOfTheApplication() {
		podcastService.onBind(podcastIntent());
		
		final Intent savedIntent = getPendingActivity();
		assertThat(savedIntent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK, is(Intent.FLAG_ACTIVITY_NEW_TASK));
	}
	
	@Test
	public void theNotificationsPendingIntentCanBeUpdated() {
		podcastService.onBind(podcastIntent());
	}
	
	private Intent podcastIntent() {
		Podcast podcast = new Podcast();
		podcast.setMediaUrl("");
		return podcastIntentWith(podcast);
	}

	private Intent podcastIntentWith(Podcast podcast) {
		Intent podcastIntent = new Intent();
		podcastIntent.putExtra(Podcast.class.getName(), podcast);
		return podcastIntent;
	}

	private LatestEventInfo getLastEventInfo() {
		final Notification notification = shadowOf(podcastService).getLastForegroundNotification();
		return shadowOf(notification).getLatestEventInfo();
	}

	private Intent getPendingActivity() {
		final LatestEventInfo eventInfo = getLastEventInfo();
		
		final Intent savedIntent = shadowOf(eventInfo.getContentIntent()).getSavedIntent();
		return savedIntent;
	}

}
