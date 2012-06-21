package com.oasisgranger;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.os.Bundle;

import com.oasisgranger.R.layout;
import com.oasisgranger.logging.FlurryLogger;
import com.oasisgranger.test.OasisTestRunner;
import com.xtremelabs.robolectric.Robolectric;

@RunWith(OasisTestRunner.class)
public class OasisActivityTest {

	@Mock FlurryLogger flurryLogger;
	private OasisTestActivity activity;

	@Before
	public void setUp() {
		setupMocks();

		activity = new OasisTestActivity();
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

	private class OasisTestActivity extends OasisActivity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			setContentView(layout.activity_home);
			super.onCreate(savedInstanceState);
		}
	}

}
