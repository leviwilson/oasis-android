package com.oasisgranger.test.shadows;

import android.widget.Chronometer;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.shadows.ShadowView;

@Implements(Chronometer.class)
public class ShadowChronometer extends ShadowView {
	private boolean wasStarted;
	private boolean wasStopped;
	private long initialElapsedMillis;
	
	@Implementation
	public void start() {
		wasStarted = true;
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
		return wasStarted;
	}

	public boolean wasStopped() {
		return wasStopped;
	}

	public void reset() {
		wasStarted = false;
		wasStopped = false;
	}

	public long initialElapsed() {
		return initialElapsedMillis;
	}
}
