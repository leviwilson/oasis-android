package com.oasisgranger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class OasisService extends Service {
	
	public OasisService() {
		final OasisGrangerApp app = (OasisGrangerApp) getApplication();
		app.injectInto(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
