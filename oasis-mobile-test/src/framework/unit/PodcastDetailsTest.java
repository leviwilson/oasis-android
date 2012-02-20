package framework.unit;

import java.util.Date;

import android.content.Intent;
import android.widget.TextView;

import com.oasisgranger.PodcastDetails;
import com.oasisgranger.R;
import com.oasisgranger.helpers.ViewHelper;
import com.oasisgranger.models.Podcast;

public class PodcastDetailsTest extends OasisActivityUnitTestCase<PodcastDetails> {

	public PodcastDetailsTest() {
		super(PodcastDetails.class);
	}
	
	public void testThatTheTitleIsDisplayed() {
		Podcast podcast = new Podcast("Sermon Title", new Date());
		Intent intent = new Intent();
		intent.putExtra(Podcast.class.getName(), podcast);
		
		startActivityLifecycleWith(intent);
		
		TextView titleText = ViewHelper.findFor(getActivity(), R.id.podcast_title);
		
		assertEquals("Sermon Title", titleText.getText().toString());
	}
}
