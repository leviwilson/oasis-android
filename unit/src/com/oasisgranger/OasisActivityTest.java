package com.oasisgranger;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.oasisgranger.logging.FlurryLogger;
import com.oasisgranger.test.OasisTestRunner;
import com.xtremelabs.robolectric.Robolectric;

@RunWith(OasisTestRunner.class)
public class OasisActivityTest {
	
	@Mock FlurryLogger flurryLogger;
	
	private OasisActivity activity;
	
	@Before
	public void setUp() {
		setupMocks();
		
		activity = new OasisActivity();
		shadowOf(activity).create();
	}
	
	@Test
	public void itInitiatesAFlurrySession() {
		verify(flurryLogger).startSession(activity);
	}
	
	@Test
	public void itFinalizesTheFlurrySession() {
		activity.onStop();
		verify(flurryLogger).endSession();
	}

	private void setupMocks() {
		OasisGrangerApp application = (OasisGrangerApp) Robolectric.application;
		application.configure()
			.withBinding(FlurryLogger.class, flurryLogger);
	}

}
