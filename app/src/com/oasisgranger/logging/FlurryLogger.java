package com.oasisgranger.logging;

import android.content.Context;

import com.flurry.android.FlurryAgent;

public class FlurryLogger {

	private Context context;

	public void startSession(final Context context) {
		this.context = context;
		FlurryAgent.onStartSession(context, "PC5R3F37ZKF32SWCHJD9");
	}

	public void endSession() {
		FlurryAgent.onEndSession(context);
	}

}
