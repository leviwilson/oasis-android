package com.oasisgranger;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItem;

public class OasisFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getOasisApplication().injectInto(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			goHome();

			// Get rid of the slide-in animation, if possible
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
				this.overridePendingTransition(0, 0);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	private void goHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	private OasisGrangerApplication getOasisApplication() {
		return (OasisGrangerApplication) getApplication();
	}

	protected void initializeChildTitle(String title) {
		ActionBar actionBar = getSupportActionBar();
		if (null != actionBar) {
			actionBar.setTitle(title);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

}
