package com.oasisgranger;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.content.Intent;

import com.oasisgranger.media.PodcastServiceConnector;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.test.OasisTestRunner;
import com.xtremelabs.robolectric.Robolectric;

@RunWith(OasisTestRunner.class)
public class PodcastPlayerActivityTest {
	
	@Mock PodcastServiceConnector podcastPlayer;
	
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
		verify(podcastPlayer).connectWith(podcast);
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
		application.configure().withBinding(PodcastServiceConnector.class, podcastPlayer);
	}

}
