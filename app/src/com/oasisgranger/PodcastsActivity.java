package com.oasisgranger;

import static com.oasisgranger.helpers.ViewHelper.findFor;

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
import com.oasisgranger.R.layout;
import com.oasisgranger.models.Podcast;
import com.oasisgranger.task.TaskRunner;

public class PodcastsActivity extends OasisActivity {

	@Inject OasisPodcasts oasisPodcasts;
	@Inject DialogFacade dialogFacade;
	@Inject TaskRunner taskRunner;

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_podcasts);

		setTitle("Podcasts");

		listView = findFor(this, R.id.podcast_list);
		listView.setOnItemClickListener(new OnPodcastClick());
		
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
		final DialogInterface progressDialog = dialogFacade.showProgressFor(this, "Loading...");
		taskRunner.run(new LoadPodcastsWorkItem(this, progressDialog));
	}

	private final class OnPodcastClick implements OnItemClickListener {
		
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(getApplicationContext(), PodcastDetails.class);
			intent.putExtra(Podcast.class.getName(), podcastAt(position));
			startActivity(intent);
		}

		private Podcast podcastAt(int position) {
			return (Podcast)listView.getItemAtPosition(position);
		}
	}

}
