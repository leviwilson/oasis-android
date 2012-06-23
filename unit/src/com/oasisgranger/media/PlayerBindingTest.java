package com.oasisgranger.media;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.oasisgranger.PodcastService;
import com.oasisgranger.test.OasisTestRunner;

@RunWith(OasisTestRunner.class)
public class PlayerBindingTest {
	
	@Mock MediaPlayer mediaPlayer;
	@Mock PodcastService podcastService;
	
	PlayerBinding playerBinding;

	@Before
	public void setUp() {
		when(podcastService.getPlayer()).thenReturn(mediaPlayer);
		playerBinding = new PlayerBinding(podcastService);
	}
	
	@Test
	public void itAutoPlaysWhenPrepared() {
		playerBinding.onPrepared(mediaPlayer);
		verify(mediaPlayer).start();
	}
	
	@Test
	public void itIsNotPlayingIfItHasNotBeenPrepared() {
		when(mediaPlayer.isPlaying()).thenReturn(true);
		assertThat(playerBinding.isPlaying(), is(false));
	}
	
	@Test
	public void itIsNotPlayingIfPreparedButPaused() {
		playerBinding.onPrepared(mediaPlayer);
		when(mediaPlayer.isPlaying()).thenReturn(false);
		
		assertThat(playerBinding.isPlaying(), is(false));
	}
	
	@Test
	public void itIsPlayingIfPreparedAndPlaying() {
		playerBinding.onPrepared(mediaPlayer);
		when(mediaPlayer.isPlaying()).thenReturn(true);
		
		assertThat(playerBinding.isPlaying(), is(true));
	}
	
	@Test
	public void itPlays() {
		playerBinding.play();
		verify(mediaPlayer).start();
	}
	
	@Test
	public void itPauses() {
		playerBinding.pause();
		verify(mediaPlayer).pause();
	}
	
	@Test
	public void itStops() {
		playerBinding.stop();
		verify(mediaPlayer).stop();
	}
	
	@Test
	public void stopAlsoStopsTheService() {
		playerBinding.stop();
		verify(podcastService).stopSelf();
	}
	
	@Test
	public void itSignalsWhenPlaybackHasStarted() {
		final TestOnInitialPlayback listener = new TestOnInitialPlayback();
		playerBinding.setOnInitialPlaybackListener(listener);
		
		playerBinding.onPrepared(mediaPlayer);
		
		assertThat(listener.getPlayer(), sameInstance(playerBinding));
	}
	
	@Test
	public void itSignalsLateComersThatPlaybackHasAlreadyCommenced() {
		playerBinding.onPrepared(mediaPlayer);
		
		final TestOnInitialPlayback listener = new TestOnInitialPlayback();
		playerBinding.setOnInitialPlaybackListener(listener);
		
		assertThat(listener.getPlayer(), sameInstance(playerBinding));
	}
	
	@Test
	public void itStopsPlayingIfWeLoseAudioFocus() {
		playerBinding.onAudioFocusChange(AudioManager.AUDIOFOCUS_LOSS);
		verify(mediaPlayer).stop();
	}
	
	@Test
	public void itResumesPlaybackWhenGainingBackTheFocus() {
		playerBinding.onAudioFocusChange(AudioManager.AUDIOFOCUS_GAIN);
		verify(mediaPlayer).start();
		verify(mediaPlayer).setVolume(1.0f, 1.0f);
	}
	
	@Test
	public void itPausesIfWeShouldGetTheAudioBackSoon() {
		playerBinding.onAudioFocusChange(AudioManager.AUDIOFOCUS_LOSS_TRANSIENT);
		verify(mediaPlayer).pause();
	}
	
	@Test
	public void itQuietsForNotificationsAndSuch() {
		playerBinding.onAudioFocusChange(AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK);
		verify(mediaPlayer).setVolume(0.1f, 0.1f);
	}
	
	@Test
	public void itCanFormatTheTotalTime() {
		final int hourMillis = (int) TimeUnit.HOURS.toMillis(2);
		final int minuteMillis = (int) TimeUnit.MINUTES.toMillis(42);
		final int secondMillis = (int) TimeUnit.SECONDS.toMillis(7);
		when(mediaPlayer.getDuration())
			.thenReturn(hourMillis + minuteMillis + secondMillis);
		
		assertThat(playerBinding.formatTotalTime("HH:mm:ss"), is("02:42:07"));
	}
	
	private final class TestOnInitialPlayback extends OnInitialPlaybackListener {

		private PlayerBinding player;

		@Override
		public void onInitialPlayback(PlayerBinding player) {
			this.player = player;
		}
		
		public PlayerBinding getPlayer() {
			return this.player;
		}
		
	}
}
