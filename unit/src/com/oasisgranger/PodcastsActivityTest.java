package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.findFor;
import static com.oasisgranger.helpers.ViewHelper.textOf;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oasisgranger.OasisGrangerApplication;
import com.oasisgranger.OasisPodcasts;
import com.oasisgranger.PodcastDetails;
import com.oasisgranger.PodcastsActivity;
import com.oasisgranger.R.id;
import com.oasisgranger.Requestor;
import com.oasisgranger.Response;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.models.PodcastsFeed;
import com.oasisgranger.test.OasisTestRunner;
import com.xtremelabs.robolectric.Robolectric;

@RunWith(OasisTestRunner.class)
public class PodcastsActivityTest {

	@Mock
	Requestor requestor;
	@Mock
	Response response;

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

		final ListView listView = findFor(activity, id.podcast_list);
		assertThat(listView.getCount(), is(1));
	}

	@Test
	public void itCanDisplayThePodcastTitleAndDate() {
		@SuppressWarnings("deprecation")
		final Podcast podcast = new Podcast("My Title", new Date("1/1/2012"));
		setupToReturn(podcast);
		startActivity();

		final ListView listView = findFor(activity, id.podcast_list);
		final View view = listView.getAdapter().getView(0, null, null);

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

		final ListView listView = findFor(activity, id.podcast_list);
		listView.getOnItemClickListener().onItemClick(null, null, 0, 0);
		
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
		application.configure().withBinding(Requestor.class, requestor);

		when(requestor.get(anyString())).thenReturn(response);
		when(response.getMessage()).thenReturn(
				"{responseData: {feed: {entries: [] }}}");
	}

	private void setupToReturn(Podcast... podcasts) {
		final Gson gson = new GsonBuilder().setDateFormat(
				OasisPodcasts.FEED_DATE_FORMAT).create();

		try {
			when(response.getMessage()).thenReturn(
					gson.toJson(new PodcastsFeed(podcasts)));
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
