package com.oasisgranger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class OasisService extends Service {
	
	@Override
	public void onCreate() {
		final OasisGrangerApp app = (OasisGrangerApp) getApplication();
		app.injectInto(this);
		
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
