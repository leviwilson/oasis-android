package com.oasisgranger;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.inject.Inject;
import com.oasisgranger.helpers.ViewHelper;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.task.TaskRunner;

public class PodcastsActivity extends OasisActivity {

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

		initializeChildTitle("Podcasts");

		listView = ViewHelper.findFor(this, R.id.podcast_list);
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getBaseContext(), PodcastDetails.class);
				intent.putExtra(Podcast.class.getName(), (Podcast)listView.getItemAtPosition(position));
				startActivity(intent);
			}
		});
		
		loadPodcasts();
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
