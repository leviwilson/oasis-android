package com.oasisgranger;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class OasisFragmentActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getOasisApplication().injectInto(this);
	}

	private OasisGrangerApplication getOasisApplication() {
		return (OasisGrangerApplication) getApplication();
	}
	
}
