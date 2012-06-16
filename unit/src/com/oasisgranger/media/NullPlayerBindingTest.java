package com.oasisgranger.media;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.oasisgranger.test.OasisTestRunner;

@RunWith(OasisTestRunner.class)
public class NullPlayerBindingTest {
	
	private NullPlayerBinding nullPlayer;

	@Before
	public void setUp() {
		nullPlayer = new NullPlayerBinding();
	}
	
	@Test
	public void playDoesNothing() {
		nullPlayer.play();
	}
	
	@Test
	public void pauseDoesNothing() {
		nullPlayer.pause();
	}
	
	@Test
	public void stopDoesNothing() {
		nullPlayer.stop();
	}
	
	@Test
	public void itAlwaysShowsAsNotPlaying() {
		assertThat(nullPlayer.isPlaying(), is(false));
	}
}
