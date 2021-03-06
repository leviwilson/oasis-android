package com.oasisgranger;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.inject.Inject;
import com.oasisgranger.R.id;
import com.oasisgranger.logging.FlurryLogger;

public class OasisActivity extends com.google.actionbar.ActionBarActivity {
	
	@Inject
	private FlurryLogger flurryLogger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getOasisApplication().injectInto(this);
	}
	
	@Override
	protected void onStart() {
		flurryLogger.startSession(this);
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		flurryLogger.endSession();
		super.onStop();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(com.oasisgranger.R.menu.podcasts_menu, menu);
		MenuCompat.setShowAsAction(menu.findItem(id.podcasts_refresh), 1);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (!isHome()) {
				goBack();
			}

			// Get rid of the slide-in animation, if possible
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
				this.overridePendingTransition(0, 0);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	private void goBack() {
		finish();
	}

	private boolean isHome() {
		return HomeActivity.class.equals(this.getClass());
	}

	private OasisGrangerApp getOasisApplication() {
		return (OasisGrangerApp) getApplication();
	}
	
}
