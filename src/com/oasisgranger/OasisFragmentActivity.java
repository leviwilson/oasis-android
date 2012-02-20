package com.oasisgranger;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItem;
import android.widget.TextView;

import com.oasisgranger.helpers.ViewHelper;

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

	protected void setTextFor(int id, String text) {
		TextView titleView = ViewHelper.findFor(this, id);
		titleView.setText(text);
	}

}
