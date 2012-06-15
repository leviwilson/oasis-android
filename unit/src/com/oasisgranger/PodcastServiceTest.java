package com.oasisgranger;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Spy;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;

import com.oasisgranger.media.PlayerBinding;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.test.OasisTestRunner;
import com.xtremelabs.robolectric.Robolectric;

@RunWith(OasisTestRunner.class)
public class PodcastServiceTest {
	
	private PodcastService podcastService;
	
	@Spy MediaPlayer mediaPlayer;

	private OasisGrangerApp application;

	@Before
	public void setUp() {
		application = (OasisGrangerApp) Robolectric.application;
		application.configure()
			.withBinding(MediaPlayer.class, mediaPlayer);
		podcastService = new PodcastService();
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

}
