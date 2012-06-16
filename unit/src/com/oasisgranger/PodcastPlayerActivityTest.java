package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.clickOn;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.content.Intent;

import com.oasisgranger.R.id;
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
	public void itInitiallyPlaysThePodast() {
		startActivity();
		verify(serviceConnector).connectWith(podcast);
	}
	
	@Test
	public void itCanPauseThePodcast() {
		startActivity();
		clickOn(activity, id.play_or_pause);
		verify(player).pause();
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

	private void startActivity() {
		activity = new PodcastPlayerActivity();
		
		final Intent intent = new Intent();
		intent.putExtra(Podcast.class.getName(), podcast);
		activity.setIntent(intent);
		
		shadowOf(activity).create();
	}

	private void setupMocks() {
		OasisGrangerApp application = (OasisGrangerApp) Robolectric.application; 
		application.configure()
			.withBinding(PlayerBinding.class, player)
			.withBinding(PodcastServiceConnector.class, serviceConnector);
		
		when(serviceConnector.getPlayer()).thenReturn(player);
	}

}
