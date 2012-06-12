package com.oasisgranger;

import android.test.ApplicationTestCase;

public class OasisPodcastsTest extends ApplicationTestCase<OasisGrangerApplication> {
	
	public OasisPodcastsTest() {
		super(OasisGrangerApplication.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		createApplication();
	}

	public void testThatItCanRetrievePodcasts() {
		final OasisPodcasts oasisPodcasts = getApplication().instanceOf(OasisPodcasts.class);
		assertTrue("Expected to find some podcasts", oasisPodcasts.load().size() != 0);
	}

}
