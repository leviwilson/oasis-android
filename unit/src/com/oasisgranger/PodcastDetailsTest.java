package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.textOf;
import static com.oasisgranger.test.matchers.StartedActivityMatcher.startedWithExtra;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;

import com.oasisgranger.R.id;
import com.oasisgranger.helpers.ViewHelper;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.test.OasisTestRunner;
import com.oasisgranger.test.matchers.Extra;

@RunWith(OasisTestRunner.class)
public class PodcastDetailsTest {

	private PodcastDetails activity;
	private Podcast podcast;

	@Before
	public void setUp() {
		activity = new PodcastDetails();
		podcast = new Podcast();
	}

	@Test
	public void itDisplaysTheTitle() {
		podcast.setTitle("Sermon Title");
		startActivity();

		assertThat(textOf(activity, id.podcast_title), is("Sermon Title"));
	}
	
	@Test
	public void itDisplaysThePodcastDate() throws ParseException {
		podcast.publishedDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2012");
		startActivity();
		
		assertThat(textOf(activity, id.podcast_date), is("1/1/2012"));
	}

	@Test
	public void itDisplaysTheDescription() {
		podcast.setDescription("This is the podcast description.");
		startActivity();

		assertThat(textOf(activity, id.podcast_description), is("This is the podcast description."));
	}

	@Test
	public void itCanPlayThePodcast() {
		startActivity();

		ViewHelper.clickOn(activity, id.play_podcast);

		assertThat(activity, startedWithExtra(PodcastPlayerActivity.class, new Extra(podcast)));
	}

	private void startActivity() {
		final Intent intent = new Intent();
		intent.putExtra(Podcast.class.getName(), podcast);
		activity.setIntent(intent);

		shadowOf(activity).create();
	}

}
