package com.oasisgranger.test.shadows;

import android.widget.Chronometer;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.shadows.ShadowView;

@Implements(Chronometer.class)
public class ShadowChronometer extends ShadowView {
	private int startCount;
	private boolean wasStopped;
	private long initialElapsedMillis;

	@Implementation
	public void start() {
		++startCount;
	}

	@Implementation
	public void stop() {
		wasStopped = true;
	}

	@Implementation
	public void setBase(long millis) {
		initialElapsedMillis = millis;
	}

	public boolean wasStarted() {
		return startCount == 1;
	}

	public boolean wasRestarted() {
		return startCount == 2;
	}

	public boolean wasStopped() {
		return wasStopped;
	}

	public long initialElapsed() {
		return initialElapsedMillis;
	}
}
