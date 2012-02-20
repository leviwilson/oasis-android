package framework.unit;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oasisgranger.OasisPodcasts;
import com.oasisgranger.PodcastsActivity;
import com.oasisgranger.R;
import com.oasisgranger.helpers.ViewHelper;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.models.PodcastsFeed;

public class PodcastsActivityTest extends
		OasisActivityUnitTestCase<PodcastsActivity> {

	private static final String EMPTY_PODCASTS = "{responseData: {feed: {entries: [] }}}";

	public PodcastsActivityTest() {
		super(PodcastsActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setupToRespondWith(EMPTY_PODCASTS);
	}

	public void testThatWeCanLoadPodcasts() {
		Podcast[] podcasts = { new Podcast() };
		setupToReturn(podcasts);
		startActivityLifecycle();

		ListView listView = ViewHelper
				.findFor(getActivity(), R.id.podcast_list);
		assertEquals(1, listView.getCount());
	}

	public void testThatWeLoadTitleAndDate() {
		Podcast[] podcasts = { new Podcast("My Title", new Date("1/1/2012")) };
		setupToReturn(podcasts);
		startActivityLifecycle();

		ListView listView = ViewHelper
				.findFor(getActivity(), R.id.podcast_list);
		View view = listView.getAdapter().getView(0, null, null);

		TextView title = ViewHelper.findFor(view, R.id.podcast_title);
		TextView date = ViewHelper.findFor(view, R.id.podcast_date);

		assertEquals("My Title", title.getText().toString());
		assertEquals("Sun 01/01/2012", date.getText().toString());
	}
	
	public void testThatWeWillShowDetailsForAPodcast() {
		Podcast[] podcasts = { new Podcast("My Title", new Date("1/1/2012")) };
		setupToReturn(podcasts);
		startActivityLifecycle();
		
		ListView listView = ViewHelper.findFor(getActivity(), R.id.podcast_list);
		listView.getOnItemClickListener().onItemClick(null, null, 0, 0);
		
		Intent actualIntent = getStartedActivityIntent();
		assertNotNull(actualIntent);
		
		Podcast actualPodcast = actualIntent.getParcelableExtra(Podcast.class.getName());
		assertEquals("My Title", actualPodcast.title);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
		assertEquals("1/1/2012", dateFormat.format(actualPodcast.publishedDate));
	}

	private void setupToReturn(Podcast[] podcasts) {
		Gson gson = new GsonBuilder().setDateFormat(
				OasisPodcasts.FEED_DATE_FORMAT).create();
		setupToRespondWith(gson.toJson(new PodcastsFeed(podcasts)));
	}

}
