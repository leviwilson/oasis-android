package com.oasisgranger;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.oasisgranger.test.OasisTestRunner;

@RunWith(OasisTestRunner.class)
public class OasisGrangerApplicationTest {
	
	private OasisGrangerApplication application;

	@Before
	public void setUp() {
		application = new OasisGrangerApplication();
		application.onCreate();
	}

	@Test
	public void itKeepsTheMusicBumpin() {
		assertThat(shadowOf(application).getNextStartedService(), is(not(nullValue())));
	}

}
