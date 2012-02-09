package framework.unit;

import java.io.IOException;

import android.widget.ListView;

import com.google.gson.Gson;
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

	private void setupToResponseWith(PodcastsFeed podcastsFeed) {
		setupToRespondWith(new Gson().toJson(podcastsFeed));
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
