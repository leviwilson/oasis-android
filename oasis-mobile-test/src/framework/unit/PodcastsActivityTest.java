package framework.unit;

import java.io.IOException;
import java.util.Date;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oasisgranger.OasisPodcasts;
import com.oasisgranger.PodcastsActivity;
import com.oasisgranger.R;
import com.oasisgranger.Requestor;
import com.oasisgranger.Response;
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
		setupToResponseWith(new PodcastsFeed(podcasts));
		startActivityLifecycle();
		
		ListView listView = ViewHelper.findFor(getActivity(), R.id.podcast_list);
		assertEquals(1, listView.getCount());
	}
	
	public void testThatWeLoadTitleAndDate() {
		Podcast[] podcasts = { new Podcast("My Title", new Date("1/1/2012")) };
		setupToResponseWith(new PodcastsFeed(podcasts));
		startActivityLifecycle();
		
		ListView listView = ViewHelper.findFor(getActivity(), R.id.podcast_list);
		View view = listView.getAdapter().getView(0, null, null);
		
		TextView title = ViewHelper.findFor(view, R.id.podcast_title);
		TextView date = ViewHelper.findFor(view, R.id.podcast_date);
		
		assertEquals("My Title", title.getText().toString());
		assertEquals("Sun 01/01/2012", date.getText().toString());
		
	}

	private void setupToResponseWith(PodcastsFeed podcastsFeed) {
		Gson gson = new GsonBuilder()
			.setDateFormat(OasisPodcasts.FEED_DATE_FORMAT)
			.create();
		setupToRespondWith(gson.toJson(podcastsFeed));
	}

	private void setupToRespondWith(final String message) {
		RequestorStub requestor = (RequestorStub) getApplication().instanceOf(
				Requestor.class);
		requestor.setResponse(new Response(null) {
			@Override
			public String getMessage() throws IllegalStateException,
					IOException {
				return message;
			}
		});
	}

}
