package com.oasisgranger.media;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

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
	public void itKnowsIfTheMediaIsPlayingOrNot() {
		playerBinding.isPlaying();
		verify(mediaPlayer).isPlaying();
	}
}
