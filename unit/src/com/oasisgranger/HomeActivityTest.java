package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.clickOn;
import static com.oasisgranger.test.matchers.StartedActivityMatcher.started;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.oasisgranger.R.id;
import com.oasisgranger.test.OasisTestRunner;

@RunWith(OasisTestRunner.class)
public class HomeActivityTest {
	
	private HomeActivity activity;

	@Before
	public void setUp() {
		activity = new HomeActivity();
		shadowOf(activity).create();
	}
	
	@Test
	public void itCanViewPodcasts() {
		clickOn(activity, id.home_btn_podcast);
		assertThat(activity, started(PodcastsActivity.class));
	}
	
	@Test
	public void itCanSeeWhatWeAreAbout() {
		clickOn(activity, id.home_btn_aboutus);
		assertThat(activity, started(AboutActivity.class));
	}
}
