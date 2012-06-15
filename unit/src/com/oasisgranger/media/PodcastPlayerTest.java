package com.oasisgranger.media;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Spy;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.oasisgranger.PodcastService;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.test.OasisTestRunner;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.matchers.StartedServiceMatcher;

@RunWith(OasisTestRunner.class)
public class PodcastPlayerTest {
	
	private PodcastPlayer player;
	
	@Spy Context context = Robolectric.application;
	
	@Before
	public void setUp() {
		player = new PodcastPlayer(context);
	}
	
	@Test
	public void itStartsThePodcastService() {
		final Podcast podcast = new Podcast();
		player.play(podcast);
		
		final Intent expectedService = new Intent(context, PodcastService.class);
		expectedService.putExtra(Podcast.class.getName(), podcast);
		
		assertThat(context, new StartedServiceMatcher(expectedService));
	}
	
	@Test
	public void itConnectsToTheService() {
		player.play(new Podcast());
		
		ArgumentCaptor<Intent> intentArg = ArgumentCaptor.forClass(Intent.class);
		verify(context).startService(intentArg.capture());
		
		verify(context).bindService(eq(intentArg.getValue()), any(ServiceConnection.class), eq(0));
	}
	
}
