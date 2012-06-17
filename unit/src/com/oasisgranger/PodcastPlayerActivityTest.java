package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.clickOn;
import static com.oasisgranger.helpers.ViewHelper.isEnabled;
import static com.oasisgranger.helpers.ViewHelper.textOf;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import android.content.Intent;

import com.oasisgranger.R.id;
import com.oasisgranger.media.OnInitialPlaybackListener;
import com.oasisgranger.media.OnPlayerConnectedListener;
import com.oasisgranger.media.PlayerBinding;
import com.oasisgranger.media.PodcastServiceConnector;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.test.OasisTestRunner;
import com.xtremelabs.robolectric.Robolectric;

@RunWith(OasisTestRunner.class)
public class PodcastPlayerActivityTest {
	
	@Mock PodcastServiceConnector serviceConnector;
	@Mock PlayerBinding player;
	
	private PodcastPlayerActivity activity;
	private Podcast podcast;
	
	@Before
	public void setUp() {
		podcast = new Podcast();
		setupMocks();
	}
	
	@Test
	public void itClearsTheTitle() {
		startActivity();
		assertThat(activity.getTitle().toString(), is(""));
	}
	
	@Test
	public void controlsAreDisabledByDefault() {
		startActivity();
		assertThat(isEnabled(activity, id.play_or_pause), is(false));
		assertThat(isEnabled(activity, id.stop), is(false));
	}
	
	@Test
	public void itCaresAboutPlaybackAfterConnecting() {
		startActivity();
		verify(player).setOnInitialPlaybackListener((OnInitialPlaybackListener)anyObject());
	}
	
	@Test
	public void controlsAreEnabledAfterPlaybackStarts() {
		startActivity();
		playbackHasStarted();
		
		assertThat(isEnabled(activity, id.play_or_pause), is(true));
		assertThat(isEnabled(activity, id.stop), is(true));
	}
	
	@Test
	public void itInitiallyPlaysThePodast() {
		startActivity();
		verify(serviceConnector).connectWith(podcast);
	}
	
	@Test
	public void itCanStopThePodcast() {
		startActivity();
		clickOn(activity, id.stop);
		verify(player).stop();
	}
	
	@Test
	public void itDisconnectsWhenItDoesNotCareAnymore() {
		startActivity();
		
		activity.onStop();
		verify(serviceConnector).disconnect();
	}
	
	@Test
	public void pauseIsDisplayedIfPlaying() {
		appearAsPlaying();
		startActivity();
		playbackHasStarted();
		
		assertThat(textOf(activity, id.play_or_pause), is("Pause"));
	}
	
	@Test
	public void playIsDisplayedIfNotPlaying() {
		appearAsPaused();
		startActivity();
		playbackHasStarted();
		
		assertThat(textOf(activity, id.play_or_pause), is("Play"));
	}
	
	@Test
	public void weCanToggleTheAudioState() {
		appearAsPlaying();
		startActivity();
		
		clickOn(activity, id.play_or_pause);
		
		verify(player).toggleAudio();
	}
	
	@Test
	public void audioStateIsCorrectWhenToggling() {
		startActivity();
		
		appearAsPlaying();
		clickOn(activity, id.play_or_pause);
		assertThat(textOf(activity, id.play_or_pause), is("Pause"));
		
		appearAsPaused();
		clickOn(activity, id.play_or_pause);
		assertThat(textOf(activity, id.play_or_pause), is("Play"));
	}

	private void startActivity() {
		activity = new PodcastPlayerActivity();
		
		final Intent intent = new Intent();
		intent.putExtra(Podcast.class.getName(), podcast);
		activity.setIntent(intent);
		
		shadowOf(activity).create();
		beConnected();
	}

	private void setupMocks() {
		OasisGrangerApp application = (OasisGrangerApp) Robolectric.application; 
		application.configure()
			.withBinding(PlayerBinding.class, player)
			.withBinding(PodcastServiceConnector.class, serviceConnector);
		
		when(serviceConnector.getPlayer()).thenReturn(player);
	}

	private void appearAsPlaying() {
		when(player.isPlaying()).thenReturn(true);
	}

	private void appearAsPaused() {
		when(player.isPlaying()).thenReturn(false);
	}

	private void beConnected() {
		ArgumentCaptor<OnPlayerConnectedListener> listenerArg = ArgumentCaptor.forClass(OnPlayerConnectedListener.class);
		verify(serviceConnector).setOnPlayerConnected(listenerArg.capture());
		listenerArg.getValue().onConnected(player);
	}

	private void playbackHasStarted() {
		ArgumentCaptor<OnInitialPlaybackListener> listenerArg = ArgumentCaptor.forClass(OnInitialPlaybackListener.class);
		verify(player).setOnInitialPlaybackListener(listenerArg.capture());
		
		listenerArg.getValue().onInitialPlayback(player);
	}

}
