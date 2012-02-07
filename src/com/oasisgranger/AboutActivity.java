package com.oasisgranger;

import android.os.Bundle;
import android.support.v4.app.ActionBar;

import com.oasisgranger.R;

public class AboutActivity extends OasisFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_about);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("About Oasis");
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}
