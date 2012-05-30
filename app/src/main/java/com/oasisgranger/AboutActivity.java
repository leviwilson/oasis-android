package com.oasisgranger;

import android.os.Bundle;

import com.oasisgranger.R;

public class AboutActivity extends OasisActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		setTitle("About Oasis");
	}
}
