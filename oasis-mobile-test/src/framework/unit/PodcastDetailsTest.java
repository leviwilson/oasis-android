package framework.unit;

import android.content.Intent;

import com.oasisgranger.PodcastDetails;
import com.oasisgranger.R;
import com.oasisgranger.models.Podcast;

public class PodcastDetailsTest extends OasisActivityUnitTestCase<PodcastDetails> {

	private Podcast podcast;

	public PodcastDetailsTest() {
		super(PodcastDetails.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		podcast = new Podcast();
	}

	private void startPodcastDetailsActivity() {
		Intent intent = new Intent();
		intent.putExtra(Podcast.class.getName(), podcast);
		startActivityLifecycleWith(intent);
	}
	
	public void testThatTheTitleIsDisplayed() {
		podcast.setTitle("Sermon Title");
		startPodcastDetailsActivity();
		
		assertEquals("Sermon Title", textFor(R.id.podcast_title));
	}
	
	public void testThatTheDescriptionIsDisplayed() {
		podcast.setDescription("This is the podcast description.");
		startPodcastDetailsActivity();
		
		assertEquals("This is the podcast description.", textFor(R.id.podcast_description));
	}
}
