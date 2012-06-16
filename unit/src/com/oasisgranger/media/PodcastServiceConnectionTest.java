package com.oasisgranger.media;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.oasisgranger.test.OasisTestRunner;

@RunWith(OasisTestRunner.class)
public class PodcastServiceConnectionTest {
	
	@Mock PlayerBinding player;
	PodcastServiceConnection serviceConnection;
	
	@Before
	public void setUp() {
		serviceConnection = new PodcastServiceConnection();
	}

	@Test
	public void itInitiallyHasNoPlayer() {
		assertThat(serviceConnection.getPlayer(), is(nullValue()));
	}
	
	@Test
	public void itHasAPlayerWhenConnected() {
		serviceConnection.onServiceConnected(null, player);
		
		assertThat(serviceConnection.getPlayer(), sameInstance(player));
	}
}
