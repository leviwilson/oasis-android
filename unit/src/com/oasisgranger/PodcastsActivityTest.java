package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.findFor;
import static com.oasisgranger.helpers.ViewHelper.textOf;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.oasisgranger.R.id;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.test.OasisTestRunner;
import com.xtremelabs.robolectric.Robolectric;

@RunWith(OasisTestRunner.class)
public class PodcastsActivityTest {

	@Mock OasisPodcasts oasisPodcasts;
	
	private PodcastsActivity activity;

	@Before
	public void setUp() throws Exception {
		setupMocks();

		activity = new PodcastsActivity();
	}

	@Test
	public void itCanLoadPodcasts() {
		setupToReturn(new Podcast());
		startActivity();

		final ListView listView = podcastList();
		assertThat(listView.getCount(), is(1));
	}

	@Test
	public void itCanDisplayThePodcastTitleAndDate() {
		@SuppressWarnings("deprecation")
		final Podcast podcast = new Podcast("My Title", new Date("1/1/2012"));
		setupToReturn(podcast);
		startActivity();

		final View view = podcastList().getAdapter().getView(0, null, null);

		assertThat(textOf(view, id.podcast_title), is("My Title"));
		assertThat(textOf(view, id.podcast_date), is("Sun 01/01/2012"));
	}

	@Test
	public void itCanDisplayThePodcastDetails() {
		@SuppressWarnings("deprecation")
		Date expectedDate = new Date("1/1/2012");
		
		final Podcast podcast = new Podcast("My Title", expectedDate);
		setupToReturn(podcast);
		startActivity();

		clickOnFirstItem();
		
		final Intent started = shadowOf(activity).getNextStartedActivity();
		assertThat(started.getComponent().getClassName(), is(PodcastDetails.class.getName()));
		
		final Podcast actualPodcast = started.getParcelableExtra(Podcast.class.getName());
		assertThat(actualPodcast.getTitle(), is("My Title"));
		assertThat(actualPodcast.publishedDate, is(expectedDate));
	}

	private void startActivity() {
		shadowOf(activity).create();
	}

	private void setupMocks() throws Exception {
		OasisGrangerApplication application = (OasisGrangerApplication) Robolectric.application;
		application.configure().withBinding(OasisPodcasts.class, oasisPodcasts);
		
		when(oasisPodcasts.load()).thenReturn(new ArrayList<Podcast>());
	}

	private ListView podcastList() {
		final ListView listView = findFor(activity, id.podcast_list);
		return listView;
	}

	private void clickOnFirstItem() {
		podcastList().getOnItemClickListener().onItemClick(null, null, 0, 0);
	}

	private void setupToReturn(Podcast... podcasts) {
		ArrayList<Podcast> podcastList = new ArrayList<Podcast>(Arrays.asList(podcasts));
		when(oasisPodcasts.load()).thenReturn(podcastList);
	}

}
