package com.oasisgranger;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.inject.Inject;
import com.oasisgranger.helpers.ViewHelper;
import com.oasisgranger.task.TaskRunner;

public class PodcastsActivity extends OasisFragmentActivity {

	@Inject OasisPodcasts oasisPodcasts;

	@Inject
	private DialogFacade dialogFacade;

	@Inject
	TaskRunner taskRunner;

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_podcasts);

		intializeActionBar();

		listView = ViewHelper.findFor(this, R.id.podcast_list);
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getBaseContext(), PodcastActivity.class);
				startActivity(intent);
			}
		});
		
		loadPodcasts();
	}

	private void intializeActionBar() {
		ActionBar actionBar = getSupportActionBar();
		if (null != actionBar) {
			actionBar.setTitle("Podcasts");
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.podcasts_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.podcasts_refresh:
			loadPodcasts();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void loadPodcasts() {
		final DialogInterface progressDialog = dialogFacade.showProgressFor(
				this, "Loading...");
		taskRunner.run(new LoadPodcastsWorkItem(this, progressDialog));
	}

}
